package com.example.hallelapp.payload.resposta;

import com.example.hallelapp.model.Membro;
import com.example.hallelapp.model.Roles;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Set;

public class LoginResponse {
    private String token;

    Membro objeto;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Membro getMembro() {
        return objeto;
    }

    public void setMembro(Membro membro) {
        this.objeto = membro;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", membro=" + objeto +
                '}';
    }
}
