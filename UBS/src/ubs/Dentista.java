/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ubs.exceptions.PrivilegiosInsulficientesException;

/**
 *
 * @author luizgustavolino
 */
public class Dentista extends Especialista{

    public static List<Especialista> todos(){
        return todos(Dentista.class);
    }
    
    public void realizarProcedimentos(Paciente paciente){
        UBS.getInstance().ui.mostraLinha("Quais os procedimentos realizados no paciente?");
        String opcao = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(new String[]{"Limpeza dos dentes", "Obturação dos dentes"});
        switch(opcao){
            case "Limpeza dos dentes":
               try{
                paciente.getProntuario().adicionarNota("Dentes limpos");
               }catch(PrivilegiosInsulficientesException ex){}
                break;
            case "Obturação dos dentes":
                try{
                paciente.getProntuario().adicionarNota("Dentes obturados");
               }catch(PrivilegiosInsulficientesException ex){}
                break;
        }
    }

    @Override
    public void atender(Paciente p) {        
        realizarProcedimentos(p);
    }
    

}
