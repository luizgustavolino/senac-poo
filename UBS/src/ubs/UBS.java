/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        ui = new InterfaceDoUsuario();
        carregarContexto();
        ui.iniciar(this);
    }
    
    private void carregarContexto(){
        // From: x-stream.github.io/tutorial.html (may, 15)
        xstream = new XStream(new StaxDriver());

        try{
            File xmlFile = new File("ubs_db.xml");
            if(xmlFile.exists() && !xmlFile.isDirectory()) { 
                usuarios = (List<Usuario>) xstream.fromXML(xmlFile);
            }else{
                usuarios = new ArrayList<>();
            }
        }catch(XStreamException e){
            throw new RuntimeException("Erro ao carregar os dados da UBS!");
        }
    }
    
    public void salvarContexto(){
        
        // From: http://stackoverflow.com/questions/13063815/save-xml-file-with-xstream (may, 15)
        FileOutputStream fos = null;
        
        try {
            fos = new FileOutputStream("ubs_db.xml");
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
    
    public void registrarUsuario(Usuario novoUsuario){
        usuarios.add(novoUsuario);
        salvarContexto();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    @Override
    public ArrayList<String> acoesDisponiveis(String contexto) {
        ArrayList<String> acoes = new ArrayList<>();
        acoes.addAll(Arrays.asList("Login", "Cadastro", "Encerrar"));
        return acoes;
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
            case "Cadastro": return new Cadastro();
            case "Encerrar":
            default:
                return null;
        }
    }
    
}
