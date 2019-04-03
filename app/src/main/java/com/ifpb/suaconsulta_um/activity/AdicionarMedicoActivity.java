package com.ifpb.suaconsulta_um.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.firebase.ConfiguracaoFirebase;
import com.ifpb.suaconsulta_um.model.Medico;

public class AdicionarMedicoActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private EditText editNome, editCrm, editTelefone, editEmail, editEspecialidade;
    private Button cadastrar;
    private ProgressBar progressBar;
    private Medico medico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiconar_medico);

        Toolbar toolbar = findViewById(R.id.toolbarAdcMedico);
        toolbar.setTitle("Cadastrar Médico");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = ConfiguracaoFirebase.getFirebaseAuth();

        inicializarComponentes();
        progressBar.setVisibility(View.GONE);

        medico = new Medico();

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                cadastrar.setEnabled(false);

                String nome = editNome.getText().toString();
                String telefone = editTelefone.getText().toString();
                String crm = editCrm.getText().toString();
                String email = editEmail.getText().toString();
                String especialidade = editEspecialidade.getText().toString();

                if(nome.isEmpty() || crm.isEmpty() || email.isEmpty() || especialidade.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    cadastrar.setEnabled(true);
                } else {
                    medico.setNome(nome);
                    medico.setTelefone(telefone);
                    medico.setCRM(Integer.parseInt(crm));
                    medico.setEmail(email);
                    medico.setEspecialidade(especialidade);

                    cadastrarMedico(medico);

                    progressBar.setVisibility(View.GONE);
                    cadastrar.setEnabled(true);

                }
            }
        });
    }

    private void cadastrarMedico(Medico medico) {
        try{
            databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("unidades");
            databaseReference.child(auth.getCurrentUser().getUid()).child("medicos").child(String.valueOf(medico.getCRM())).setValue(medico);
            Toast.makeText(AdicionarMedicoActivity.this, "Médico cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception ex){
            progressBar.setVisibility(View.GONE);
            cadastrar.setEnabled(true);
            Log.e("CADASTROMEDICO", ex.getMessage());
            Toast.makeText(this, "Falha ao cadastrar médico", Toast.LENGTH_SHORT).show();
        }

    }

    private void inicializarComponentes() {
        editNome = findViewById(R.id.editAdcMedicoNome);
        editCrm = findViewById(R.id.editAdcMedicoCRM);
        editTelefone = findViewById(R.id.editAdcMedicoTelefone);
        editEmail = findViewById(R.id.editAdcMedicoEmail);
        editEspecialidade = findViewById(R.id.editAdcMedicoEspecialidae);
        progressBar = findViewById(R.id.progressAdcMedico);
        cadastrar = findViewById(R.id.buttonAdcMedico);
    }
}
