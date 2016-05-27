/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubs.extensions;
import java.util.Calendar;
import java.util.Date;

// Nota: Como é uma extensão da linguagem,
// escrevemos os métodos em inglês
public class DateHelpers{
    
    public static Boolean isSameDay(Date a, Date b){
        
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(a);
        Calendar bCalendar = Calendar.getInstance();
        bCalendar.setTime(b);
        
        if(aCalendar.get(Calendar.YEAR) == bCalendar.get(Calendar.YEAR)){
            int refDOY   = aCalendar.get(Calendar.DAY_OF_YEAR);
            int todayDOY = bCalendar.get(Calendar.DAY_OF_YEAR);
            return refDOY == todayDOY;
        }else{
            return false;
        }
    }
    
    public static Boolean isFutureDay(Date reference){
        
        Calendar refCalendar = Calendar.getInstance();
        refCalendar.setTime(reference);
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(new Date());
        
        int refYear     = refCalendar.get(Calendar.YEAR);
        int todayYear   = todayCalendar.get(Calendar.YEAR);
        
        if(refYear > todayYear){
            return true;
        }else if(refYear < todayYear){
            return false;
        }else{
            int refDOY   = refCalendar.get(Calendar.DAY_OF_YEAR);
            int todayDOY = todayCalendar.get(Calendar.DAY_OF_YEAR);
            return refDOY > todayDOY;
        }
    }
    
}
