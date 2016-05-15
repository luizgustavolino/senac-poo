/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.util.Arrays;
import java.util.List;
import ubs.ui.UserInterface;
import ubs.ui.UserInterfaceHandler;

/**
 *
 * @author luizgustavolino
 */
public class UBS implements UserInterfaceHandler {
    
    // From: www.devmedia.com.br/trabalhando-com-singleton-java/23632 (may, 15)
    private static final UBS INSTANCE = new UBS();

    private List<Usuario> usuarios;
    private XStream xstream;
    private UserInterface ui;
    
    public static UBS getInstance(){
        return INSTANCE;
    }
    
    public static void main(String[] args) {
        UBS.getInstance().startProgram();
    }
    
    private void startProgram(){
        // From: x-stream.github.io/tutorial.html (may, 15)
        xstream = new XStream(new StaxDriver());
        ui = new UserInterface(this);
    }
    
    public void saveContext(){
        String xml = xstream.toXML(getUsuarios());
        System.out.print(xml);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    @Override
    public List<String> acoesDisponiveis(String contexto) {
        return Arrays.asList("Login", "Cadastrar", "Encerrar");
    }

    @Override
    public UserInterfaceHandler escolherAcao(String acao) {
        System.out.print("Escolhi: "+ acao);
        return this;
    }
    
}
