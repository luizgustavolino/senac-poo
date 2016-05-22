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
public abstract class Especialista extends Funcionario{
    
    private Agendamento[] agendamentos; 
    
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
    
    public Especialista() {
        super();
    }
}
