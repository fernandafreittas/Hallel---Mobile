package com.example.hallelapp.htpp;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.payload.requerimento.BuscarIdAssociadoReq;
import com.example.hallelapp.payload.requerimento.ParticiparEventosRequest;
import com.example.hallelapp.payload.requerimento.SeVoluntariarEventoReq;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpMembro {

    private String token;
    //private static final String UrlBase = "http://192.168.1.4:8080/api/";

    //fernanda
    private static final String UrlBase = "http://10.100.82.4:8080/api/";
    public static final MediaType JSON = MediaType.get("application/json");

    // Interface para tratar da conexão da Api caso seja bem sucedida ou dê erro
    public interface HttpCallback {
        void onSuccess(String response);
        void onFailure(IOException e);
    }

    public void participarDeEvento(final ParticiparEventosRequest participarEventosRequest
                                   ,   InformacoesDaSessao informacoesDaSessao,final HttpCallback callback
                                ) {
        token = informacoesDaSessao.getToken();

        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(participarEventosRequest);
        String url = UrlBase + "eventos/participarEvento";

        Log.d("HttpMembro", "url: " + url);
        Log.d("HttpMembro", participarEventosRequest.toString());

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println("deu certooo");
                    callback.onSuccess(responseBody);
                } else {
                    callback.onFailure(new IOException("Erro ao realizar requisição: " + response.code()));
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

                System.out.println("deu errado");

                callback.onFailure(e);
            }
        });
    }

    public void InformacoesDePerfil (
                                     InformacoesDaSessao informacoesDaSessao ,final HttpMain.HttpCallback callback) {
        OkHttpClient client = new OkHttpClient();

        token = informacoesDaSessao.getToken();

        String url = UrlBase + "membros/perfil/"+informacoesDaSessao.getId();

        System.out.println(url);

        System.out.println(token);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println(responseBody);
                    callback.onSuccess(responseBody);
                } else {
                    callback.onFailure(new IOException("Erro ao realizar requisição: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }
        });
    }


    public void BuscarIdAssociado (BuscarIdAssociadoReq buscarIdAssociadoReq,
            InformacoesDaSessao informacoesDaSessao , final HttpMain.HttpCallback callback) {
        OkHttpClient client = new OkHttpClient();

        token = informacoesDaSessao.getToken();

        Gson gson = new Gson();
        String json = gson.toJson(buscarIdAssociadoReq);

        String url = UrlBase + "membros/buscarAssociadoEmail";

        System.out.println(url);

        System.out.println(token);

        System.out.println(buscarIdAssociadoReq.toString());

        RequestBody body = RequestBody.create(json, JSON);


        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println(responseBody);
                    callback.onSuccess(responseBody);
                } else {
                    callback.onFailure(new IOException("Erro ao realizar requisição: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }
        });
    }





}
