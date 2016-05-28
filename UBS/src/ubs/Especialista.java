/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import ubs.extensions.DateHelpers;
import ubs.exceptions.NenhumPacienteEscolhidoException;

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
        Collections.sort(agendamentos, new Comparator<Agendamento>() {
            @Override
            public int compare(Agendamento a, Agendamento b){
                return a.getData().compareTo(b.getData());
            }
        });
    }
    
    public void removerAgendamento(Agendamento agendamento){
        try{
            agendamentos.remove(agendamento);
        }catch(Exception e){
            UBS.getInstance().ui.mostraLinha("(!) Um agendamento marcado como cancelado não foi encontrado.");
        }
    }
    
    protected Paciente selecionaProximoPaciente() throws NenhumPacienteEscolhidoException{
        Paciente pacienteEscolhido = null;
        do{
            
            UBS.getInstance().ui.mostraLinha("Como gostaria de escolher o próximo paciente?");
            String opt = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(new String[]{"Buscando nos meus agendamentos", "Consultando por nome e data de nascimento", "Cancelar"});

            if(opt.equals("Buscando nos meus agendamentos")){
                
                ArrayList<Agendamento> proximosAgendamentos = proximosAgendamentos();
                
                if(proximosAgendamentos == null){
                    
                    UBS.getInstance().ui.mostraLinha("(!) " + nome + ", não há agendamentos em seu nome.");
                    
                }else{
                    
                    ArrayList<Paciente> pacientesAgendados = new ArrayList<>();
                    ArrayList<Agendamento> agendamentosDosPacientes = new ArrayList<>();
                    for (Agendamento agendamento : proximosAgendamentos) {
                        if(pacientesAgendados.indexOf(agendamento.getPaciente()) == -1){
                            pacientesAgendados.add(agendamento.getPaciente());
                            agendamentosDosPacientes.add(agendamento);
                        }
                    }

                    ArrayList<String> nomes = new ArrayList<>();
                    for (Agendamento umAgendamento : agendamentosDosPacientes){
                        nomes.add(umAgendamento.getPaciente().descricao() + "\n    -> próximo agendameto para " + umAgendamento.horario());
                    }
                    
                    nomes.add("Continuar procurando");
                    String nomePacienteEscolhido = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(nomes);
                    if(!nomePacienteEscolhido.equals("Continuar procurando")){
                        int index = nomes.indexOf(nomePacienteEscolhido);
                        pacienteEscolhido = pacientesAgendados.get(index);
                    }
                }
                
            }else if(opt.equals("Consultando por nome e data de nascimento")){
                
                UBS.getInstance().ui.mostra("Digite o sobrenome do paciente: ");
                String nomeDigitado = UBS.getInstance().ui.pedeString();
                
                List<Usuario> todos = UBS.getInstance().getUsuarios();
                ArrayList<Paciente> provaveis = new ArrayList<>();
                for (Usuario usuario : todos) {
                    if(usuario.getClass().equals(Paciente.class) && usuario.getSobrenome().toLowerCase().equals(nomeDigitado.toLowerCase())){
                        provaveis.add((Paciente) usuario);
                    }
                }
                
                if(provaveis.isEmpty()){
                    UBS.getInstance().ui.mostraLinha("(!) Nenhum paciente foi encontrado para este sobrenome.");
                }else{
                    ArrayList<String> nomes = new ArrayList<>();
                    for (Paciente pacienteProvavel : provaveis) nomes.add(pacienteProvavel.descricao());
                    nomes.add("Continuar procurando");
                    String nomePacienteEscolhido = UBS.getInstance().ui.pedeEscolhaEntreOpcoes(nomes);
                    if(!nomePacienteEscolhido.equals("Continuar procurando")){
                        int index = nomes.indexOf(nomePacienteEscolhido);
                        pacienteEscolhido = provaveis.get(index);
                    }
                }
            }else{
                throw new NenhumPacienteEscolhidoException();
            }
        
        } while(pacienteEscolhido == null);
        return pacienteEscolhido;
    }
    
    public List<Agendamento> agendamentosNoDia(Date dia){
        ArrayList<Agendamento> noDia = new ArrayList<>();
        if(agendamentos != null){
            for (Agendamento agendamento : agendamentos) {
                if(DateHelpers.isSameDay(agendamento.getData(), dia))
                    noDia.add(agendamento);
            }
        }
        return noDia;
    }
    
    private ArrayList<Agendamento> proximosAgendamentos(){
        ArrayList<Agendamento> proximos = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            if(agendamento.getData().after(new Date())){
                proximos.add(agendamento);
            }
        }
        return proximos;
    }
    
    public Especialista() {
        super();
    }
}
