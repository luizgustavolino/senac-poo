/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;

/**
 *
 * @author luizgustavolino
 */
public class Prontuario {
    
    protected final Paciente paciente;
    protected ArrayList<Anotacao> anotacao;
    
    public Prontuario(Paciente paciente){
        this.paciente = paciente;
    }
    
    public String resumo(){
        //to do
        return " ";
    }
}
