package com.example.hallelapp.recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.R;
import com.example.hallelapp.model.LocalEvento;

import java.util.List;

public class LocalEditRecycle extends RecyclerView.Adapter<LocalEditRecycle.LocalViewHolder> {

    private Context context;
    private List<LocalEvento> locais;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onDeleteClick(int position);
    }

    public LocalEditRecycle(Context context, List<LocalEvento> locais, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.locais = locais;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.local_edit_recycle, parent, false);
        return new LocalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder holder, int position) {
        LocalEvento local = locais.get(position);
        holder.textViewLocal.setText(local.getLocalizacao());
    }

    @Override
    public int getItemCount() {
        return locais.size();
    }

    class LocalViewHolder extends RecyclerView.ViewHolder {

        TextView textViewLocal;
        Button buttonDeleteLocal;

        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLocal = itemView.findViewById(R.id.textViewLocal);

            buttonDeleteLocal = itemView.findViewById(R.id.buttonDeleteLocal);



            buttonDeleteLocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
