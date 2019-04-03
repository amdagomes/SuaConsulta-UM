package com.ifpb.suaconsulta_um.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Spinner;

import com.ifpb.suaconsulta_um.R;

public class AdicionaConsultaActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText data, vagas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_consulta);

        Toolbar toolbar = findViewById(R.id.toolbarAdcConsulta);
        toolbar.setTitle("Adiconar Consulta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializaComponentes();
    }

    private void inicializaComponentes() {
        spinner = findViewById(R.id.spinnerAdcConsulta);
        data = findViewById(R.id.editAdcConsultaData);
        vagas = findViewById(R.id.editAdcConsultaVagas);
    }
}
