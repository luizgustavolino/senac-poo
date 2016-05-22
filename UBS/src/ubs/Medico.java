/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luizgustavolino
 */
public class Medico extends Especialista{

    public static List<Especialista> todos(){
        return todos(Medico.class);
    }
    
    public Medico() {
        super();
    }
    
    public String realizarCheckList(Paciente paciente){
        //to do
        return " ";
    }
    
    private String pedirExames(Paciente paciente){
        //to do
        return " ";
    }
}
