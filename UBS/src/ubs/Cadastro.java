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
            UBS.getInstance().registrarUsuario(usr);
            return usr;
        }else{
            return UBS.getInstance();
        }
    }
}
