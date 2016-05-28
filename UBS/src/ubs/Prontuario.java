/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;
import ubs.exceptions.PrivilegiosInsulficientesException;

/**
 *
 * @author luizgustavolino
 */
public class Prontuario {
    
    protected ArrayList<Anotacao> anotacoes;
    
    public Prontuario(Paciente paciente){
        anotacoes = new ArrayList<>();
    }
    
    public void adicionarNota(String texto) throws PrivilegiosInsulficientesException{
        Usuario usuario = Usuario.atual;
        if(usuario instanceof Funcionario){
            anotacoes.add(new Anotacao(texto, (Funcionario) usuario));
            UBS.getInstance().salvarContexto();
        }else{
            throw new PrivilegiosInsulficientesException();
        }
    }
    
    public String resumo(){
        //to do
        return " ";
    }
}
