package com.ifpb.suaconsulta_um.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.adapters.ConsultasAdapter;
import com.ifpb.suaconsulta_um.model.Consulta;

import java.util.List;

public class ConsultasActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private List<Consulta> consultas;
    private FloatingActionButton btnAddConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        Toolbar toolbar = findViewById(R.id.toolbarConsultas);
        toolbar.setTitle("Consultas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ConsultasAdapter(consultas, getApplicationContext());
        recycler = findViewById(R.id.recyclerConsultas);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

    }

    public void carregaAdicionarConsulta(View view){
        Intent intent = new Intent(ConsultasActivity.this, AdicionaConsultaActivity.class);
        startActivity(intent);
    }
}
