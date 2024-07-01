package com.example.hallelapp.recyclers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.R;

import java.util.List;

public class EventosArquivadosRecycle extends RecyclerView.Adapter<EventosArquivadosRecycle.ViewHolder> {

    private List<MyItem> itemList;
    private Context context;

    public EventosArquivadosRecycle(Context context, List<MyItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.eventos_arq_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyItem item = itemList.get(position);

        holder.imageView.setImageResource(item.getImageResourceId());
        holder.textViewTitle.setText(item.getTitle());
        holder.textViewDate.setText(item.getDate());

        holder.buttonDesarquivar.setOnClickListener(v -> {
            // Handle Desarquivar button click
        });

        holder.buttonDelete.setOnClickListener(v -> {
            // Handle Delete button click
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


// Model Class


    public static class MyItem {
        private int imageResourceId;
        private String title;
        private String date;

        public MyItem(int imageResourceId, String title, String date) {
            this.imageResourceId = imageResourceId;
            this.title = title;
            this.date = date;
        }

        public int getImageResourceId() {
            return imageResourceId;
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }
    }
}