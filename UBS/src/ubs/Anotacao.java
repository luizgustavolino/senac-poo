/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.Date;

/**
 *
 * @author luizgustavolino
 */
public class Anotacao {
    
    private Date data;
    private Prontuario prontuario; 
    private Funcionario autor;
    
    private String texto;
    
    public Anotacao(Prontuario prontuario, Funcionario autor){
        this.prontuario = prontuario;
        this.autor = autor;
        this.data = new Date();
    }
    
    public void editar(String novoTexto){
        //to do
    }
    
    public String resumo(){
        //to do
        return " ";
    }
}
