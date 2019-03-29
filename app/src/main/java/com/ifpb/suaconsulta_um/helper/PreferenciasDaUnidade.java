package com.ifpb.suaconsulta_um.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ifpb.suaconsulta_um.firebase.ConfiguracaoFirebase;
import com.ifpb.suaconsulta_um.firebase.UnidadeSaudeFirebase;
import com.ifpb.suaconsulta_um.model.UnidadeSaude;

import static android.content.Context.MODE_PRIVATE;

public class PreferenciasDaUnidade {

    private  static FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAuth();
    private static final String ARQUIVO_PREFERENCIAS = "arquivoPreferencias";

    public static void recuperarUsuarioLogado(final Context context) {
        DatabaseReference referenceUnidade = UnidadeSaudeFirebase.getUnidadeRef();
        referenceUnidade.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UnidadeSaude usuarioLogado = dataSnapshot.getValue(UnidadeSaude.class);
                setarPreferencias(context, usuarioLogado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void setarPreferencias(Context context, UnidadeSaude usuarioLogado) {

        SharedPreferences preferences = context.getSharedPreferences(ARQUIVO_PREFERENCIAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nome", usuarioLogado.getNome());
        editor.putString("cnpj", usuarioLogado.getCNPJ());
        editor.putString("email", usuarioLogado.getEmail());
        editor.putString("telefone", usuarioLogado.getTelefone());
        editor.putString("rua", usuarioLogado.getRua());
        editor.putString("bairro", usuarioLogado.getBairro());
        editor.putString("id", usuarioLogado.getId());

        //editor.commit();
        editor.apply();
    }

}
