package com.example.hallelapp.payload.resposta;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private String token;
    private Object objeto;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                ", objeto=" + objeto +
                '}';
    }
}
