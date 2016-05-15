/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.Arrays;
import java.util.List;
import ubs.exceptions.UsuarioIncorretoException;
import ubs.ui.Interfaciavel;

/**
 *
 * @author luizgustavolino
 */

public class Usuario implements Interfaciavel{
    
    //private int id;
    private String nome;
    private String sobrenome;
    private String senha;
    private static Usuario atual;
    
    public static Usuario acessar() throws UsuarioIncorretoException{
        
        UBS.getInstance().ui.mostra("Usuário: ");
        String usuario = UBS.getInstance().ui.pedeString();
        
        UBS.getInstance().ui.mostra("Senha: ");
        String senha = UBS.getInstance().ui.pedeString();
        
        List<Usuario> todos = UBS.getInstance().getUsuarios();
        for (Usuario umUsuario : todos) {
            if(umUsuario.validarLogin(usuario, senha)){
                atual = umUsuario;
                return atual;
            }
        }
        
        throw new UsuarioIncorretoException();
    }
    
    public static Usuario cadastrar() {
        
        UBS.getInstance().ui.mostra("Seu nome: ");
        String nome = UBS.getInstance().ui.pedeString();
        
        UBS.getInstance().ui.mostra("Seu sobrenome: ");
        String sobrenome = UBS.getInstance().ui.pedeString();
        
        UBS.getInstance().ui.mostra("Sua senha: ");
        String senha = UBS.getInstance().ui.pedeString();
        
        return new Usuario(nome, sobrenome, senha);
    }
    
    public static Usuario usuarioAtual(){
        return atual;
    }
    
    public void encerrarSessao(){
        atual = null;
        UBS.getInstance().salvarContexto();
    }
    
    protected Usuario(String nome, String sobrenome, String senha){
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.senha = senha;
    }
    
    private Boolean validarLogin(String usuario, String senha){
        return this.nome.equals(nome) && this.senha.equals(senha);
    }

    @Override
    public List<String> acoesDisponiveis(String contexto) {
        return Arrays.asList("Agendar");
    }

    @Override
    public Interfaciavel escolherAcao(String acao) {
        return this;
    }
}
