/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import ubs.exceptions.AgendamentoCanceladoException;
import ubs.ui.Interfaciavel;

/**
 *
 * @author luizgustavolino
 */
public class Paciente extends Usuario{
    
    private String dataDeNascimento;
    private final List<Agendamento> agendamentos; 
    private final Prontuario prontuario;
    
    public Paciente() {
        
        super();
        
        do{
            
            UBS.getInstance().ui.mostraLinha("Digite sua data de nascimento (dia/mês/ano): ");
            String dataDigitada = UBS.getInstance().ui.pedeString();
            DateFormat formatador = new SimpleDateFormat("d/M/yyyy");
            formatador.setLenient(false);
            Date dataEscolhida;

            try {
                dataEscolhida = formatador.parse(dataDigitada);
                dataDeNascimento = formatador.format(dataEscolhida);
            }catch(ParseException e){
                // erro na digitação
            }finally{
                if(dataDeNascimento == null){
                    UBS.getInstance().ui.mostra("Desculpe, data inválida. ");
                }
            }
            
        }while(dataDeNascimento == null);
        
        agendamentos = new ArrayList<>();
        prontuario = new Prontuario(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(Paciente.class)){
            Paciente p = (Paciente) obj;
            return p.getEmail().equals(email);
        }else{
            return super.equals(obj); 
        }
    }
    
    @Override
    public ArrayList<String> acoesDisponiveis(String contexto) {
        ArrayList<String> acoes = new ArrayList<>();
        acoes.addAll(Arrays.asList("Meus agendamentos", "Novo agendamento", "Cancelar agendamento", "Consultar meu prontuário"));
        acoes.addAll(super.acoesDisponiveis(contexto));
        return acoes;
    }
    
    @Override
    public Interfaciavel escolherAcao(String acao) {
        switch(acao){
            case "Novo agendamento": return fluxoNovoAgendamento();
            case "Meus agendamentos": return fluxoMeusAgendamentos();
            case "Cancelar agendamento": return fluxoCancelarAgendamento();
            default: return super.escolherAcao(acao);
        }
    }
    
    private Interfaciavel fluxoNovoAgendamento(){
        
        try{
                    
            Agendamento novoAgendamento = new Agendamento(this);
            novoAgendamento.confirmar();

            getAgendamentos().add(novoAgendamento);
            Collections.sort(getAgendamentos(), new Comparator<Agendamento>() {
                @Override
                public int compare(Agendamento a, Agendamento b){
                    return a.getData().compareTo(b.getData());
                }
            });

            UBS.getInstance().salvarContexto();

        }catch(AgendamentoCanceladoException e){
            UBS.getInstance().ui.mostraLinha("O agendamento foi cancelado.");
        }
        
        return this;
    }
    
    private Interfaciavel fluxoMeusAgendamentos(){
        
        if(proximosAgendamentos().isEmpty()){
            UBS.getInstance().ui.mostraLinha("Você não tem consultas agendadas, " + nome + ".");
            return this;
        }
        
        UBS.getInstance().ui.mostraLinha("Estas são suas próximas consultas agendadas:");
        for (Agendamento agendamento : proximosAgendamentos()) {
            UBS.getInstance().ui.mostraLinha(agendamento.descricao());
        }
        
        return this;
    }
    
    private Interfaciavel fluxoCancelarAgendamento(){
        
        if(proximosAgendamentos().isEmpty()){
            UBS.getInstance().ui.mostraLinha("Você não tem consultas agendadas, " + nome + ".");
            return this;
        }
        
        ArrayList<Agendamento> proximos = proximosAgendamentos();
        ArrayList<String> opcoes = new ArrayList<>();
        for (Agendamento proximo : proximos) opcoes.add(proximo.descricao());
        opcoes.add("Cancelar");
        
        String opcao = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(opcoes);
        if ("Cancelar".equals(opcao)) return this;
        
        int index = opcoes.indexOf(opcao);
        Agendamento agendamentoEscolhido = proximos.get(index);
        
        UBS.getInstance().ui.mostraLinha("Confirma o cancelamento do agendamento abaixo?");
        UBS.getInstance().ui.mostraLinha(agendamentoEscolhido.descricao());
        
        String confirmacao = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(new String[]{"SIM, cancele esse agendamento","NÃO, voltar ao menu de opções"});
        if ("NÃO, voltar ao menu de opções".equals(confirmacao)) return this;
        
        getAgendamentos().remove(agendamentoEscolhido);
        
        UBS.getInstance().ui.mostraLinha("O agendamento foi cancelado.\nObrigado por avisar com antecedência.");
        UBS.getInstance().salvarContexto();
        
        return this;
    }
    
    private ArrayList<Agendamento> proximosAgendamentos(){
        ArrayList<Agendamento> proximos = new ArrayList<>();
        for (Agendamento agendamento : getAgendamentos()) {
            if(agendamento.getData().after(new Date())){
                proximos.add(agendamento);
            }
        }
        return proximos;
    }

    public String descricao(){
        return "# " + sobrenome.toUpperCase() + ", " + nome + " (nascimento: " + getDataDeNascimento() + ") #";
    }
    
    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }
}
