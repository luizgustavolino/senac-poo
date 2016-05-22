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
    private List<Agendamento> agendamentos; 

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
    public List<String> acoesDisponiveis(String contexto) {
        return Arrays.asList("Novo agendamento", "Cancelar agendamento", "Meus agendamentos", "Consultar meu prontuário", "Logout");
    }
    
    @Override
    public Interfaciavel escolherAcao(String acao) {
        
        switch(acao){
            case "Novo agendamento":
                try{
                    Agendamento novoAgendamento = new Agendamento(this);
                    agendamentos.add(novoAgendamento);
                }catch(AgendamentoCanceladoException e){
                    UBS.getInstance().ui.mostraLinha("O agendamento foi cancelado.");
                }
        }
        
        return this;
    }
}
