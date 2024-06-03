package com.example.hallelapp.recyclers;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.R;

import java.util.ArrayList;
import java.util.List;
public class ColaboradorAdapter extends RecyclerView.Adapter<ColaboradorAdapter.ColaboradorViewHolder> {

    private List<String> colaboradores;
    private Context context;

    public ColaboradorAdapter(Context context) {
        this.context = context;
        this.colaboradores = new ArrayList<>();
        // Inicializa com um colaborador vazio
        colaboradores.add("");
    }

    @NonNull
    @Override
    public ColaboradorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycle, parent, false);
        return new ColaboradorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColaboradorViewHolder holder, int position) {
        String colaborador = colaboradores.get(position);
        holder.editText.setText(colaborador);

        holder.buttonAdicionar.setOnClickListener(v -> {
            colaboradores.add(position + 1, ""); // Adiciona novo colaborador após o atual
            notifyItemInserted(position + 1);
        });

        holder.buttonDelete.setOnClickListener(v -> {
            if (colaboradores.size() > 1) {
                colaboradores.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, colaboradores.size());
                Toast.makeText(context, "Colaborador removido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Não é possível remover o único colaborador", Toast.LENGTH_SHORT).show();
            }
        });

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                colaboradores.set(position, s.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return colaboradores.size();
    }

    public List<String> getColaboradores() {
        return colaboradores;
    }

    public void adicionaColaboradores (List<String> listcolaboradores){

        colaboradores.clear();
        colaboradores.addAll(listcolaboradores);
        notifyDataSetChanged();
    }

    static class ColaboradorViewHolder extends RecyclerView.ViewHolder {

        EditText editText;
        Button buttonAdicionar;
        Button buttonDelete;

        public ColaboradorViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.textVoluntario);
            buttonAdicionar = itemView.findViewById(R.id.adicionar);
            buttonDelete = itemView.findViewById(R.id.deletar);
        }
    }
}

