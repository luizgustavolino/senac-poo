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
public class Paciente extends Usuario{
    
    public String dataDeNascimento;
    private Agendamento[] agendamentos; 

    public Paciente(String email, String nome, String sobrenome, String senha) {
        super(email, nome, sobrenome, senha);
    }

}
