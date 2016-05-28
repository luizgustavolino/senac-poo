/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

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
    
    public void editar(){
        
        if(!Usuario.atual.equals(this.autor)){
            
            UBS.getInstance().ui.mostraLinha("(!) As anotações do prontuário só podem ser editadas pelo autor.");
            
        }else{
            
            UBS.getInstance().ui.mostraLinha("Insira o novo texto:");
            String novoTexto = UBS.getInstance().ui.pedeString();
            UBS.getInstance().ui.mostraLinha("Tem certeza que deseja editar o texto?");
            String confirma = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(new String[]{"Sim, editar.","Não, cancelar a edição."});

            if(confirma.equals("Sim, editar.")){
                this.texto = novoTexto;
                UBS.getInstance().salvarContexto();
                UBS.getInstance().ui.mostraLinha("A anotação foi editada.");
            }else{
                UBS.getInstance().ui.mostraLinha("A edição foi cancelada.");
            }
            
        }
    }
    
    public String resumo(){
        DateFormat formatador = new SimpleDateFormat("hh':'mm");
        return formatador.format(data) + " - " + getAutor().descricao() + ":\n"+getTexto();
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
