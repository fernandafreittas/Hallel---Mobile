package com.example.hallelapp.htpp;

import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.payload.requerimento.BuscarIdAssociadoReq;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpAssociado {

    private String token;

    //lorenzo

    private static final String UrlBase = "http://192.168.1.7:8080/api/associado/";

    //fernanda


   // private static final String UrlBase = "http://192.168.100.36:8080/api/associado/";
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




    public void listarPagamentoAssociadoPerfilByMesAndAno (String idAssociado,String mes ,String ano,
                                   InformacoesDaSessao informacoesDaSessao , final HttpMain.HttpCallback callback) {
        OkHttpClient client = new OkHttpClient();

        token = informacoesDaSessao.getToken();


        String url = UrlBase + "perfil/pagamento/" + idAssociado + "?mes=" + mes + "&ano=" + ano;

        System.out.println(url);

        System.out.println(token);

        System.out.println();



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

    public void listarTodosAssociados(InformacoesDaSessao informacoesDaSessao, final HttpCallback callback) {
        OkHttpClient client = new OkHttpClient();

        token = informacoesDaSessao.getToken();

        String url = UrlBase + "listAll";

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