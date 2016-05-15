/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

/**
 *
 * @author luizgustavolino
 */
public class Agendamento {
    private Data data;
    private Paciente paciente;
    private Especialista especialista;
    
    private Agendamento(Data data, Paciente paciente, Especialista especialista){
        
    }
    
    public static Agendamento agendar(){
        //to do
        return new Agendamento(new Data(), new Paciente(), new Especialista());
    }
    
    public void cancelar(){
        
    }
}
