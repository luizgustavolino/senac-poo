/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;
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
    protected String email;
    protected String nome;
    protected String sobrenome;
    protected String senha;
    protected static Usuario atual;
    
    public static Usuario acessar() throws UsuarioIncorretoException{
        
        UBS.getInstance().ui.mostra("Email: ");
        String usuario = UBS.getInstance().ui.pedeString();
        
        UBS.getInstance().ui.mostra("Senha: ");
        String senha = UBS.getInstance().ui.pedeString();
        
        List<Usuario> todos = UBS.getInstance().getUsuarios();
        for (Usuario umUsuario : todos) {
            if(umUsuario.validarLogin(usuario, senha)){
                atual = umUsuario;
                UBS.getInstance().ui.mostraLinha("Bem vindo a UBS, " + atual.getNome() + ".");
                return atual;
            }
        }
        
        throw new UsuarioIncorretoException();
    }
    
    public Usuario() {
        
        boolean emailDisponivel;
        
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
        nome = UBS.getInstance().ui.pedeString();
        
        UBS.getInstance().ui.mostra("Seu sobrenome: ");
        sobrenome = UBS.getInstance().ui.pedeString();
        
        UBS.getInstance().ui.mostra("Sua senha: ");
        senha = UBS.getInstance().ui.pedeString();
     
    }
    
    public static Usuario usuarioAtual(){
        return atual;
    }
    
    private void encerrarSessao(){
        UBS.getInstance().ui.mostraLinha("Acesso finalizado, " + atual.getNome() + ". Até logo.");
        atual = null;     
    }
    
    private Boolean validarLogin(String emailUsuario, String senha){
        return this.email.equals(emailUsuario) && this.senha.equals(senha);
    }

    @Override
    public ArrayList<String> acoesDisponiveis(String contexto) {
        ArrayList<String> acoes = new ArrayList<>();
        acoes.addAll(Arrays.asList("Logout"));
        return acoes;
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
    
    public String nomeCompleto(){
        return getSobrenome().toUpperCase() + ", " + getNome();
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }
}
