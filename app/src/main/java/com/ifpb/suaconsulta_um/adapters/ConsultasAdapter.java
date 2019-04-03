package com.ifpb.suaconsulta_um.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ifpb.suaconsulta_um.model.Consulta;

import java.util.List;

public class ConsultasAdapter extends RecyclerView.Adapter<ConsultasAdapter.MyViewHolder>{

    public ConsultasAdapter(List<Consulta> consultas, Context context) {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
