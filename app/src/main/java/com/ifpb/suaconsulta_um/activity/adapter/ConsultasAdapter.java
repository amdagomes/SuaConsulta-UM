package com.ifpb.suaconsulta_um.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.firebase.ConfiguracaoFirebase;
import com.ifpb.suaconsulta_um.model.Consulta;
import com.ifpb.suaconsulta_um.model.Medico;

import java.util.List;

public class ConsultasAdapter extends RecyclerView.Adapter<ConsultasAdapter.MyViewHolder>{

    private DatabaseReference reference;

    private List<Consulta> consultas;
    private Context context;

    public ConsultasAdapter(List<Consulta> consultas, Context context) {
        this.consultas = consultas;
        this.context = context;

        reference = ConfiguracaoFirebase.getDatabaseReference().child("unidades");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_consultas, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        Consulta consulta = consultas.get(i);
        myViewHolder.data.setText(consulta.getData());
        myViewHolder.totalVagas.setText(String.valueOf(consulta.getNumVagas()));
        myViewHolder.vagasPreenchidas.setText(String.valueOf(consulta.getVagasPreenchidas()));

        String unidade = consulta.getUnidadeMedica();
        String crm = consulta.getMedico();
        Log.i("LISTACONSULTAS", "crm: " + crm + ", uniade: " + unidade);
        reference.child(unidade).child("medicos").child(crm).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Medico medico = dataSnapshot.getValue(Medico.class);
//                Log.i("LISTACONSULTAS", medico.toString());
                myViewHolder.medico.setText(medico.getNome());
                myViewHolder.crm.setText(String.valueOf(medico.getCRM()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return consultas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView medico, data, totalVagas, vagasPreenchidas, crm;

        public MyViewHolder(View itemView) {
            super(itemView);

            medico = itemView.findViewById(R.id.textConsultaMedico);
            data = itemView.findViewById(R.id.textConsultaData);
            totalVagas = itemView.findViewById(R.id.textConsultaTotal);
            vagasPreenchidas = itemView.findViewById(R.id.textConsultaPreenchidas);
            crm = itemView.findViewById(R.id.textConsultaCrm);
        }
    }
}
