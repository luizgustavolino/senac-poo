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
    
    public String dataDeNascimento;
    private final List<Agendamento> agendamentos; 

    public Paciente() {
        
        super();
        
        do{
            
            UBS.getInstance().ui.mostraLinha("Digite sua data de nascimento (dia/mês/ano): ");
            String dataDigitada = UBS.getInstance().ui.pedeString();
            DateFormat formatador = new SimpleDateFormat("d/M/yyyy");
            Date dataEscolhida;

            try {
                dataEscolhida = formatador.parse(dataDigitada);
                String dataEntendida = formatador.format(dataEscolhida);
                if(dataEntendida.equals(dataDigitada)){
                    dataDeNascimento = dataEntendida;
                }
            }catch(ParseException e){
                // erro na digitação
            }finally{
                if(dataDeNascimento == null){
                    UBS.getInstance().ui.mostra("Desculpe, data inválida. ");
                }
            }
            
        }while(dataDeNascimento == null);
        
        agendamentos = new ArrayList<>();
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

            agendamentos.add(novoAgendamento);
            Collections.sort(agendamentos, new Comparator<Agendamento>() {
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
        
        UBS.getInstance().ui.mostraLinha("Estas são suas próximas consultas agendadas:");
        for (Agendamento agendamento : proximosAgendamentos()) {
            UBS.getInstance().ui.mostraLinha(agendamento.descricao());
        }
        
        return this;
    }
    
    private Interfaciavel fluxoCancelarAgendamento(){
        
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
        
        agendamentos.remove(agendamentoEscolhido);
        
        UBS.getInstance().ui.mostraLinha("O agendamento foi cancelado.\nObrigado por avisar com antecedência.");
        UBS.getInstance().salvarContexto();
        
        return this;
    }
    
    private ArrayList<Agendamento> proximosAgendamentos(){
        ArrayList<Agendamento> proximos = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            if(agendamento.getData().after(new Date())){
                proximos.add(agendamento);
            }
        }
        return proximos;
    }
}
