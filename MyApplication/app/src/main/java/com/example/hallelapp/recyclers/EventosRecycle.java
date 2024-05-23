package com.example.hallelapp.recyclers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.R;
import com.example.hallelapp.model.Eventos;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

import java.util.ArrayList;
import java.util.List;

public class EventosRecycle extends RecyclerView.Adapter<EventosRecycle.MyViewHolder> {


    private List<AllEventosListResponse> eventos = new ArrayList<>();

    public List<AllEventosListResponse> getEventos() {
        return eventos;
    }

    public void setEventos(List<AllEventosListResponse> eventos) {
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventos_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AllEventosListResponse evento = eventos.get(position);

        holder.textTitulo.setText(evento.getTitulo());

        String suaStringBase64 = evento.getImagem();

        // Verificar se a string não é nula e contém a vírgula
        if (suaStringBase64 != null && suaStringBase64.contains(",")) {
            // Obter a parte da string que contém os dados em base64
            String[] partes = suaStringBase64.split(",");
            if (partes.length > 1) {
                String dadosBase64 = partes[1];

                // Decodificar a string base64 em uma imagem Bitmap
                byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                holder.imageEvento.setImageBitmap(decodedByte);
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {

        if (eventos != null) {
            return eventos.size();
        } else {
            return 0; // ou qualquer outro valor padrão, dependendo do seu caso de uso
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textTitulo;
        ImageView imageEvento;

        public MyViewHolder(View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textTituloEvento);
            imageEvento = itemView.findViewById(R.id.imagemEvento);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }




}
