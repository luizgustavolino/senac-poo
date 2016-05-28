/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author luizgustavolino
 */
public class Dentista extends Especialista{

    public static List<Especialista> todos(){
        return todos(Dentista.class);
    }
    
    public Dentista() {
        super();
    }
    
    
    public String realizarProcedimentos(Paciente paciente){
        //to do
        return " ";
    }
}
