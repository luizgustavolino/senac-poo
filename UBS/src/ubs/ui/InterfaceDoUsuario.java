/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs.ui;

import java.awt.Robot;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import ubs.UBS;
import ubs.extensions.DateHelpers;

/**
 *
 * @author luizgustavolino
 */
public class InterfaceDoUsuario {
    
    private Interfaciavel handlerAtual;
    private String contextoAtual;
    
    public void iniciar(Interfaciavel handler){
        handlerAtual = handler;
        chamarProximasAcoes();
    }
    
    private void chamarProximasAcoes(){ 
        List<String> acoes =  handlerAtual.acoesDisponiveis(contextoAtual);
        String  escolhida = pedeEscolhaEntreOpcoes(acoes);
        handlerAtual = handlerAtual.escolherAcao(escolhida);
        if(handlerAtual != null) chamarProximasAcoes();
    }
    
    public String pedeString(){
        Scanner entrada = new Scanner(System.in);
        return entrada.nextLine();
    }
 
    public void mostra(String texto){
        System.out.print(texto);
    }
    
    public void mostraLinha(String texto){
        mostra(texto + "\n");
    }
    
    public String pedeEscolhaEntreOpcoes(String[] acoes){
        List<String> acoesList = Arrays.asList(acoes);
        return pedeEscolhaEntreOpcoes(acoesList);
    }
    
    public String pedeEscolhaEntreOpcoes(List<String> acoes){
        
        String  escolhida = null;
        
        do{
            
            System.out.println("Escolha uma opção:");
            for (int i = 0; i < acoes.size(); i++) {
                String acao = acoes.get(i);
                mostraLinha("["+(i+1) + "] "+acao);
            }
            
            Scanner entrada = new Scanner(System.in);
            int selecao;
            mostra("> ");
            
            if (entrada.hasNextInt()) {
                selecao = entrada.nextInt();
                if(selecao > 0 && selecao <= acoes.size()){
                    escolhida = acoes.get(selecao - 1);
                }else{
                    mostra("Opção '" + selecao + "' inválida! ");
                }
            }else{
                mostra("Opção precisa ser numérica! ");
            }
        }while(escolhida == null);
        
        mostra("\n");
        return escolhida;
    }
    
    public Date pedeUmaData(){
        
        Date dataEscolhida = null;
        do{
            String dataDigitada = UBS.getInstance().ui.pedeString();
            DateFormat formatador = new SimpleDateFormat("d/M/yyyy");
            
            try {
                dataEscolhida = formatador.parse(dataDigitada);
            }catch(ParseException e){
                // erro na digitação
            }finally{
                if(dataEscolhida == null){
                    UBS.getInstance().ui.mostra("Desculpe, data inválida. ");
                }
            }
            
        }while(dataEscolhida == null);
        return dataEscolhida;
    }
    
    public Date pedeUmaDataFutura(){
        Date dataEscolhida = null;
        boolean dataValida = false;
        do{
            dataEscolhida = pedeUmaData();
            if(DateHelpers.isFutureDay(dataEscolhida)){
                dataValida = true;
            }else{
                UBS.getInstance().ui.mostraLinha("Por favor, digite uma data futura.");
            }
        }while(dataValida != true);
        return dataEscolhida;
    }
}
