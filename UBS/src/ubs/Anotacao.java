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
    private Funcionario autor;
    private String texto;
    
    public Anotacao(String texto, Funcionario autor){
        this.texto = texto;
        this.autor = autor;
        this.data = new Date();
    }
    
    public void editar(String novoTexto){
        //to do
    }
    
    public String resumo(){
        return getAutor().descricao() + ": "+getTexto();
    }

    public Date getData() {
        return data;
    }

    public Funcionario getAutor() {
        return autor;
    }

    public String getTexto() {
        return texto;
    }
}
