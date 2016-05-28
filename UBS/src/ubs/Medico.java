/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ubs.ui.Interfaciavel;

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
    
    @Override
    public ArrayList<String> acoesDisponiveis(String contexto) {
        ArrayList<String> acoes = new ArrayList<>();
        acoes.addAll(Arrays.asList("Atender próximo paciente"));
        acoes.addAll(super.acoesDisponiveis(contexto));
        return acoes;
    }
    
    @Override
    public Interfaciavel escolherAcao(String acao) {
        switch(acao){
            case "Atender próximo paciente":
                try{
                    Paciente p = selecionaProximoPaciente();
                }catch(Exception e){
                    
                }
                return this;
                
            default: return super.escolherAcao(acao);
        }
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