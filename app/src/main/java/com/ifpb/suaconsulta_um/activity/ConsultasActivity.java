package com.ifpb.suaconsulta_um.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.activity.adapter.ConsultasAdapter;
import com.ifpb.suaconsulta_um.firebase.ConfiguracaoFirebase;
import com.ifpb.suaconsulta_um.model.Consulta;

import java.util.ArrayList;
import java.util.List;

public class ConsultasActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private List<Consulta> consultas;
    private ChildEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        Toolbar toolbar = findViewById(R.id.toolbarConsultas);
        toolbar.setTitle("Consultas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = ConfiguracaoFirebase.getFirebaseAuth();
        databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("consultas").child(auth.getCurrentUser().getUid()).getParent();

        consultas = new ArrayList<>();

        recycler = findViewById(R.id.recyclerConsultas);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);

        adapter = new ConsultasAdapter(consultas, getApplicationContext());
        recycler.setAdapter(adapter);
    }

    private void recuperaConsultas() {
        eventListener = databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Log.i("VERIFICANDO", data.getRef().toString());
                    Consulta consulta = data.getValue(Consulta.class);
                    consulta.setUid(data.getKey());
                    if (consulta.getUnidadeMedica().equals(auth.getCurrentUser().getUid())){
                        consultas.add(consulta);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                recuperaConsultas();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void carregaAdicionarConsulta(View view){
        Intent intent = new Intent(ConsultasActivity.this, AdicionaConsultaActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        consultas.clear();
        recuperaConsultas();
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(eventListener);
    }
}
