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
            this.calendar.set(Calendar.HOUR, JobContantes.HORA_INICIO_EXECUCAO);
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
            this.calendar.set(Calendar.HOUR, JobContantes.HORA_FIM_EXECUCAO);
            return calendar.getTime();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new Date();
        }
    }

    public Date getFimExecucao() {
        this.calendar.setTime(this.dia);
        calendar.set(Calendar.HOUR, JobContantes.HORA_FIM_EXECUCAO);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return this.calendar.getTime();
    }

    public void setProximoDia() {
        if (this.dia == null) {
            this.dia = new Date();
            this.calendar.setTime(this.dia);
            this.calendar.set(Calendar.HOUR, 0);
            this.calendar.set(Calendar.MINUTE, 0);
            this.calendar.set(Calendar.SECOND, 0);
        } else {
            this.calendar.setTime(this.dia);
            this.calendar.add(Calendar.HOUR_OF_DAY, 24);
        }
        this.dia = this.calendar.getTime();
    }
}
