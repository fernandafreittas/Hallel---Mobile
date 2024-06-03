package com.example.hallelapp.htpp;

import android.util.Log;

import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.payload.requerimento.AdministradorLoginRequest;
import com.example.hallelapp.payload.requerimento.EventosRequest;
import com.example.hallelapp.payload.requerimento.LoginRequest;
import com.example.hallelapp.payload.requerimento.ParticiparEventosRequest;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpAdm {

    private String token;
    private static final String UrlBase = "http://192.168.1.4:8080/api/administrador";
    public static final MediaType JSON = MediaType.get("application/json");

    // Interface para tratar da conexão da Api caso seja bem sucedida ou dê erro
    public interface HttpCallback {
        void onSuccess(String response);
        void onFailure(IOException e);
    }

    public void RealizarLogin(final AdministradorLoginRequest administradorLoginRequest, final HttpAdm.HttpCallback callback
    ) {


        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(administradorLoginRequest);
        String url = UrlBase + "/login";

        Log.d("HttpADM", "url: " + url);
        Log.d("HttpADM", administradorLoginRequest.toString());

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
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


    public void criarEvento(final EventosRequest eventosRequest, AuthenticationResponse authenticationResponse, final HttpAdm.HttpCallback callback
    ) {


        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(eventosRequest);
        String url = UrlBase + "/eventos/create";

        Log.d("HttpADM", "url: " + url);

        Log.d("HttpADM", eventosRequest.toString());
        System.out.println("oi " + eventosRequest.toString());
        System.out.println("oi " + eventosRequest.getDate());

        token =authenticationResponse.getToken();


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

    public void ListLocaisEventos( AuthenticationResponse authenticationResponse, final HttpAdm.HttpCallback callback
    ) {


        OkHttpClient client = new OkHttpClient();

        String url = UrlBase + "/locais";

        Log.d("HttpADM", "url: " + url);


        token =authenticationResponse.getToken();


        Request request = new Request.Builder()
                .url(url)
                .get()
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

    public void ListInformacoesEventoById(String id, AuthenticationResponse authenticationResponse, final HttpAdm.HttpCallback callback
    ) {


        OkHttpClient client = new OkHttpClient();

        String url = UrlBase + "/eventos/"+id+"/list";

        Log.d("HttpADM", "url: " + url);


        token =authenticationResponse.getToken();


        Request request = new Request.Builder()
                .url(url)
                .get()
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

    public void EditarEvento(String id,String data,final EventosRequest eventosRequest, AuthenticationResponse authenticationResponse, final HttpAdm.HttpCallback callback
    ) {


        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(eventosRequest);
        String url = UrlBase + "/eventos/"+id+"/"+data+"/edit";

        Log.d("HttpADM", "url: " + url);
        Log.d("HttpADM", eventosRequest.toString());

        System.out.println("oi " + eventosRequest.toString());
        System.out.println("oi " + eventosRequest.getDate());

        token =authenticationResponse.getToken();

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






}
