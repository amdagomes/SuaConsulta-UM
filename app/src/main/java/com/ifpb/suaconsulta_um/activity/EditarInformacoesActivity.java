package com.ifpb.suaconsulta_um.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.firebase.UnidadeSaudeFirebase;
import com.ifpb.suaconsulta_um.helper.PreferenciasDaUnidade;
import com.ifpb.suaconsulta_um.model.UnidadeSaude;

public class EditarInformacoesActivity extends AppCompatActivity {

    private final String ARQUIVO_PREFERENCIAS = "arquivoPreferencias";
    private SharedPreferences preferences;
    private EditText editNome, editCnpj, editTelefone, editRua, editBairro;
    private Button salvar;
    private ProgressBar progressBar;
    private UnidadeSaude unidadeSaude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_informacoes);

        Toolbar toolbar = findViewById(R.id.toolbarEditar);
        toolbar.setTitle("Editar informações");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        incializarComponentes();

        preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        unidadeSaude = new UnidadeSaude();

        progressBar.setVisibility(View.GONE);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                //atualiza informações do usuário
                atualizar();
                finish();
                Toast.makeText(EditarInformacoesActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void incializarComponentes() {
        editBairro = findViewById(R.id.editEditarBairro);
        editCnpj = findViewById(R.id.editEditarCnpj);
        editRua = findViewById(R.id.editEditarRua);
        editNome = findViewById(R.id.editEditarNome);
        editTelefone = findViewById(R.id.editEditarTelefone);
        salvar = findViewById(R.id.buttonEditarSalvar);
        progressBar = findViewById(R.id.progressEditar);
    }

    private void setComponentes() {
        editNome.setText(preferences.getString("nome", "não definido"));
        editBairro.setText(preferences.getString("bairro", "não definido"));
        editRua.setText(preferences.getString("rua", "não definido"));
        editTelefone.setText(preferences.getString("telefone", "não definido"));
        editCnpj.setText(preferences.getString("cnpj", "não definido"));
    }

    private void atualizar(){
        unidadeSaude.setTelefone(editTelefone.getText().toString());
        unidadeSaude.setNome(editNome.getText().toString());
        unidadeSaude.setRua(editRua.getText().toString());
        unidadeSaude.setBairro(editBairro.getText().toString());
        unidadeSaude.setCNPJ(editCnpj.getText().toString());
        unidadeSaude.setEmail(preferences.getString("email", "não definido"));

        if(preferences.contains("id")){
            unidadeSaude.setId(preferences.getString("id", "nao definido"));
            UnidadeSaudeFirebase.salvar(unidadeSaude);
        }else {
            Toast.makeText(EditarInformacoesActivity.this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
        }

        PreferenciasDaUnidade.setarPreferencias(getApplicationContext(), unidadeSaude);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setComponentes();
    }

}
