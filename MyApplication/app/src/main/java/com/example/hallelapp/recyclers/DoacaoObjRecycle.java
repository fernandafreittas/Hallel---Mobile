package com.example.hallelapp.recyclers;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.R;

import java.util.ArrayList;
import java.util.List;

public class DoacaoObjRecycle extends RecyclerView.Adapter<DoacaoObjRecycle.DoacaoViewHolder> {
    private List<DoacaoItem> doacaoItems;

    public DoacaoObjRecycle() {
        this.doacaoItems = new ArrayList<>();
        this.doacaoItems.add(new DoacaoItem()); // Add initial item
    }

    @NonNull
    @Override
    public DoacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_donation_obj, parent, false);
        return new DoacaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoacaoViewHolder holder, int position) {
        DoacaoItem currentItem = doacaoItems.get(position);

        holder.editTextItem.setText(currentItem.getItem());
        holder.editTextQuantity.setText(currentItem.getQuantity());

        // Listener para o campo do item
        holder.editTextItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentItem.setItem(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Listener para o campo de quantidade
        holder.editTextQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentItem.setQuantity(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        holder.buttonAddObj.setOnClickListener(v -> addItem(holder.getAdapterPosition()));
        holder.buttonDeleteObj.setOnClickListener(v -> removeItem(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return doacaoItems.size();
    }

    public void addItem(int position) {
        doacaoItems.add(position + 1, new DoacaoItem());
        notifyItemInserted(position + 1);
    }

    public void removeItem(int position) {
        if (doacaoItems.size() > 1) {
            doacaoItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public List<DoacaoItem> getDoacaoItems() {
        return doacaoItems;
    }

    static class DoacaoViewHolder extends RecyclerView.ViewHolder {
        EditText editTextItem;
        EditText editTextQuantity;
        Button buttonAddObj;
        Button buttonDeleteObj;

        public DoacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextItem = itemView.findViewById(R.id.editText5);
            editTextQuantity = itemView.findViewById(R.id.editText6);
            buttonAddObj = itemView.findViewById(R.id.buttonAddObj);
            buttonDeleteObj = itemView.findViewById(R.id.buttonDeleteObj);
        }
    }

    public static class DoacaoItem {
        private String item;
        private String quantity;

        public DoacaoItem() {
            this.item = "";
            this.quantity = "";
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }
}
