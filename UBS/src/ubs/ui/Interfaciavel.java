/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs.ui;

import java.util.List;

// Não consegui pensar num nome bom em português, desculpa Takeo!
public interface Interfaciavel {
    public List<String> acoesDisponiveis(String contexto);
    public Interfaciavel escolherAcao(String acao);
}
