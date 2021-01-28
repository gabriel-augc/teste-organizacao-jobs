package com.testeVivo.utils;

import com.testeVivo.constantes.JobContantes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParametrosAgendamento {
    private final Calendar calendar = Calendar.getInstance();
    private Date dia;

    public Date getDataInicioJanela() {
        try {
            Date inicioJanela = new SimpleDateFormat("yyyy-MM-dd").parse(JobContantes.INICIO_JANELA);

            this.calendar.setTime(inicioJanela);
            this.calendar.add(Calendar.HOUR_OF_DAY, JobContantes.HORA_INICIO_EXECUCAO);
            return calendar.getTime();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new Date();
        }
    }

    public Date getDataFimJanela() {
        try {
            Date inicioJanela = new SimpleDateFormat("yyyy-MM-dd").parse(JobContantes.FIM_JANELA);

            this.calendar.setTime(inicioJanela);
            this.calendar.add(Calendar.HOUR_OF_DAY, JobContantes.HORA_FIM_EXECUCAO);
            return calendar.getTime();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new Date();
        }
    }

    public Date getInicioExecucao() {
        if (this.dia == null) {
            this.dia = new Date(0);
        }

        this.calendar.setTime(this.dia);
        this.calendar.add(Calendar.HOUR_OF_DAY, JobContantes.HORA_INICIO_EXECUCAO);
        return this.calendar.getTime();
    }

    public Date getFimExecucao() {
        this.dia = new Date(0);
        this.calendar.setTime(this.dia);
        this.calendar.add(Calendar.HOUR_OF_DAY, JobContantes.HORA_FIM_EXECUCAO);
        return this.calendar.getTime();
    }

    public void setProximoDia() {
        if (this.dia == null) {
            this.dia = new Date(0);
            this.calendar.add(Calendar.HOUR_OF_DAY, JobContantes.HORA_INICIO_EXECUCAO);
        } else {
            this.calendar.setTime(this.dia);
            this.calendar.add(Calendar.HOUR_OF_DAY, 24);
        }
    }
}
