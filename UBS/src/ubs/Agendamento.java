/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;
import java.util.List;
import ubs.exceptions.AgendamentoCanceladoException;

/**
 *
 * @author luizgustavolino
 */
public class Agendamento {
    private Data data;
    private Paciente paciente;
    private Especialista especialista;
    
    public Agendamento(Paciente paciente) throws AgendamentoCanceladoException {
        
        this.paciente = paciente;
        
        List<Especialista> especialistas;
        String[] especialidades = {"Médico", "Dentista", "Cancelar"};
        String especialidade = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(especialidades);
        
        switch(especialidade){
            case "Médico":
                especialistas = Medico.todos();
                UBS.getInstance().ui.mostra("Selecionando entre médicos. ");
                break;
            case "Dentista":
                especialistas = Dentista.todos();
                UBS.getInstance().ui.mostra("Selecionando entre dentistas. ");
                break;
            case "Cancelar": throw new AgendamentoCanceladoException();
            default:
                UBS.getInstance().ui.mostraLinha("Não foi possível encontrar funcionário para especialidade escolhida.");
                throw new AgendamentoCanceladoException();
        }
        
        List<String> opcoesDeEspecialistas = new ArrayList<>();
        for (Especialista umEspecialista : especialistas) opcoesDeEspecialistas.add(umEspecialista.nomeCompletoComEmail());
        opcoesDeEspecialistas.add("Cancelar");
        
        String[] stringsDeOpcoesDeEspecialistas = opcoesDeEspecialistas.toArray(new String[opcoesDeEspecialistas.size()]);
        String especialistaEscolhido = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(stringsDeOpcoesDeEspecialistas);
        if(especialistaEscolhido.equals("Cancelar")) throw new AgendamentoCanceladoException();
        
        int indexDoEspecialista = opcoesDeEspecialistas.indexOf(especialistaEscolhido);
        especialista = especialistas.get(indexDoEspecialista);
        
        
    }
    
    public static Agendamento agendar(){
        //to do
        return null;
    }
    
    public void cancelar(){
        
    }
}
