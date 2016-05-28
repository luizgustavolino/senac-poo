/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ubs.ui.Interfaciavel;

/**
 *
 * @author luizgustavolino
 */
public class Cadastro implements Interfaciavel{

    @Override
    public ArrayList<String> acoesDisponiveis(String contexto) {
        ArrayList<String> acoes = new ArrayList<>();
        acoes.addAll(Arrays.asList("Paciente", "Médico", "Dentista", "Enfermeiro", "Cancelar"));
        return acoes;
    }

    @Override
    public Interfaciavel escolherAcao(String acao) {
        
        Usuario usr = null;
        
        switch(acao){
            case "Paciente": usr = new Paciente(); break;
            case "Médico": usr = new Medico(); break;
            case "Dentista": usr = new Dentista(); break;
            case "Enfermeiro": usr =  new Enfermeiro(); break;
        }
        
        if (usr != null) {
            
            UBS.getInstance().ui.mostraLinha("Confirma as informações abaixo?");
            UBS.getInstance().ui.mostraLinha("- " + usr.sobrenome.toUpperCase() + ", " + usr.nome);
            UBS.getInstance().ui.mostraLinha("- email: "+usr.email);
            
            String confirmacao = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(new String[]{"SIM, confirmo", "NÃO, cancelar"});
            
            if(confirmacao.equals("SIM, confirmo")){
                UBS.getInstance().registrarUsuario(usr);
                UBS.getInstance().ui.mostraLinha("Cadastro concluído, "+usr.getNome()+".\nVocê já pode utilizar a opção 'Login'.");
            }else{
                UBS.getInstance().ui.mostraLinha("Seu cadastro foi cancelado.");
            }
        }
        
        return UBS.getInstance();
    }
}
