package com.ifpb.suaconsulta_um.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.activity.adapter.MedicosAdapter;
import com.ifpb.suaconsulta_um.firebase.ConfiguracaoFirebase;
import com.ifpb.suaconsulta_um.model.Medico;

import java.util.ArrayList;
import java.util.List;

public class MedicosActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ChildEventListener childEventListener;
    private List<Medico> medicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicos);

        Toolbar toolbar = findViewById(R.id.toolbarMedicos);
        toolbar.setTitle("Médicos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = ConfiguracaoFirebase.getFirebaseAuth();
        databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("unidades").child(auth.getCurrentUser().getUid()).child("medicos").getParent();
        medicos = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerMedicos);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MedicosActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new MedicosAdapter(medicos, MedicosActivity.this);
        recyclerView.setAdapter(adapter);

//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
    }

    public void carreagarAdicionarMedico(View view){
        Intent intent = new Intent(MedicosActivity.this, AdicionarMedicoActivity.class);
        startActivity(intent);
    }

    public void recuperarMedicos(){
        childEventListener = databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Medico medico = data.getValue(Medico.class);
                    Log.i("MEDICOS", medico.toString());
                    medicos.add(medico);
                    adapter.notifyDataSetChanged();
                }
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

    @Override
    protected void onStart() {
        super.onStart();
        medicos.clear();
        recuperarMedicos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(childEventListener);
    }
}
