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
public class Funcionario extends Usuario {
    private AcessoProntuario[] prontuarios;

    public Funcionario(String nome, String sobrenome, String senha) {
        super(nome, sobrenome, senha);
    }
    
    public String descricao(){
        //to do
        return " ";
    } 
}
