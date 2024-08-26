package com.example.hallelapp.recyclers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.R;
import com.example.hallelapp.model.Eventos;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private Context context;
    private List<AllEventosListResponse> eventoList;
    private OnArquivarClickListener onArquivarClickListener;

    public EventoAdapter(Context context, List<AllEventosListResponse> eventoList, OnArquivarClickListener onArquivarClickListener) {
        this.context = context;
        this.eventoList = eventoList;
        this.onArquivarClickListener = onArquivarClickListener;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.eventoscadastrados_adapter, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        AllEventosListResponse evento = eventoList.get(position);
        holder.textTituloEvento.setText(evento.getTitulo());

        String suaStringBase64 = evento.getImagem();
        if (suaStringBase64 != null && suaStringBase64.contains(",")) {
            String[] partes = suaStringBase64.split(",");
            if (partes.length > 1) {
                String dadosBase64 = partes[1];
                byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.imagemEvento.setImageBitmap(decodedByte);
            }
        }

        holder.buttonArquivar.setOnClickListener(v -> {
            if (onArquivarClickListener != null) {
                onArquivarClickListener.onArquivarClick(evento);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        ImageView imagemEvento;
        TextView textTituloEvento;
        Button buttonArquivar;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemEvento = itemView.findViewById(R.id.imagemEventocad);
            textTituloEvento = itemView.findViewById(R.id.textTituloEventocad);
            buttonArquivar = itemView.findViewById(R.id.button6);
        }
    }

    public interface OnArquivarClickListener {
        void onArquivarClick(AllEventosListResponse evento);
    }
}

