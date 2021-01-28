package com.testVivo.utils;

import com.testeVivo.constantes.JobConstantes;
import com.testeVivo.utils.ParametrosAgendamento;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParametrosAgendamentoTest{
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void deveRetornarADataDeInicioDaJanelaFormatadaCorretamente() throws ParseException {
        JobConstantes.INICIO_JANELA = "2021-01-01";
        JobConstantes.HORA_INICIO_EXECUCAO = 8;

        ParametrosAgendamento paramentos = new ParametrosAgendamento();
        Date dataInicioJanela = paramentos.getDataInicioJanela();

        Date dataEsperada = this.sdf.parse(JobConstantes.INICIO_JANELA);
        this.calendar.setTime(dataEsperada);
        this.calendar.add(Calendar.HOUR_OF_DAY, JobConstantes.HORA_INICIO_EXECUCAO);
        dataEsperada = this.calendar.getTime();

        Assert.assertEquals(dataInicioJanela, dataEsperada);
    }

    @Test
    public void deveRetornarADataDeFimDaJanelaFormatadaCorretamente() throws ParseException {
        JobConstantes.FIM_JANELA = "2021-01-01";
        JobConstantes.HORA_FIM_EXECUCAO = 16;

        ParametrosAgendamento paramentos = new ParametrosAgendamento();
        Date dataFimJanela = paramentos.getDataFimJanela();

        Date dataEsperada = this.sdf.parse(JobConstantes.FIM_JANELA);
        this.calendar.setTime(dataEsperada);
        this.calendar.add(Calendar.HOUR_OF_DAY, JobConstantes.HORA_FIM_EXECUCAO);
        dataEsperada = this.calendar.getTime();

        Assert.assertEquals(dataFimJanela, dataEsperada);
    }

    @Test
    public void deveRetornarODiaNaDataAtualQuandoSetProximoiaForChamadoPelaPrimeiraVez() {
        ParametrosAgendamento paramentos = new ParametrosAgendamento();
        paramentos.setProximoDia();

        Date fimExecucao = paramentos.getFimExecucao();

        Date hoje = new Date();
        this.calendar.setTime(hoje);
        int diaAtual = this.calendar.get(Calendar.DAY_OF_MONTH);

        this.calendar.setTime(fimExecucao);
        Assert.assertEquals(this.calendar.get(Calendar.DAY_OF_MONTH), diaAtual);
    }

    @Test
    public void deveRetornarOProximoDiaQuandoSetProximoiaForChamadoPelaSegundaVez() {
        ParametrosAgendamento paramentos = new ParametrosAgendamento();
        paramentos.setProximoDia();
        paramentos.setProximoDia();

        Date fimExecucao = paramentos.getFimExecucao();

        Date hoje = new Date();
        this.calendar.setTime(hoje);
        this.calendar.add(Calendar.HOUR, 24);
        int proximoDia = this.calendar.get(Calendar.DAY_OF_MONTH);

        this.calendar.setTime(fimExecucao);
        Assert.assertEquals(this.calendar.get(Calendar.DAY_OF_MONTH), proximoDia);
    }
}