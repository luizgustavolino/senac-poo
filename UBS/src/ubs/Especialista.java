/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javafx.scene.input.DataFormat;
import ubs.extensions.DateHelpers;
import ubs.exceptions.NenhumPacienteEscolhidoException;
import ubs.ui.Interfaciavel;

/**
 *
 * @author luizgustavolino
 */
public abstract class Especialista extends Funcionario{
    
    private List<Agendamento> agendamentos; 
    
    public Especialista() {
        super();
    }
    
    public static List<Especialista> todos(){
        return todos(Especialista.class);
    }
    
    protected static List<Especialista> todos(Class especialidade){
        
        List<Especialista> todosEspecialistas = new ArrayList<>();
        List<Usuario> todos = UBS.getInstance().getUsuarios();
        
        for(Usuario usuario : todos){
            if(usuario.getClass().equals(especialidade)){
                todosEspecialistas.add((Especialista)usuario);
            }
        }

        return todosEspecialistas;
    }
    
    public abstract void atender(Paciente p);
    
    public void adicionarAgendamento(Agendamento agendamento){
        if(agendamentos == null) agendamentos = new ArrayList<>();
        agendamentos.add(agendamento);
        Collections.sort(agendamentos, new Comparator<Agendamento>() {
            @Override
            public int compare(Agendamento a, Agendamento b){
                return a.getData().compareTo(b.getData());
            }
        });
    }
    
    public void removerAgendamento(Agendamento agendamento){
        try{
            agendamentos.remove(agendamento);
        }catch(Exception e){
            UBS.getInstance().ui.mostraLinha("(!) Um agendamento marcado como cancelado não foi encontrado.");
        }
    }
    
    public List<Agendamento> agendamentosNoDia(Date dia){
        ArrayList<Agendamento> noDia = new ArrayList<>();
        if(agendamentos != null){
            for (Agendamento agendamento : agendamentos) {
                if(DateHelpers.isSameDay(agendamento.getData(), dia))
                    noDia.add(agendamento);
            }
        }
        return noDia;
    }
    
    public ArrayList<Agendamento> proximosAgendamentos(){
        ArrayList<Agendamento> proximos = new ArrayList<>();
        if(agendamentos != null){
            for (Agendamento agendamento : agendamentos) {
                if(agendamento.getData().after(new Date())){
                    proximos.add(agendamento);
                }
            }
        }
        return proximos;
    }
    
    protected Paciente selecionaProximoPaciente() throws NenhumPacienteEscolhidoException{
        Paciente pacienteEscolhido = null;
        do{
            
            UBS.getInstance().ui.mostraLinha("Como gostaria de escolher o paciente?");
            String opt = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(new String[]{"Buscando nos meus agendamentos", "Consultando por nome e data de nascimento", "Cancelar"});

            switch (opt) {
                case "Buscando nos meus agendamentos":
                    
                    ArrayList<Agendamento> proximosAgendamentos = proximosAgendamentos();
                    
                    if(proximosAgendamentos.isEmpty()){
                        UBS.getInstance().ui.mostraLinha("(!) " + nome + ", não há agendamentos em seu nome.");
                    }else{
                        
                        ArrayList<Paciente> pacientesAgendados = new ArrayList<>();
                        ArrayList<Agendamento> agendamentosDosPacientes = new ArrayList<>();
                        for (Agendamento agendamento : proximosAgendamentos) {
                            if(pacientesAgendados.indexOf(agendamento.getPaciente()) == -1){
                                pacientesAgendados.add(agendamento.getPaciente());
                                agendamentosDosPacientes.add(agendamento);
                            }
                        }
                        
                        ArrayList<String> nomes = new ArrayList<>();
                        for (Agendamento umAgendamento : agendamentosDosPacientes){
                            nomes.add(umAgendamento.getPaciente().descricao() + "\n    -> próximo agendameto para " + umAgendamento.horario());
                        }
                        
                        nomes.add("Continuar procurando");
                        String nomePacienteEscolhido = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(nomes);
                        if(!nomePacienteEscolhido.equals("Continuar procurando")){
                            int index = nomes.indexOf(nomePacienteEscolhido);
                            pacienteEscolhido = pacientesAgendados.get(index);
                        }
                    }   break;
                case "Consultando por nome e data de nascimento":
                    
                    UBS.getInstance().ui.mostra("Digite o sobrenome do paciente: ");
                    String nomeDigitado = UBS.getInstance().ui.pedeString();
                    List<Usuario> todos = UBS.getInstance().getUsuarios();
                    ArrayList<Paciente> provaveis = new ArrayList<>();
                    
                    for (Usuario usuario : todos) {
                        if(usuario.getClass().equals(Paciente.class) && usuario.getSobrenome().toLowerCase().equals(nomeDigitado.toLowerCase())){
                            provaveis.add((Paciente) usuario);
                        }
                    }
                    
                    if(provaveis.isEmpty()){
                        UBS.getInstance().ui.mostraLinha("(!) Nenhum paciente foi encontrado para este sobrenome.");
                    }else{
                        
                        ArrayList<String> nomes = new ArrayList<>();
                        for (Paciente pacienteProvavel : provaveis) nomes.add(pacienteProvavel.descricao());
                        nomes.add("Continuar procurando");
                        String nomePacienteEscolhido = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(nomes);
                        
                        if(!nomePacienteEscolhido.equals("Continuar procurando")){
                            int index = nomes.indexOf(nomePacienteEscolhido);
                            pacienteEscolhido = provaveis.get(index);
                        }
                    }
                    
                    break;
                default:
                    throw new NenhumPacienteEscolhidoException();
            }
        
        } while(pacienteEscolhido == null);
        return pacienteEscolhido;
    }
    
    
    @Override
    public ArrayList<String> acoesDisponiveis(String contexto) {
        ArrayList<String> acoes = new ArrayList<>();
        acoes.addAll(Arrays.asList("Agendamentos do dia", "Atender um paciente", "Pesquisar prontuários"));
        acoes.addAll(super.acoesDisponiveis(contexto));
        return acoes;
    }
    
