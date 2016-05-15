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
    private String email;
    private String nome;
    private String sobrenome;
    private String senha;
    private static Usuario atual;
    
    public static Usuario acessar() throws UsuarioIncorretoException{
        
        UBS.getInstance().ui.mostra("Email: ");
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
        
        boolean emailDisponivel;
        String email;
        
        do{
            
            emailDisponivel = true;
            UBS.getInstance().ui.mostra("Seu email: ");
            email = UBS.getInstance().ui.pedeString();

            List<Usuario> todos = UBS.getInstance().getUsuarios();
            for(Usuario usuario : todos){
                if(usuario.getEmail().equals(email)){
                    UBS.getInstance().ui.mostraLinha("Usuario já cadastrado!");
                    emailDisponivel = false;
                    break;
                }   
            }
        }while(!emailDisponivel);
        
        UBS.getInstance().ui.mostra("Seu primeiro nome: ");
        String nome = UBS.getInstance().ui.pedeString();
        
        UBS.getInstance().ui.mostra("Seu sobrenome: ");
        String sobrenome = UBS.getInstance().ui.pedeString();
        
        UBS.getInstance().ui.mostra("Sua senha: ");
        String senha = UBS.getInstance().ui.pedeString();
        
        Usuario novo = new Usuario(email, nome, sobrenome, senha);
        UBS.getInstance().registrarUsuario(novo);
        return novo;
    }
    
    public static Usuario usuarioAtual(){
        return atual;
    }
    
    private void encerrarSessao(){
        atual = null;     
    }
    
    protected Usuario(String email, String nome, String sobrenome, String senha){
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.senha = senha;
    }
    
    private Boolean validarLogin(String usuario, String senha){
        return this.email.equals(email) && this.senha.equals(senha);
    }

    @Override
    public List<String> acoesDisponiveis(String contexto) {
        return Arrays.asList("Agendar", "Logout");
    }

    @Override
    public Interfaciavel escolherAcao(String acao) {
        
        switch(acao){
            case "Logout":
                encerrarSessao();
                return UBS.getInstance();
        }
        
        return this;
    }

    public String getEmail() {
        return email;
    }
}
