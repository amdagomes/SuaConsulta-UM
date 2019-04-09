package com.ifpb.suaconsulta_um.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.firebase.ConfiguracaoFirebase;
import com.ifpb.suaconsulta_um.model.Consulta;
import com.ifpb.suaconsulta_um.model.Medico;

import java.util.ArrayList;
import java.util.List;

public class AdicionaConsultaActivity extends AppCompatActivity {

    private DatabaseReference referenceMedico;
    private DatabaseReference referenceConsulta;
    private FirebaseAuth auth;

    private Spinner spinner;
    private EditText data, vagas;
    private Button buttonCadastrar;
    private ProgressBar progressBar;
    private ArrayAdapter<Medico> adapter;
    private Consulta consulta;
    private Medico medico;
    private List<Medico> medicos;

    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_consulta);

        Toolbar toolbar = findViewById(R.id.toolbarAdcConsulta);
        toolbar.setTitle("Adiconar Consulta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializaComponentes();

        progressBar.setVisibility(View.GONE);
        consulta = new Consulta();
        medicos = new ArrayList<>();

        auth = ConfiguracaoFirebase.getFirebaseAuth();
        referenceMedico = ConfiguracaoFirebase.getDatabaseReference().child("unidades").child(auth.getCurrentUser().getUid()).child("medicos");
        referenceConsulta = ConfiguracaoFirebase.getDatabaseReference().child("consultas");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                consulta.setMedico(
                        String.valueOf(medicos.get(position).getCRM())
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                consulta.setData(data.getText().toString());
                consulta.setNumVagas(Integer.parseInt(vagas.getText().toString()));
                consulta.setVagasRestantes(Integer.parseInt(vagas.getText().toString()));
                consulta.setUnidadeMedica(auth.getCurrentUser().getUid());
                try{
                    referenceConsulta.child(auth.getCurrentUser().getUid()).push().setValue(consulta);
//                    referenceConsulta.push().setValue(consulta);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AdicionaConsultaActivity.this, "Consulta cadastrada", Toast.LENGTH_SHORT).show();
                    finish();
                }catch (Exception ex){

                }
            }
        });
    }

    private void inicializaComponentes() {
        buttonCadastrar = findViewById(R.id.buttonAdcConsulta);
        spinner = findViewById(R.id.spinnerAdcConsulta);
        progressBar = findViewById(R.id.progressAdcConsulta);
        data = findViewById(R.id.editAdcConsultaData);
        vagas = findViewById(R.id.editAdcConsultaVagas);
    }

    private void recuperaMedicos(){
         eventListener = referenceMedico.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 for(DataSnapshot data : dataSnapshot.getChildren()){
                     Medico medico = data.getValue(Medico.class);
                     Log.i("MEDICOS", medico.toString());
                     medicos.add(medico);
                 }

                 adapter = new ArrayAdapter<>(AdicionaConsultaActivity.this, android.R.layout.simple_spinner_item, medicos);
                 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                 spinner.setAdapter(adapter);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
    }

    @Override
    protected void onStart() {
        super.onStart();
        medicos.clear();
        recuperaMedicos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        referenceMedico.removeEventListener(eventListener);
    }
}
