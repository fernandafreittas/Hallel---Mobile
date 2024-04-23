package com.example.hallelapp.model;

public class InformacoesDaSessao {

    private String token;
    private String id;

    private Boolean lembreDeMin;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getLembreDeMin() {
        return lembreDeMin;
    }

    public void setLembreDeMin(Boolean lembreDeMin) {
        this.lembreDeMin = lembreDeMin;
    }

    @Override
    public String toString() {
        return "InformacoesDaSessao{" +
                "token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", lembreDeMin=" + lembreDeMin +
                '}';
    }
}
