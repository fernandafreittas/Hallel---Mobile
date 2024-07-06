package com.example.hallelapp.recyclers;

import com.example.hallelapp.model.EventoArquivado;

public interface OnEventoArquivadoClickListener {
    void onDesarquivarClick(EventoArquivado eventoArquivado);
    void onDeleteClick(EventoArquivado eventoArquivado);
}