package com.example.hallelapp.payload.requerimento;

public class BuscarIdAssociadoReq {
    String email;

    public BuscarIdAssociadoReq(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "BuscarIdAssociadoReq{" +
                "email='" + email + '\'' +
                '}';
    }
}
