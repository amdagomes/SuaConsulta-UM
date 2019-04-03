package com.ifpb.suaconsulta_um.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ifpb.suaconsulta_um.R;

public class MedicosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicos);

        Toolbar toolbar = findViewById(R.id.toolbarMedicos);
        toolbar.setTitle("Médicos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializaComponentes();

    }

    private void inicializaComponentes() {
        recyclerView = findViewById(R.id.recyclerMedicos);
    }

    public void carreagarAdicionarMedico(View view){
        Intent intent = new Intent(MedicosActivity.this, AdicionarMedicoActivity.class);
        startActivity(intent);
    }
}
