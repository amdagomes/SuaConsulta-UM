package com.ifpb.suaconsulta_um.model;

public class Consulta {
    private String id;
    private String data;
    private int numVagas;
    private String medico;
    private String unidadeMedica;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getNumVagas() {
        return numVagas;
    }

    public void setNumVagas(int numVagas) {
        this.numVagas = numVagas;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getUnidadeMedica() {
        return unidadeMedica;
    }

    public void setUnidadeMedica(String unidadeMedica) {
        this.unidadeMedica = unidadeMedica;
    }
}
