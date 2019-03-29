package com.ifpb.suaconsulta_um.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.firebase.ConfiguracaoFirebase;
import com.ifpb.suaconsulta_um.helper.PreferenciasDaUnidade;
import com.ifpb.suaconsulta_um.model.UnidadeSaude;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private Button buttonLogin;
    private EditText textEmail;
    private EditText textSenha;
    private ProgressBar progressLogin;

    private UnidadeSaude unidadeSaude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressLogin.setVisibility(View.VISIBLE);

                String email = textEmail.getText().toString();
                String senha = textSenha.getText().toString();

                if(email.isEmpty() || senha.isEmpty()){
                    progressLogin.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Preencha os campos", Toast.LENGTH_LONG).show();
                } else{
                    unidadeSaude = new UnidadeSaude();
                    unidadeSaude.setEmail(email);
                    unidadeSaude.setSenha(senha);
                    validarLogin(unidadeSaude);
                }
            }
        });
    }

    public boolean verificarUsuarioLogado(){
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return true;
        }
        return false;
    }

    public void validarLogin(UnidadeSaude usuario) {
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressLogin.setVisibility(View.GONE);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //busca usuário logado e salva suas informações no sharedPreferences
                    PreferenciasDaUnidade.recuperarUsuarioLogado(getApplicationContext());
                    finish();
                } else {
                    progressLogin.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Erro ao tentar fazer login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializarComponentes() {
        buttonLogin = findViewById(R.id.buttonLogin);
        textEmail = findViewById(R.id.editLoginEmail);
        textSenha = findViewById(R.id.editLoginSenha);
        progressLogin = findViewById(R.id.progressLogin);
    }

    public void carregaTelaCadastro(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }
}
