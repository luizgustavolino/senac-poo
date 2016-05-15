/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs.ui;

import java.util.List;

/**
 *
 * @author luizgustavolino
 */
public class UserInterface {
    
    private UserInterfaceHandler handlerAtual;
    private String contextoAtual;
    
    public UserInterface(UserInterfaceHandler handler){
        handlerAtual = handler;
        chamarProximasAcoes();
    }
    
    private void chamarProximasAcoes(){
        
        List<String> acoes =  handlerAtual.acoesDisponiveis(contextoAtual);
        
        System.out.println("Escolha uma opção:");
        for (int i = 0; i < acoes.size(); i++) {
            String acao = acoes.get(i);
            System.out.println("    "+(i+1) + ". "+acao);
        }
        
        
    }
    
}
