package com.ifpb.suaconsulta_um.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.activity.EditarMedicoActivity;
import com.ifpb.suaconsulta_um.firebase.ConfiguracaoFirebase;
import com.ifpb.suaconsulta_um.model.Medico;

import java.io.Serializable;
import java.util.List;

public class MedicosAdapter extends RecyclerView.Adapter<MedicosAdapter.MyViewHolder> {

    private List<Medico> medicos;
    private Context context;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    public MedicosAdapter(List<Medico> medicos, Context context) {
        this.medicos = medicos;
        this.context = context;
        this.databaseReference = ConfiguracaoFirebase.getDatabaseReference();
        this.auth = ConfiguracaoFirebase.getFirebaseAuth();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_medicos, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        final Medico medico = medicos.get(i);
        myViewHolder.nome.setText(medico.getNome());
        myViewHolder.crm.setText(String.valueOf(medico.getCRM()));
        myViewHolder.telefone.setText(medico.getTelefone());
        myViewHolder.email.setText(medico.getEmail());
        myViewHolder.especialidade.setText(medico.getEspecialidade());

        myViewHolder.btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditarMedicoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("medico", (Serializable) medico);

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        myViewHolder.btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("unidades").child(auth.getCurrentUser().getUid()).child("medicos")
                        .child(String.valueOf(medico.getCRM())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context, "Médico removido", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Falha ao remover Médico", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nome, crm, telefone, email, especialidade;
        private Button btnAtualizar, btnRemover;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textMedicoNome);
            crm = itemView.findViewById(R.id.textMedicoCRM);
            telefone = itemView.findViewById(R.id.textMedicoTelefone);
            email = itemView.findViewById(R.id.textMedicoEmail);
            especialidade = itemView.findViewById(R.id.textMedicoEspecialidade);
            btnAtualizar = itemView.findViewById(R.id.btnMedicoAtualizar);
            btnRemover = itemView.findViewById(R.id.btnMedicoRemover);
        }
    }
}
