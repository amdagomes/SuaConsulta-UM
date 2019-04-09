package com.ifpb.suaconsulta_um.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.firebase.ConfiguracaoFirebase;
import com.ifpb.suaconsulta_um.model.UnidadeSaude;

public class ContaActivity extends AppCompatActivity {

    private final String ARQUIVO_PREFERENCIAS = "arquivoPreferencias";

    private FirebaseAuth auth;
    private SharedPreferences preferences;
    private TextView nome, cnpj, telefone, email, rua, bairro;
    private UnidadeSaude unidadeSaude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        toolbar.setTitle("Conta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = ConfiguracaoFirebase.getFirebaseAuth();
        preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        unidadeSaude = new UnidadeSaude();

        incializarComponentes();
//        recuperaPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuracoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //trata clique do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemEditar:
                startActivity(new Intent(ContaActivity.this, EditarInformacoesActivity.class));
                break;
//            case R.id.itemAlterarSenha:
//                //ação
//                break;
            case R.id.itemSair:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void incializarComponentes() {
        nome = findViewById(R.id.textContaNome);
        cnpj = findViewById(R.id.textContaCNPJ);
        rua = findViewById(R.id.textContaRua);
        bairro = findViewById(R.id.textContaBairro);
        email = findViewById(R.id.textContaEmail);
        telefone = findViewById(R.id.textContaTelefone);
    }

    private void setValores() {
        nome.setText(unidadeSaude.getNome());
        email.setText(unidadeSaude.getEmail());
        cnpj.setText(unidadeSaude.getCNPJ());
        bairro.setText(unidadeSaude.getBairro());
        rua.setText(unidadeSaude.getRua());
        telefone.setText(unidadeSaude.getTelefone());
    }

    private void recuperaPreferences() {
        unidadeSaude.setNome(preferences.getString("nome", "não definido"));
        unidadeSaude.setCNPJ(preferences.getString("cnpj", "não definido"));
        unidadeSaude.setTelefone(preferences.getString("telefone", "não definido"));
        unidadeSaude.setRua(preferences.getString("rua", "não definido"));
        unidadeSaude.setBairro(preferences.getString("bairro", "não definido"));
        unidadeSaude.setEmail(preferences.getString("email", "não definido"));
        setValores();
    }

    private void logout() {
        try{
            preferences.edit().clear().commit();
            auth.signOut();
            startActivity(new Intent(ContaActivity.this, LoginActivity.class));
            finish();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaPreferences();
    }
}
