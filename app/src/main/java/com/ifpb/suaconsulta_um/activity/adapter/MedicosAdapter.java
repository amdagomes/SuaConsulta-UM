package com.ifpb.suaconsulta_um.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifpb.suaconsulta_um.R;
import com.ifpb.suaconsulta_um.model.Medico;

import java.util.List;

public class MedicosAdapter extends RecyclerView.Adapter<MedicosAdapter.MyViewHolder> {

    private List<Medico> medicos;
    private Context context;

    public MedicosAdapter(List<Medico> medicos, Context context) {
        this.medicos = medicos;
        this.context = context;
        Log.i("MEDICOS", String.valueOf(medicos.size()));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_medicos, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Medico medico = medicos.get(i);
        myViewHolder.nome.setText(medico.getNome());
        myViewHolder.crm.setText(String.valueOf(medico.getCRM()));
        myViewHolder.telefone.setText(medico.getTelefone());
        myViewHolder.email.setText(medico.getEmail());
        myViewHolder.especialidade.setText(medico.getEspecialidade());
    }

    @Override
    public int getItemCount() {
        return medicos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nome, crm, telefone, email, especialidade;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textMedicoNome);
            crm = itemView.findViewById(R.id.textMedicoCRM);
            telefone = itemView.findViewById(R.id.textMedicoTelefone);
            email = itemView.findViewById(R.id.textMedicoEmail);
            especialidade = itemView.findViewById(R.id.textMedicoEspecialidade);
        }
    }
}