    @Override
    public Interfaciavel escolherAcao(String acao) {
        switch(acao){
            case "Agendamentos do dia": return fluxoAgendamentosDoDia();
            case "Atender um paciente": return fluxoAtenderUmPaciente();
            case "Pesquisar prontuários": return fluxoPesquisaProntuário();
            default: return super.escolherAcao(acao);
        }
    }
    
    private Interfaciavel fluxoAgendamentosDoDia(){
        
        List<Agendamento> doDia = agendamentosNoDia(new Date());
        
        if(doDia.isEmpty()){
            UBS.getInstance().ui.mostraLinha("Nenhuma consulta marcada para hoje, "+nome+".");
        }else{
            UBS.getInstance().ui.mostraLinha("Estes são os agendamentos do dia, "+nome+":");
            for (Agendamento a : doDia) {
                UBS.getInstance().ui.mostraLinha("# "+a.horario() + ": " + a.getPaciente().nomeCompleto() + " #");
            }
        }
        return this; 
    }
     
    private Interfaciavel fluxoAtenderUmPaciente(){
        try{
            Paciente p = selecionaProximoPaciente();
            boolean continua;
            do{
                
                String opt = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(new String[]{
                    "Ver o prontuário", "Iniciar atendimento", "Cancelar"}
                );
                
                switch(opt){
                    case "Ver o prontuário":
                        p.getProntuario().visualizar();
                        continua = true;
                        break;
                    case "Iniciar atendimento":
                        atender(p);
                    default:
                        continua = false;
                        break;
                }
            }while(continua);
        }catch(Exception e){}
        return this;
    }
    
    private  Interfaciavel fluxoPesquisaProntuário(){
        try{
            
            UBS.getInstance().ui.mostraLinha("Pesquisando o arquivo de prontuários...");
            Paciente paciente = selecionaProximoPaciente();
            boolean continua;
            
            do{
                UBS.getInstance().ui.mostraLinha("Prontuário selecionado: "+paciente.nomeCompleto());
                String opt = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(new String[]{
                    "Visualizar o prontuário", "Editar uma anotação", "Cancelar"}
                );
                
                switch(opt){
                    case "Visualizar o prontuário":
                        
                        paciente.getProntuario().visualizar();
                        continua = true;
                        break;
                        
                    case "Editar uma anotação":
                        
                        List<Anotacao> todas = paciente.getProntuario().getAnotacoes(); 
                        ArrayList<Anotacao> minhas = new ArrayList<>();
                        for (Anotacao nota : todas) {
                            if(nota.getAutor().equals(this)) minhas.add(nota);
                        }
                        
                        if(minhas.isEmpty()){
                            UBS.getInstance().ui.mostraLinha("(!) Nenhuma anotação em nome de "+nome+" para este paciente.");
                            
                        }else{
                            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM");
                            boolean continuaEditando;

                            for (Anotacao nota : minhas) {

                                UBS.getInstance().ui.mostraLinha("-- Anotação do dia "+formatador.format(nota.getData())+" --");
                                UBS.getInstance().ui.mostraLinha(nota.resumo());
                                UBS.getInstance().ui.mostraLinha("-- Fim da anotação --------");
                                String notaOpt = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(new String[]{
                                    "Editar", "Próxima anotação", "Sair"}
                                );

                                switch(notaOpt){
                                    case "Sair": continuaEditando = false; break;
                                    case "Editar": nota.editar();
                                    default: continuaEditando = true; break;
                                }

                                if(!continuaEditando) break;
                            }

                            UBS.getInstance().ui.mostraLinha("(!) Fim do prontuário.");
                        }
                        
                        continua = true;
                        break;
                    default:
                        continua = false;
                        break;
                }
            }while(continua);
        }catch(Exception e){}
        return this;
    }
}
