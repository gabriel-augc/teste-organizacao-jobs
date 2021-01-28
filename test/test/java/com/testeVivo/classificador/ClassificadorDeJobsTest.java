package test.java.com.testeVivo.classificador;


import com.testeVivo.classificador.ClassificadorDeJobs;
import com.testeVivo.constantes.JobContantes;
import com.testeVivo.entidades.Job;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClassificadorDeJobsTest {
    private ClassificadorDeJobs classificador;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date now = new Date();

    @Before
    public void initObjects() {
        this.classificador = new ClassificadorDeJobs();


        JobContantes.INICIO_JANELA = this.sdf.format(this.now);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.now);
        calendar.add(Calendar.HOUR_OF_DAY, 48);
        JobContantes.FIM_JANELA = this.sdf.format(calendar.getTime());
    }

    @Test
    public void deveRetornarUmaListaDeJobsDeAcordoComORegistroConsumido() throws IOException, ParseException {
        String registro = "[{\"ID\": 1,\"Descrição\": \"Importação de arquivos de fundos\",\"Data Máxima de conclusão" +
                "\": \"2019-11-10 12:00:00\",\"Tempo estimado\": \"2\"},{\"ID\": 2,\"Descrição\": \"Importação de dad" +
                "os da Base Legada\",\"Data Máxima de conclusão\": \"2019-11-11 12:00:00\",\"Tempo estimado\": \"4\"}" +
                ",{\"ID\": 3,\"Descrição\": \"Importação de dados de integração\",\"Data Máxima de conclusão\": \"201" +
                "9-11-11 08:00:00\",\"Tempo estimado\": \"6\"}]";

        List<Job> jobs = this.classificador.mapeiaListaDeJobsAPartirDoRegistro(registro);

        Assertions.assertThat(jobs).extracting("Id").contains(1, 2, 3);
        Assertions.assertThat(jobs).extracting("Descricao").contains(
                "Importação de arquivos de fundos",
                "Importação de dados da Base Legada",
                "Importação de dados de integração"
        );
        Assertions.assertThat(jobs).extracting("DataMaximaConclusao").contains(
                this.sdf.parse("2019-11-10 12:00:00"),
                this.sdf.parse("2019-11-11 12:00:00"),
                this.sdf.parse("2019-11-11 08:00:00")
        );
        Assertions.assertThat(jobs).extracting("TempoEstimado").contains(2, 4, 6);
    }

    @Test
    public void deveRetornarUmaListaVaziaQuandoADataMaximaDeConclusaoDosJobsJaTiverPassado() throws ParseException {
        List<Job> jobs = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.now);
        calendar.add(Calendar.HOUR_OF_DAY, -24);

        calendar.getTime();
        Job job1 = new Job(1, "teste1", calendar.getTime(), 1);
        jobs.add(job1);

        Job job2 = new Job(2, "teste2", calendar.getTime(), 1);
        jobs.add(job2);

        Job job3 = new Job(3, "teste3", calendar.getTime(), 1);
        jobs.add(job3);

        List<List<Job>> agenda = this.classificador.montaAgendaDeExecucao(jobs);
        Assert.assertEquals(agenda.size(), 0);
    }

    @Test
    public void deveRetornarUmaListaVaziaQuandoTodosOsJobsNaoConseguemRodarNaJanelaDe8Horas() throws ParseException {
        List<Job> jobs = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.now);
        calendar.add(Calendar.HOUR_OF_DAY, 24);

        calendar.getTime();
        Job job1 = new Job(1, "teste1", calendar.getTime(), 9);
        jobs.add(job1);

        Job job2 = new Job(2, "teste2", calendar.getTime(), 9);
        jobs.add(job2);

        Job job3 = new Job(3, "teste3", calendar.getTime(), 9);
        jobs.add(job3);

        List<List<Job>> agenda = this.classificador.montaAgendaDeExecucao(jobs);
        Assert.assertEquals(agenda.size(), 0);
    }

    @Test
    public void deveRetornarUmaListaDeListasContendoOJob1E3NoPrimeiroDiaEoJob2NoSegundo() throws ParseException {
        List<Job> jobs = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(this.now);
        calendar.set(Calendar.HOUR, JobContantes.HORA_INICIO_EXECUCAO + 2);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Job job1 = new Job(1, "teste1", calendar.getTime(), 2);
        jobs.add(job1);

        calendar.setTime(this.now);
        calendar.add(Calendar.HOUR_OF_DAY, JobContantes.HORA_INICIO_EXECUCAO + 28);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Job job2 = new Job(2, "teste2", calendar.getTime(), 4);
        jobs.add(job2);

        calendar.setTime(this.now);
        calendar.add(Calendar.HOUR_OF_DAY, JobContantes.HORA_INICIO_EXECUCAO + 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Job job3 = new Job(3, "teste3", calendar.getTime(), 6);
        jobs.add(job3);

        List<List<Job>> agenda = this.classificador.montaAgendaDeExecucao(jobs);

        Assert.assertEquals(agenda.size(), 2);

        List<Job> lista1 = new ArrayList<>();
        lista1.add(job1);
        lista1.add(job3);

        List<Job> lista2 = new ArrayList<>();
        lista2.add(job2);

        Assertions.assertThat(agenda).contains(lista1);
        Assertions.assertThat(agenda).contains(lista2);
    }

    @Test
    public void deveRetornarUmaListaDeListasContendoOJob1NoPrimeiroDiaEOJob2NoSegundo() throws ParseException {
        List<Job> jobs = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(this.now);
        calendar.set(Calendar.HOUR, JobContantes.HORA_INICIO_EXECUCAO + 2);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Job job1 = new Job(1, "teste1", calendar.getTime(), 2);
        jobs.add(job1);

        calendar.setTime(this.now);
        calendar.add(Calendar.HOUR_OF_DAY, JobContantes.HORA_INICIO_EXECUCAO + 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Job job2 = new Job(2, "teste2", calendar.getTime(), 7);
        jobs.add(job2);

        calendar.setTime(this.now);
        calendar.add(Calendar.HOUR_OF_DAY, JobContantes.HORA_INICIO_EXECUCAO + 28);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Job job3 = new Job(3, "teste3", calendar.getTime(), 7);
        jobs.add(job3);

        List<List<Job>> agenda = this.classificador.montaAgendaDeExecucao(jobs);

        Assert.assertEquals(agenda.size(), 2);

        List<Job> lista1 = new ArrayList<>();
        lista1.add(job1);

        List<Job> lista2 = new ArrayList<>();
        lista2.add(job2);

        Assertions.assertThat(agenda).contains(lista1);
        Assertions.assertThat(agenda).contains(lista2);
    }
}