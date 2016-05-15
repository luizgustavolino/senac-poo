/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.List;
import ubs.exceptions.UsuarioIncorretoException;

/**
 *
 * @author luizgustavolino
 */

public class Usuario {
    
    //private int id;
    private String nome;
    private String sobrenome;
    private String senha;
    private static Usuario atual;
    
    public static Usuario Acessar(String usuario, String senha)
            throws UsuarioIncorretoException{
        
        List<Usuario> todos = UBS.getInstance().getUsuarios();
        for (Usuario umUsuario : todos) {
            if(umUsuario.validarLogin(usuario, senha)){
                atual = umUsuario;
                return atual;
            }
        }
        
        throw new UsuarioIncorretoException();
    }
    
    public static Usuario usuarioAtual(){
        return atual;
    }
    
    public void encerrarSessao(){
        atual = null;
        UBS.getInstance().saveContext();
    }
    
    private Boolean validarLogin(String usuario, String senha){
        return this.nome.equals(nome) && this.senha.equals(senha);
    }
}
