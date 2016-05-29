/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ubs.exceptions.PrivilegiosInsulficientesException;
import ubs.ui.Interfaciavel;

/**
 *
 * @author luizgustavolino
 */
public class Medico extends Especialista{

    public static List<Especialista> todos(){
        return todos(Medico.class);
    }
    
    @Override
    public void atender(Paciente p) {
        realizarCheckList(p);
    }
     
    private void realizarCheckList(Paciente paciente){
        try {
            paciente.getProntuario().adicionarNota("Atendimento iniciado");
            //UBS.getInstance().ui.mostraLinha("O paciente esta bem?");
            //UBS.getInstance().ui.pedeEscolhaEntreOpcoes("")
        } catch (PrivilegiosInsulficientesException ex) { }
    }
    
    private String pedirExames(Paciente paciente){
        //to do
        return " ";
    }
    
}

//From :
//http://www.tuasaude.com/sintomas-causados-pelo-zika-virus/
//http://www.cdc.gov/chikungunya/symptoms/index.html
//http://www.dengue.pr.gov.br/modules/conteudo/conteudo.php?conteudo=5