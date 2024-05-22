package com.example.hallelapp.model;

public class LocalEventoLocalizacaoRequest {

    private String id;
    private String localizacao;


    @Override
    public String toString() {
        return "LocalEventoLocalizacaoRequest{" +
                "id='" + id + '\'' +
                ", localizacao='" + localizacao + '\'' +
                '}';
    }

    public LocalEventoLocalizacaoRequest toRequest(LocalEvento localEvento){
        LocalEventoLocalizacaoRequest request = new LocalEventoLocalizacaoRequest();
        request.setId(localEvento.getId());
        request.setLocalizacao(localEvento.getLocalizacao());
        return request;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
