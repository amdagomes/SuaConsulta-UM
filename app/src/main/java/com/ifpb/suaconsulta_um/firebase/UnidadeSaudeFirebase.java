package com.ifpb.suaconsulta_um.firebase;

import com.google.firebase.database.DatabaseReference;
import com.ifpb.suaconsulta_um.model.UnidadeSaude;

public class UnidadeSaudeFirebase {

    private static DatabaseReference unidadeRef = ConfiguracaoFirebase.getDatabaseReference().child("unidades");

    public static void salvar(UnidadeSaude unidade){
        unidadeRef.child(unidade.getId()).setValue(unidade);
    }

    public static DatabaseReference getUnidadeRef(){
        return unidadeRef;
    }
}
