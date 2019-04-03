package com.ifpb.suaconsulta_um.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.model.UnidadeSaude;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void carregarConfiguracoesConta(View view){
        Intent intent = new Intent(MainActivity.this, ContaActivity.class);
        startActivity(intent);
    }

    public void carregarConsultas(View view){
        Intent intent = new Intent(MainActivity.this, ConsultasActivity.class);
        startActivity(intent);
    }

    public void carregarMedicos(View view){
        Intent intent = new Intent(MainActivity.this, MedicosActivity.class);
        startActivity(intent);
    }

}
