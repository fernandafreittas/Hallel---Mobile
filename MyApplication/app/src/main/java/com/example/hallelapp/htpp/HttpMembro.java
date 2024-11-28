package com.example.hallelapp.htpp;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.payload.requerimento.BuscarIdAssociadoReq;
import com.example.hallelapp.payload.requerimento.EditPerfilRequest;
import com.example.hallelapp.payload.requerimento.ParticiparEventosRequest;
import com.example.hallelapp.payload.requerimento.SeVoluntariarEventoReq;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpMembro {

    private String token;

    private static final String UrlBase = "https://apihallel-deploy.onrender.com/api/";


    //fernanda

 //   private static final String UrlBase = "http://10.100.85.80:8080/api/";


//    private static final String UrlBase = "http://192.168.100.36:8080/api/";

    public static final MediaType JSON = MediaType.get("application/json");

    // Interface para tratar da conexão da Api caso seja bem sucedida ou dê erro
    public interface HttpCallback {
        void onSuccess(String response);
        void onFailure(IOException e);
    }

    private boolean isValidJson(String responseBody) {
        // Verifica se a resposta é um valor booleano simples
        if ("true".equals(responseBody) || "false".equals(responseBody)) {
            return true;
        }

        try {
            // Tenta interpretar como JSONObject
            new JSONObject(responseBody);
            return true;
        } catch (Exception e) {
            try {
                // Tenta interpretar como JSONArray
                new JSONArray(responseBody);
                return true;
            } catch (Exception e1) {
                return false; // Se der erro em ambos os casos, não é um JSON válido
            }
        }
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
                    if (isValidJson(responseBody)) {
                        callback.onSuccess(responseBody);
                    } else {
                        callback.onFailure(new IOException("Resposta não é um JSON válido"));
                    }
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
                    if (isValidJson(responseBody)) {
                        callback.onSuccess(responseBody);
                    } else {
                        callback.onFailure(new IOException("Resposta não é um JSON válido"));
                    }
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

    public void ListDoacoaUser (String email, InformacoesDaSessao informacoesDaSessao , final HttpMain.HttpCallback callback) {
        OkHttpClient client = new OkHttpClient();
        token = informacoesDaSessao.getToken();

        // Criar um mapa ou uma classe para o payload
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);

        Gson gson = new Gson();
        String json = gson.toJson(payload); // Converte o mapa em JSON

        String url = UrlBase + "eventos/listDoacoesDinheiroUser";

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
                    if (isValidJson(responseBody)) {
                        callback.onSuccess(responseBody);
                    } else {
                        callback.onFailure(new IOException("Resposta não é um JSON válido"));
                    }
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

    public void ListDoacoaObjetoUser (String email, InformacoesDaSessao informacoesDaSessao , final HttpMain.HttpCallback callback) {
        OkHttpClient client = new OkHttpClient();
        token = informacoesDaSessao.getToken();

        // Criar um mapa ou uma classe para o payload
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);

        Gson gson = new Gson();
        String json = gson.toJson(payload); // Converte o mapa em JSON

        String url = UrlBase + "eventos/listDoacoesObjetoUser";

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
                    if (isValidJson(responseBody)) {
                        callback.onSuccess(responseBody);
                    } else {
                        callback.onFailure(new IOException("Resposta não é um JSON válido"));
                    }
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

    public void EditPerfilUsuario (EditPerfilRequest editPerfilRequest,String id,
                                   InformacoesDaSessao informacoesDaSessao , final HttpMain.HttpCallback callback) {
        OkHttpClient client = new OkHttpClient();

        token = informacoesDaSessao.getToken();

        Gson gson = new Gson();
        String json = gson.toJson(editPerfilRequest);

        String url = UrlBase + "membros/perfil/editar/"+id;

        System.out.println(url);

        System.out.println(token);

        System.out.println(editPerfilRequest.toString());

        RequestBody body = RequestBody.create(json, JSON);


        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println(responseBody);
                    if (isValidJson(responseBody)) {
                        callback.onSuccess(responseBody);
                    } else {
                        callback.onFailure(new IOException("Resposta não é um JSON válido"));
                    }
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
