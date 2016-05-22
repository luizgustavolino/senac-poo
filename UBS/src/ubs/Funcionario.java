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
public abstract class Funcionario extends Usuario {
    private AcessoProntuario[] prontuarios;

    public Funcionario() {
        super();
    }

    public String descricao(){
         return "(" + this.getClass().getSimpleName() + ") " + nomeCompleto();
    }
    
    public String nomeCompleto(){
        return sobrenome.toUpperCase() + ", " + nome;
    }
    
    public String nomeCompletoComEmail(){
        return nomeCompleto()+" ("+email+")";
    }
}
