package com.ifpb.suaconsulta_um.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.ifpb.suaconsulta_um.R;

public class AdicionarMedicoActivity extends AppCompatActivity {

    private EditText nome, crm, telefone, email, especialidade;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiconar_medico);

        Toolbar toolbar = findViewById(R.id.toolbarAdcMedico);
        toolbar.setTitle("Cadastrar Médico");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        nome = findViewById(R.id.editAdcMedicoNome);
        crm = findViewById(R.id.editAdcMedicoCRM);
        telefone = findViewById(R.id.editAdcMedicoTelefone);
        email = findViewById(R.id.editAdcMedicoEmail);
        especialidade = findViewById(R.id.editAdcMedicoEspecialidae);
        cadastrar = findViewById(R.id.buttonAdcMedico);
    }
}
