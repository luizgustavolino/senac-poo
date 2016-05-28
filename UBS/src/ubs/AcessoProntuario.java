/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import ubs.exceptions.PrivilegiosInsulficientesException;

/**
 *
 * @author luizgustavolino
 */
public class AcessoProntuario extends Prontuario{

    private final Funcionario funcionario;
    
    public static AcessoProntuario doPaciente(Paciente paciente) throws PrivilegiosInsulficientesException{
        
        Funcionario funcionarioAtual = (Funcionario) Usuario.usuarioAtual();
        
        if(funcionarioAtual.getClass().equals(Medico.class) || funcionarioAtual.getClass().equals(Dentista.class)){
            return new AcessoProntuario(paciente, funcionarioAtual);
        }
        
        throw new PrivilegiosInsulficientesException();
    }
    
    private AcessoProntuario(Paciente paciente, Funcionario funcionario) {
        super(paciente);
        this.funcionario = funcionario;
    }
    
    public void adicionarNota(){
        
    }
}
