/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs.ui;

import java.util.List;
import java.util.Scanner;

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
        Scanner entrada = new Scanner(System.in);
        String  escolhida = null;
        
        do{
            
            System.out.println("Escolha uma opção:");
            for (int i = 0; i < acoes.size(); i++) {
                String acao = acoes.get(i);
                mostraLinha("    "+(i+1) + ". "+acao);
            }
            
            mostra("> ");
            
            int selecao = entrada.nextInt();
            if(selecao > 0 && selecao <= acoes.size()){
                escolhida = acoes.get(selecao - 1);
            }else{
                mostra("Opção '" + selecao + "' inválida! ");
            }
        }while(escolhida == null);
        
        handlerAtual = handlerAtual.escolherAcao(escolhida);
        if(handlerAtual != null) chamarProximasAcoes();
    }
    
    public String pedeString(){
        Scanner entrada = new Scanner(System.in);
        return entrada.next();
    }
    
    public void mostra(String texto){
        System.out.print(texto);
    }
    
    public void mostraLinha(String texto){
        mostra(texto + "\n");
    }
    
}
