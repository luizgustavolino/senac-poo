/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import ubs.exceptions.AgendamentoCanceladoException;

/**
 *
 * @author luizgustavolino
 */
public class Agendamento {
    
    private Date data;
    private Paciente paciente;
    private Especialista especialista;
    
    public Agendamento(Paciente paciente) throws AgendamentoCanceladoException {
        
        // ESCOLHA DO PACIENTE
        this.paciente = paciente;
        
        // ESCOLHA DO MÉDICO
        List<Especialista> especialistas;
        String[] especialidades = {"Médico", "Dentista", "Cancelar"};
        String especialidade = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(especialidades);
        
        switch(especialidade){
            case "Médico":
                especialistas = Medico.todos();
                UBS.getInstance().ui.mostra("Selecionando entre médicos. ");
                break;
            case "Dentista":
                especialistas = Dentista.todos();
                UBS.getInstance().ui.mostra("Selecionando entre dentistas. ");
                break;
            case "Cancelar": throw new AgendamentoCanceladoException();
            default:
                UBS.getInstance().ui.mostraLinha("Não foi possível encontrar funcionário para especialidade escolhida.");
                throw new AgendamentoCanceladoException();
        }
        
        List<String> opcoesDeEspecialistas = new ArrayList<>();
        for (Especialista umEspecialista : especialistas) opcoesDeEspecialistas.add(umEspecialista.nomeCompletoComEmail());
        opcoesDeEspecialistas.add("Cancelar");
        
        String especialistaEscolhido = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(opcoesDeEspecialistas);
        if(especialistaEscolhido.equals("Cancelar")) throw new AgendamentoCanceladoException();
        
        int indexDoEspecialista = opcoesDeEspecialistas.indexOf(especialistaEscolhido);
        this.especialista = especialistas.get(indexDoEspecialista);
        
        // ESCOLHA DA DATA
        Date dataPretendida  = null;
                
        do{
            
            UBS.getInstance().ui.mostraLinha("Escolha a melhor data para sua consulta (dia/mes/ano):");
            Date dataDigitada = UBS.getInstance().ui.pedeUmaDataFutura();
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataDigitada);
            int diaDaSemana = calendar.get(Calendar.DAY_OF_WEEK);
            if (diaDaSemana == Calendar.SUNDAY || diaDaSemana == Calendar.SATURDAY){
                
                UBS.getInstance().ui.mostraLinha("Desculpe, não atendemos aos fins de semana.");
                
            }else{
                
                dataPretendida = dataDigitada;
                String periodoEscolhido;
                
                List<Agendamento> agendamentosDoEspecialista = especialista.agendamentosNoDia(dataPretendida);
                List<Date> horariosIndisponiveis = new ArrayList<>();
                for (Agendamento agendamento : agendamentosDoEspecialista) {
                    horariosIndisponiveis.add(agendamento.getData());
                }

                do{

                    String[] periodos = {"Manhã", "Tarde", "Cancelar"};
                    UBS.getInstance().ui.mostraLinha("Qual o melhor periodo para você?");
                    periodoEscolhido = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(periodos);
                    
                    List<Date> horarios;
                    switch(periodoEscolhido){
                        case "Manhã":
                            horarios = horariosPorHora(dataPretendida, new int[]{9,10,11}, horariosIndisponiveis);
                            break;
                        case "Tarde":
                            horarios = horariosPorHora(dataPretendida, new int[]{14,15,16}, horariosIndisponiveis);
                            break;
                        case "Cancelar":
                        default: throw new AgendamentoCanceladoException();
                    }
                    
                    if (horarios.isEmpty()){
                        
                        UBS.getInstance().ui.mostraLinha("(!) Infelizmente não temos mais nenhum horário disponível.");
                        periodoEscolhido = null;
                        
                    }else{
                    
                        List<String> opcoesDeHorario = new ArrayList<>();
                        DateFormat formatador = new SimpleDateFormat("HH:mm");
                        for (Date horario : horarios) opcoesDeHorario.add(formatador.format(horario));
                        opcoesDeHorario.add("Outro periodo");
                        opcoesDeHorario.add("Outro dia");
                        opcoesDeHorario.add("Cancelar");

                        UBS.getInstance().ui.mostraLinha("Qual o melhor horário?");
                        String horarioEscolhido = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(opcoesDeHorario);

                        switch (horarioEscolhido) {
                            case "Outro periodo":
                                periodoEscolhido = null; break;
                            case "Outro dia":
                                dataPretendida = null; break;
                            case "Cancelar": throw new AgendamentoCanceladoException();
                            default:
                                int index = opcoesDeHorario.indexOf(horarioEscolhido);
                                this.data = horarios.get(index);
                                break;
                        }
                    }

                }while(periodoEscolhido == null);
            }
        }while(dataPretendida == null);
        
    }
    
    public void confirmar() throws AgendamentoCanceladoException{
        
        // CONFIRMAÇÃO
        UBS.getInstance().ui.mostraLinha("Sua agendamento ficou assim:\n" + descricao());
        String confirmacao = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(new String[]{"Confirma", "Cancela"});
        
        if(confirmacao.equals("Cancela")){
            throw new AgendamentoCanceladoException();
        }else{
            UBS.getInstance().ui.mostraLinha( this.paciente.getNome() + ", sua consulta está confirmada!");
            UBS.getInstance().ui.mostraLinha( "Nos vemos em breve.");
        }
        
        this.especialista.adicionarAgendamento(this);
    }
    
    private static List<Date> horariosPorHora(Date dia, int[] horas, List<Date> indisponiveis){
        
        ArrayList<Date> horarios = new ArrayList<>();
        int[] minutos   = {0,30};
        
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.setTime(dia);
        
        for (int hora : horas) {
            for (int minuto : minutos) {
                
                cal.set(Calendar.HOUR_OF_DAY, hora);
                cal.set(Calendar.MINUTE, minuto);
                Date proposta = cal.getTime();
                
                boolean ocupado = false;
                for (Date indisponivel : indisponiveis) {
                    if (proposta.equals(indisponivel)){
                        ocupado = true;
                        break;
                    }
                }
                
                if(!ocupado) horarios.add(cal.getTime());
            }
        }
        
        return horarios;
    }
    
    public String horario(){
        return new SimpleDateFormat("dd/MM' às 'HH:mm").format(this.data);
    }
    
    public String descricao(){
        return "# Dia " + horario() + " - " + this.especialista.descricao() + " #";
    }
    
    public Date getData() {
        return data;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Especialista getEspecialista() {
        return especialista;
    }
}
