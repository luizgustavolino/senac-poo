/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author luizgustavolino
 */
public class Data {
    
    private final int ano;
    private final int mes;
    private final int dia;
    private final int hora;
    private final int minuto;
    
    public Data(){
        
        Date dataAtual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAtual);
        
        this.ano = calendar.get(Calendar.YEAR);
        this.mes = calendar.get(Calendar.MONTH);
        this.dia = calendar.get(Calendar.DAY_OF_MONTH);
        this.hora   = calendar.get(Calendar.HOUR_OF_DAY);
        this.minuto = calendar.get(Calendar.MINUTE);
        
    }
    
}
