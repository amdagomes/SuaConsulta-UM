package com.ifpb.suaconsulta_um.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.firebase.ConfiguracaoFirebase;
import com.ifpb.suaconsulta_um.model.Medico;

public class EditarMedicoActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    private EditText editNome, editCrm, editTelefone, editEmail, editEspecialidade;
    private Button btnAtualizar;
    private ProgressBar progressBar;
    private Medico medico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_medico);

        Toolbar toolbar = findViewById(R.id.toolbarEditarMedico);
        toolbar.setTitle("Atualizar Médico");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializarComponentes();

        databaseReference = ConfiguracaoFirebase.getDatabaseReference();
        auth = ConfiguracaoFirebase.getFirebaseAuth();

        medico = (Medico) getIntent().getSerializableExtra("medico");

        setComponentes();

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medico.setNome(editNome.getText().toString());
                medico.setTelefone(editTelefone.getText().toString());
                medico.setEmail(editEmail.getText().toString());
                medico.setEspecialidade(editEspecialidade.getText().toString());
                medico.setId(String.valueOf(medico.getCRM()));
                databaseReference.child("unidades").child(auth.getCurrentUser().getUid()).child("medicos")
                        .child(String.valueOf(medico.getCRM())).setValue(medico).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(EditarMedicoActivity.this, "Médico Atualizado", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditarMedicoActivity.this, "Falha ao atualizar", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });
    }

    private void setComponentes() {
        editNome.setText(medico.getNome());
        editCrm.setText(String.valueOf(medico.getCRM()));
        editTelefone.setText(medico.getTelefone());
        editEmail.setText(medico.getEmail());
        editEspecialidade.setText(medico.getEspecialidade());
    }

    private void inicializarComponentes() {
        editNome = findViewById(R.id.editMedicoNome);
        editCrm = findViewById(R.id.editMedicoCrm);
        editTelefone = findViewById(R.id.editMedicoTelefone);
        editEmail = findViewById(R.id.editMedicoEmail);
        editEspecialidade = findViewById(R.id.editMedicoEspecialidae);
        progressBar = findViewById(R.id.progressEditMedico);
        btnAtualizar = findViewById(R.id.buttonEditarMedico);
    }
}
