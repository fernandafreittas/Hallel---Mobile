package com.example.hallelapp.model;

import java.io.Serializable;

public class InformacoesDaSessao implements Serializable {

    private String token;
    private String id;

    private Boolean lembreDeMin;

    private String informacao1;
    private String informacao2;


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

    public String getInformacao1() {
        return informacao1;
    }

    public void setInformacao1(String informacao1) {
        this.informacao1 = informacao1;
    }

    public String getInformacao2() {
        return informacao2;
    }

    public void setInformacao2(String informacao2) {
        this.informacao2 = informacao2;
    }

    @Override
    public String toString() {
        return "InformacoesDaSessao{" +
                "token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", lembreDeMin=" + lembreDeMin +
                ", informacao1='" + informacao1 + '\'' +
                ", informacao2='" + informacao2 + '\'' +
                '}';
    }
}
