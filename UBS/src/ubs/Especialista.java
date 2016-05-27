/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ubs.extensions.DateHelpers;


/**
 *
 * @author luizgustavolino
 */
public abstract class Especialista extends Funcionario{
    
    private List<Agendamento> agendamentos; 
    
    public static List<Especialista> todos(){
        return todos(Especialista.class);
    }
    
    protected static List<Especialista> todos(Class especialidade){
        
        List<Especialista> todosEspecialistas = new ArrayList<>();
        List<Usuario> todos = UBS.getInstance().getUsuarios();
        
        for(Usuario usuario : todos){
            if(usuario.getClass().equals(especialidade)){
                todosEspecialistas.add((Especialista)usuario);
            }
        }

        return todosEspecialistas;
    }
    
    public void adicionarAgendamento(Agendamento agendamento){
        if(agendamentos == null) agendamentos = new ArrayList<>();
        agendamentos.add(agendamento);
    }
    
    public void removerAgendamento(Agendamento agendamento){
        try{
            agendamentos.remove(agendamento);
        }catch(Exception e){
            UBS.getInstance().ui.mostraLinha("(!) Um agendamento marcado como cancelado não foi encontrado.");
        }
    }
    
    public List<Agendamento> agendamentosNoDia(Date dia){
        
        ArrayList<Agendamento> noDia = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            if(DateHelpers.isSameDay(agendamento.getData(), dia))
                noDia.add(agendamento);
        }
        
        return noDia;
    }
    
    public Especialista() {
        super();
    }
}
