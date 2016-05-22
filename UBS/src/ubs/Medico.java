/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luizgustavolino
 */
public class Medico extends Especialista{

    public static List<Especialista> todos(){
        return todos(Medico.class);
    }
    
    public Medico() {
        super();
    }
    
    public String realizarCheckList(Paciente paciente){
        //UBS.getInstance().ui.mostraLinha("");
        //UBS.getInstance().ui.pedeEscolhaEntreOpcoes("")
        return " ";
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