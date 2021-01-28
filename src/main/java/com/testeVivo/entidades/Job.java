package com.testeVivo.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class Job {
    @JsonProperty("ID")
    private int Id;

    @JsonProperty("Descrição")
    private String Descricao;

    @JsonProperty("Data Máxima de conclusão")
    private Date DataMaximaConclusao;

    @JsonProperty("Tempo estimado")
    private int TempoEstimado;

    public Job(int Id, String Descricao, Date DataMaximaConclusao, int TempoEstimado) {
        this.Id = Id;
        this.Descricao = Descricao;
        this.DataMaximaConclusao = DataMaximaConclusao;
        this.TempoEstimado = TempoEstimado;
    }

    public Job() { }

    public int getId() {
        return Id;
    }

    public Date getDataMaximaConclusao() {
        return DataMaximaConclusao;
    }

    public int getTempoEstimado() {
        return TempoEstimado;
    }
}
