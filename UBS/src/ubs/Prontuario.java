/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import ubs.exceptions.PrivilegiosInsulficientesException;
import ubs.extensions.DateHelpers;

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
    
    public void visualizar(){
        
        if(anotacoes.isEmpty()){
            UBS.getInstance().ui.mostraLinha("(!) Prontuário sem anotações");
        }else{
            
            DateFormat formatador = new SimpleDateFormat("dd/MM");
            Date diaAtual = null;
                        
            for (Anotacao nota : anotacoes) {
                if(!DateHelpers.isSameDay(nota.getData(), diaAtual)){
                    
                    if(diaAtual != null){
                        UBS.getInstance().ui.mostraLinha("--- Fim das anotações do dia "+formatador.format(diaAtual)+" ---");
                        String confirmacao = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(new String[]{
                            "Ver o próximo dia ("+formatador.format(nota.getData())+")", "Voltar ao menu"}
                        );
                        if ("Voltar ao menu".equals(confirmacao)) return;
                    }
                    
                    UBS.getInstance().ui.mostraLinha("--- No dia "+formatador.format(nota.getData())+" ---------------------");
                    diaAtual = nota.getData();
                }
                UBS.getInstance().ui.mostraLinha(nota.resumo());
            }
            
            UBS.getInstance().ui.mostraLinha("--- FIM do prontuário ----------------");
        }
    }
}
