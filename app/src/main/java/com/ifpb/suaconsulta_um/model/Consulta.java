package com.ifpb.suaconsulta_um.model;

import java.util.Objects;

public class Consulta {
    private String id;
    private String data;
    private int numVagas;
    private int vagasPreenchidas;
    private String medico;
    private String unidadeMedica;

    public Consulta() {
    }

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

    public int getVagasPreenchidas() {
        return vagasPreenchidas;
    }

    public void setVagasPreenchidas(int vagasPreenchidas) {
        this.vagasPreenchidas = vagasPreenchidas;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return numVagas == consulta.numVagas &&
                vagasPreenchidas == consulta.vagasPreenchidas &&
                Objects.equals(id, consulta.id) &&
                Objects.equals(data, consulta.data) &&
                Objects.equals(medico, consulta.medico) &&
                Objects.equals(unidadeMedica, consulta.unidadeMedica);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, data, numVagas, vagasPreenchidas, medico, unidadeMedica);
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id='" + id + '\'' +
                ", data='" + data + '\'' +
                ", numVagas=" + numVagas +
                ", vagasPreenchidas=" + vagasPreenchidas +
                ", medico='" + medico + '\'' +
                ", unidadeMedica='" + unidadeMedica + '\'' +
                '}';
    }
}
