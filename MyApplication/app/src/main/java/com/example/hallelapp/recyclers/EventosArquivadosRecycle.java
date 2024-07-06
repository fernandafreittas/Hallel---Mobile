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
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.R;
import com.example.hallelapp.model.EventoArquivado;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EventosArquivadosRecycle extends RecyclerView.Adapter<EventosArquivadosRecycle.ViewHolder> {

    private List<EventoArquivado> itemList;
    private Context context;
    private OnEventoArquivadoClickListener listener;

    public EventosArquivadosRecycle(Context context, List<EventoArquivado> itemList, OnEventoArquivadoClickListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.eventos_arq_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventoArquivado item = itemList.get(position);

        String suaStringBase64 = item.getImagem();

        // Verificar se a string não é nula e contém a vírgula
        if (suaStringBase64 != null && suaStringBase64.contains(",")) {
            // Obter a parte da string que contém os dados em base64
            String[] partes = suaStringBase64.split(",");
            if (partes.length > 1) {
                String dadosBase64 = partes[1];

                // Decodificar a string base64 em uma imagem Bitmap
                byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                holder.imageView.setImageBitmap(decodedByte);
            }
        }

        holder.textViewTitle.setText(item.getTitulo());

        // Cria um objeto SimpleDateFormat com o formato desejado
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Converte a data para uma string
        String formattedDate = sdf.format(item.getDate());

        // Define o texto no TextView
        holder.textViewDate.setText(formattedDate);

        holder.buttonDesarquivar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDesarquivarClick(item);
            }
        });

        holder.buttonDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDate;
        Button buttonDesarquivar;
        Button buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagemEvento);
            textViewTitle = itemView.findViewById(R.id.textTituloEventoArq);
            textViewDate = itemView.findViewById(R.id.textView83);
            buttonDesarquivar = itemView.findViewById(R.id.buttonDesarquivar);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteArq);
        }
    }




}