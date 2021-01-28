package com.testeVivo.classificador;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testeVivo.entidades.Job;
import com.testeVivo.utils.ParametrosAgendamento;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClassificadorDeJobs {
    private final ParametrosAgendamento parametrosAgendamento = new ParametrosAgendamento();

    public void classificar(String registro) {
        try {
            List<Job> objJobs = this.mapeiaListaDeJobsAPartirDoRegistro(registro);

            List<List<Job>> agendamentos = this.montaAgendaDeExecucao(objJobs);
            for (List<Job>agendamento : agendamentos) {
                agendamento.forEach(job -> System.out.println(job.getId()));
                System.out.println("\n");
            }

        } catch (Exception ex) {
            System.out.println("ERRO NA CLASSIFICAÇÃO DOS JOBS CONSUMIDOS: " + ex.getMessage());
        }
    }

    public List<Job> mapeiaListaDeJobsAPartirDoRegistro(String registro) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return mapper.readValue(registro, new TypeReference<List<Job>>(){});
    }

    public List<List<Job>> montaAgendaDeExecucao(List<Job> jobs) throws ParseException {
        List<List<Job>> agendaDeJobs = new ArrayList<>();

        List<Job> jobsNaoExecutaveis = jobs.stream().filter(
                job -> job.getDataMaximaConclusao().before(this.parametrosAgendamento.getDataInicioJanela())
                        || job.getDataMaximaConclusao().after(this.parametrosAgendamento.getDataFimJanela())
                        || job.getTempoEstimado() > 8
        ).collect(Collectors.toList());
        jobs.removeAll(jobsNaoExecutaveis);
        if (jobs.size() == 0) {
            return agendaDeJobs;
        }

        while (jobs.size() > 0) {
            this.parametrosAgendamento.setProximoDia();
            if (this.parametrosAgendamento.getFimExecucao().after(this.parametrosAgendamento.getDataFimJanela())) {
                break;
            }

            List<Job> jobsDoDia = this.montaExecucaoDiaria(jobs);
            agendaDeJobs.add(jobsDoDia);

            jobs.removeAll(jobsDoDia);
        }

        return agendaDeJobs;
    }

    public List<Job> montaExecucaoDiaria(List<Job> jobs) {
        List<Job> jobsDoDia = new ArrayList<>();
        int horasRestantes = 8;

        Job j = Collections.min(jobs, Comparator.comparing(Job::getDataMaximaConclusao));
        jobsDoDia.add(j);
        jobs.remove(j);

        horasRestantes -= j.getTempoEstimado();
        if (horasRestantes == 0) {
            return jobsDoDia;
        }

        List<Job> jobsParaPeencherDia = new ArrayList<>();
        jobsParaPeencherDia = this.buscaJobsParaPreencherLacuna(jobs, jobsParaPeencherDia, horasRestantes);
        jobsDoDia.addAll(jobsParaPeencherDia);

        return jobsDoDia;
    }

    public List<Job> buscaJobsParaPreencherLacuna(List<Job> jobs, List<Job> jobsDePreenchimento, int horasRestantes) {
        try {
            int finalHorasRestantes = horasRestantes;
            Job j = Collections.min(
                    jobs.stream().filter(job ->
                            job.getTempoEstimado() <= finalHorasRestantes).collect(Collectors.toList()),
                    Comparator.comparing(Job::getDataMaximaConclusao)
            );

            if (horasRestantes - j.getTempoEstimado() < 0) {
                jobs.remove(j);
            } else {
                jobsDePreenchimento.add(j);
                jobs.remove(j);

                horasRestantes -= j.getTempoEstimado();
                if (horasRestantes == 0) {
                    return jobsDePreenchimento;
                }
            }
            return this.buscaJobsParaPreencherLacuna(jobs, jobsDePreenchimento, horasRestantes);
        } catch (Exception ex) {
            return jobsDePreenchimento;
        }
    }
}
