package com.example.hallelapp.payload.requerimento;

import com.example.hallelapp.model.DoacaoObjetosEventos;

public class AlteraRecebimentoRequest {

    private DoacaoObjetosEventos doacaoObjetosEventos;
    private Boolean isRecebido;

    public DoacaoObjetosEventos getDoacaoObjetosEventos() {
        return doacaoObjetosEventos;
    }

    public void setDoacaoObjetosEventos(DoacaoObjetosEventos doacaoObjetosEventos) {
        this.doacaoObjetosEventos = doacaoObjetosEventos;
    }

    public Boolean getRecebido() {
        return isRecebido;
    }

    public void setRecebido(Boolean recebido) {
        isRecebido = recebido;
    }

    @Override
    public String toString() {
        return "AlteraRecebimentoRequest{" +
                "doacaoObjetosEventos=" + doacaoObjetosEventos +
                ", isRecebido=" + isRecebido +
                '}';
    }
}
