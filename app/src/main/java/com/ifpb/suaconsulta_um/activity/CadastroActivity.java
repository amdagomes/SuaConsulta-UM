package com.ifpb.suaconsulta_um.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.firebase.ConfiguracaoFirebase;
import com.ifpb.suaconsulta_um.firebase.UnidadeSaudeFirebase;
import com.ifpb.suaconsulta_um.helper.PreferenciasDaUnidade;
import com.ifpb.suaconsulta_um.model.UnidadeSaude;

public class CadastroActivity extends AppCompatActivity {

    FirebaseAuth auth;

    private EditText textNome;
    private EditText textCnpj;
    private EditText textTelefone;
    private EditText textRua;
    private EditText textBairro;
    private EditText textEmail;
    private EditText textSenha;
    private ProgressBar progressCadastro;
    private Button buttonCadastrar;

    private UnidadeSaude unidadeSaude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializaComponentes();

        unidadeSaude = new UnidadeSaude();

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressCadastro.setVisibility(View.VISIBLE);
                String nome = textNome.getText().toString();
                String numCnpj = textCnpj.getText().toString();
                String rua = textRua.getText().toString();
                String bairro = textBairro.getText().toString();
                String telefone = textTelefone.getText().toString();
                String email = textEmail.getText().toString();
                String senha = textSenha.getText().toString();

                if(nome.isEmpty() || numCnpj.isEmpty() || rua.isEmpty() || bairro.isEmpty() || email.isEmpty() || senha.isEmpty()){
                    progressCadastro.setVisibility(View.GONE);
                    Toast.makeText(CadastroActivity.this, "Preencha os campos com *", Toast.LENGTH_LONG).show();
                } else{
                    unidadeSaude.setNome(nome);
                    unidadeSaude.setCNPJ(numCnpj);
                    unidadeSaude.setRua(rua);
                    unidadeSaude.setBairro(bairro);
                    unidadeSaude.setTelefone(telefone);
                    unidadeSaude.setEmail(email);
                    unidadeSaude.setSenha(senha);
                    cadastrarUnnidadeSaude(unidadeSaude);
                }
            }
        });
    }

    private void cadastrarUnnidadeSaude(final UnidadeSaude unidadeSaude) {
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(
                unidadeSaude.getEmail(),
                unidadeSaude.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    try{
                        progressCadastro.setVisibility(View.GONE);
                        //Salvar dados no firebase
                        String idUnidade = task.getResult().getUser().getUid();
                        unidadeSaude.setId(idUnidade);

                        UnidadeSaudeFirebase.salvar(unidadeSaude);
                        PreferenciasDaUnidade.recuperarUsuarioLogado(getApplicationContext());

                        Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                        finish();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    progressCadastro.setVisibility(View.GONE);
                    buttonCadastrar.setEnabled(true);
                    String erroExcecao;
                    try {
                        throw task.getException();
                    }  catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "Digite um email válido";
                    } catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "Essa conta já foi cadastrada";
                    } catch (Exception e){
                        erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void inicializaComponentes() {
        textNome = findViewById(R.id.inputCadastroNome);
        textCnpj = findViewById(R.id.inputCadastroCnpj);
        textRua = findViewById(R.id.inputCadastroRua);
        textBairro = findViewById(R.id.inputCadastroBairro);
        textTelefone = findViewById(R.id.inputCadastroTelefone);
        textEmail = findViewById(R.id.inputCadastroEmail);
        textSenha = findViewById(R.id.inputCadastroSenha);
        progressCadastro = findViewById(R.id.progressCadastro);
        buttonCadastrar = findViewById(R.id.button_cadastrar);
    }

    public void carregarTelaLogin(View view) {
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
