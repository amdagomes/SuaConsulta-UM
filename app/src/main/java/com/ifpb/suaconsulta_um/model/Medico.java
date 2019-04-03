package com.ifpb.suaconsulta_um.model;

import java.util.Objects;

public class Medico {
    private String id;
    private String nome;
    private int CRM;
    private String telefone;
    private String especialidade;
    private String email;

    public Medico() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCRM() {
        return CRM;
    }

    public void setCRM(int CRM) {
        this.CRM = CRM;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medico medico = (Medico) o;
        return CRM == medico.CRM &&
                Objects.equals(id, medico.id) &&
                Objects.equals(nome, medico.nome) &&
                Objects.equals(telefone, medico.telefone) &&
                Objects.equals(especialidade, medico.especialidade) &&
                Objects.equals(email, medico.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nome, CRM, telefone, especialidade, email);
    }
}
