package com.ifpb.suaconsulta_um.model;

import java.io.Serializable;
import java.util.Objects;

public class UnidadeSaude implements Serializable {

    private String id;
    private String nome;
    private String telefone;
    private String CNPJ;
    private String rua;
    private String bairro;
    private String email;
    private String senha;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidadeSaude that = (UnidadeSaude) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(CNPJ, that.CNPJ) &&
                Objects.equals(rua, that.rua) &&
                Objects.equals(bairro, that.bairro) &&
                Objects.equals(email, that.email) &&
                Objects.equals(senha, that.senha);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nome, telefone, CNPJ, rua, bairro, email, senha);
    }
}
