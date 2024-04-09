package com.example.hallelapp.htpp;

import com.example.hallelapp.payload.requerimento.CadastroRequest;
import com.example.hallelapp.payload.requerimento.LoginRequest;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.annotation.SuppressLint;
import android.os.AsyncTask;


public class HttpMain {


    // estou colocando o ip do meu computador pois se eu colocar local host
    // ele irá procurar dentro do celular emulado e não da maquina

    private static final String UrlBase = "http://192.168.1.4:8080/api/";
    private static final String UrlAdm = "administrador/";

    public static final MediaType JSON = MediaType.get("application/json");


    // interface para tratar da conexão da Api casso seja bem sucedida ou dê erro
    public interface HttpCallback {
        void onSuccess(String response);
        void onFailure(IOException e);
    }

    // consumido a rota de cadastro de membro da API

    public void cadastrar(final CadastroRequest cadastroRequest, final HttpCallback callback) {

        //execulta a requisição em outra thread fazendo com que a thead principal fique livre para a interfacae
        new AsyncTask<Void, Void, String>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected String doInBackground(Void... voids) {

                OkHttpClient client = new OkHttpClient();
                Gson gson = new Gson();

                //transformando o objeto em Json
                String json = gson.toJson(cadastroRequest);

                //url base
                String url = UrlBase + "cadastrar";

                //impressões no console para teste
                System.out.println(url);
                System.out.println(cadastroRequest.toString());

                //construindo o corpo da requisão
                RequestBody body = RequestBody.create(json, JSON);

                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }


            // depois da resposta ser execultada
            @Override
            protected void onPostExecute(String result) {
                //se o resultado for indiferente de null ele chama o metodo OnSucess que imprime a resposta do servidor
                // se não chaama onFailure para lançar uma exeção
                if (result != null) {
                    callback.onSuccess(result);
                } else {
                    callback.onFailure(new IOException("Erro ao realizar requisição"));
                }
            }
        }.execute();
    }

    public void login(final LoginRequest loginRequest ,  final HttpCallback callback){
        new AsyncTask<Void,Void,String>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected String doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                Gson gson = new Gson();

                String json = gson.toJson(loginRequest);

                String url = UrlBase + "login";

                System.out.println(url);
                System.out.println(loginRequest.toString());

                RequestBody body = RequestBody.create(json, JSON);

                Request request = new Request
                        .Builder()
                        .url(url)
                        .post(body)
                        .build();


                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        // Retorna o conteúdo da resposta como uma string
                        return response.body().string();
                    } else {
                        // Retorna null se a resposta não for bem-sucedida
                        return null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // Retorna null em caso de exceção
                    return null;
                }
            }


            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    System.out.println(result);
                    callback.onSuccess(result);
                } else {
                    callback.onFailure(new IOException("erro ao realizar requisição"));
                }
            }
        }.execute();


    }







/*
    public void whenGetRequestWithQueryParameter_thenCorrect()
            throws IOException {

        OkHttpClient client = new OkHttpClient();

        String url = UrlBase + "login";
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("id", "1");


        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();



    }
*/



}
