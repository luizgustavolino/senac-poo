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
public class Especialista extends Funcionario{
    private Agendamento[] agendamentos; 
    
    public static Especialista[] todos(){
        return new Especialista[0];
    }

    public Especialista(String email, String nome, String sobrenome, String senha) {
        super(email, nome, sobrenome, senha);
    }

  
}
