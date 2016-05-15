/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.jfr.events.FileWriteEvent;
import ubs.exceptions.UsuarioIncorretoException;
import ubs.ui.InterfaceDoUsuario;
import ubs.ui.Interfaciavel;

/**
 *
 * @author luizgustavolino
 */
public class UBS implements Interfaciavel {
    
    // From: www.devmedia.com.br/trabalhando-com-singleton-java/23632 (may, 15)
    private static final UBS INSTANCE = new UBS();

    private List<Usuario> usuarios;
    private XStream xstream;
    public InterfaceDoUsuario ui;
    
    public static void main(String[] args) {
        UBS.getInstance().startProgram();
    }
    
    public static UBS getInstance(){
        return INSTANCE;
    }
    
    private void startProgram(){
        carregarContexto();
        ui = new InterfaceDoUsuario();
        ui.iniciar(this);
    }
    
    private void carregarContexto(){
        
        // From: x-stream.github.io/tutorial.html (may, 15)
        xstream = new XStream(new StaxDriver());
        usuarios = new ArrayList<>();
        
    }
    
    public void salvarContexto(){
        
        FileOutputStream fos = null;
        
        try {
            fos = new FileOutputStream("ubs_db.xml");
            fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
            String xml = xstream.toXML(getUsuarios());
            byte[] bytes = xml.getBytes("UTF-8");
            fos.write(bytes);
        } catch(Exception e) {
            UBS.getInstance().ui.mostraLinha("Atenção: error ao salvar o arquivo");
        } finally {
            try{
                if(fos!=null) fos.close();
            } catch (IOException e) {
                UBS.getInstance().ui.mostraLinha("Atenção: arquivo foi salvo, mas não foi possível fecha-lo.");
            }
        }

    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    @Override
    public List<String> acoesDisponiveis(String contexto) {
        return Arrays.asList("Login", "Cadastro", "Encerrar");
    }

    @Override
    public Interfaciavel escolherAcao(String acao) {
        switch(acao){
            case "Login":
                try {
                    return Usuario.acessar();
                } catch (UsuarioIncorretoException ex) {
                    UBS.getInstance().ui.mostraLinha("Usuário ou senha incorreto!");
                    return this;
                }
            case "Cadastro":
                Usuario novoUsuario = Usuario.cadastrar();
                usuarios.add(novoUsuario);
                UBS.getInstance().salvarContexto();
                UBS.getInstance().ui.mostraLinha("Cadastro realizado. Seu login é ");
                return this;
            case "Encerrar":
            default:
                return null;
        }
    }
    
}
