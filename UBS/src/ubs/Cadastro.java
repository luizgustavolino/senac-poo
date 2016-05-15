/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.Arrays;
import java.util.List;
import ubs.ui.Interfaciavel;

/**
 *
 * @author luizgustavolino
 */
public class Cadastro implements Interfaciavel{

    @Override
    public List<String> acoesDisponiveis(String contexto) {
        return Arrays.asList("Paciente", "Médico", "Dentista", "Enfermeiro", "Cancelar");
    }

    @Override
    public Interfaciavel escolherAcao(String acao) {
        switch(acao){
            case "Paciente": return Paciente.cadastrar();
            case "Médico": return Medico.cadastrar();
            case "Dentista": return Dentista.cadastrar();
            case "Enfermeiro": return Enfermeiro.cadastrar();
            case "Cancelar":
            default: return UBS.getInstance();
        }
    }
}
