package org.gelbercabrera.ferreteria.posts.postlist.helper;

import java.util.Calendar;
import java.util.Date;

public class DateManager {
    public static String calculateTime(Date postDate) {
        Date now = new Date();

        Calendar calFechaInicial=Calendar.getInstance();
        Calendar calFechaFinal= Calendar.getInstance();

        calFechaInicial.setTime(postDate);
        calFechaFinal.setTime(now);

        long secs= cantidadTotalSegundos(calFechaInicial, calFechaFinal);
        if(secs< 60){
            return String.valueOf(secs)+" s";
        }

        long minutos = cantidadTotalMinutos(calFechaInicial, calFechaFinal);
        if (minutos < 60){
            return String.valueOf(minutos)+" m";
        }

        long horas = cantidadTotalHoras(calFechaInicial, calFechaFinal);
        if(horas<24){
            return String.valueOf(horas)+" h";
        }

        long dias = cantidadTotalDias(calFechaInicial, calFechaFinal);
        if(dias<30){
            return String.valueOf(dias)+" d";
        }

        long semanas = cantidadTotalSemanas(calFechaInicial, calFechaFinal);
        if(semanas<5){
            return String.valueOf(semanas)+" w";
        }

        long anios = cantidadTotalAnios(calFechaInicial, calFechaFinal);
        return String.valueOf(anios)+" y";
    }

    /*Metodo que devuelve el Numero total de minutos que hay entre las dos Fechas */
    public static long cantidadTotalMinutos(Calendar fechaInicial ,Calendar fechaFinal){

        long totalMinutos=0;
        totalMinutos=((fechaFinal.getTimeInMillis()-fechaInicial.getTimeInMillis())/1000/60);
        return totalMinutos;
    }
    /*Metodo que devuelve el Numero total de horas que hay entre las dos Fechas */
    public static long cantidadTotalHoras(Calendar fechaInicial ,Calendar fechaFinal){

        long totalMinutos=0;
        totalMinutos=((fechaFinal.getTimeInMillis()-fechaInicial.getTimeInMillis())/1000/60/60);
        return totalMinutos;
    }
    /*Metodo que devuelve el Numero total de Segundos que hay entre las dos Fechas */
    public static long cantidadTotalSegundos(Calendar fechaInicial ,Calendar fechaFinal){

        long totalMinutos=0;
        totalMinutos=((fechaFinal.getTimeInMillis()-fechaInicial.getTimeInMillis())/1000);
        return totalMinutos;
    }

    /*Metodo que devuelve el Numero total de Días que hay entre las dos Fechas */
    public static long cantidadTotalDias(Calendar fechaInicial ,Calendar fechaFinal){

        long totalMinutos=0;
        totalMinutos=((fechaFinal.getTimeInMillis()-fechaInicial.getTimeInMillis())/1000/60/60/24);
        return totalMinutos;
    }

    /*Metodo que devuelve el Numero total de Semanas que hay entre las dos Fechas */
    public static long cantidadTotalSemanas(Calendar fechaInicial ,Calendar fechaFinal){

        long totalMinutos=0;
        totalMinutos=((fechaFinal.getTimeInMillis()-fechaInicial.getTimeInMillis())/1000/60/60/24/7);
        return totalMinutos;
    }

    /*Metodo que devuelve el Numero total de Anios que hay entre las dos Fechas */
    public static long cantidadTotalAnios(Calendar fechaInicial ,Calendar fechaFinal){

        long totalMinutos=0;
        totalMinutos=((fechaFinal.getTimeInMillis()-fechaInicial.getTimeInMillis())/1000/60/60/24/7/52);
        return totalMinutos;
    }

}
