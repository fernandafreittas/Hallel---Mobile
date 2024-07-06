package com.example.hallelapp.htpp;

import android.util.Log;

import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.payload.requerimento.AdministradorLoginRequest;
import com.example.hallelapp.payload.requerimento.BuscarIdAssociadoReq;
import com.example.hallelapp.payload.requerimento.EventosRequest;
import com.example.hallelapp.payload.requerimento.LoginRequest;
import com.example.hallelapp.payload.requerimento.ParticiparEventosRequest;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpAdm {

    private String token;

    //lorenzo
    private static final String UrlBase = "http://192.168.1.4:8080/api/administrador";

    private static final String UrlBaseMembro = "http://192.168.1.4:8080/api/";

    private static final String UrlBaseAssociado = "http://192.168.1.4:8080/api/associado/";


    //fernanda
  //  private static final String UrlBase = "http://192.168.100.36:8080/api/administrador";

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


    public void criarEvento(String data,final EventosRequest eventosRequest, AuthenticationResponse authenticationResponse, final HttpAdm.HttpCallback callback
    ) {


        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(eventosRequest);
        String url = UrlBase + "/eventos/"+data+"/create";

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

    public void CreateLocaisEventos( AuthenticationResponse authenticationResponse, final HttpAdm.HttpCallback callback
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


    public void ListMembros( AuthenticationResponse authenticationResponse, final HttpAdm.HttpCallback callback
    ) {


        OkHttpClient client = new OkHttpClient();

        String url = UrlBase + "/membros";

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

    public void ListAssociados( AuthenticationResponse authenticationResponse, final HttpAdm.HttpCallback callback
    ) {


        OkHttpClient client = new OkHttpClient();

        String url = UrlBase + "/associado/listAll";

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

    public void ListVoluntarios(String idEventos, AuthenticationResponse authenticationResponse, final HttpAdm.HttpCallback callback
    ) {


        OkHttpClient client = new OkHttpClient();

        String url = UrlBase + "/eventos/"+idEventos+"/listVoluntarios";

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




    public void listarPagamentoAssociadoPerfilByMesAndAnoADM (String idAssociado,String mes ,String ano,
                                                           AuthenticationResponse authenticationResponse , final HttpMain.HttpCallback callback) {
        OkHttpClient client = new OkHttpClient();

        token = authenticationResponse.getToken();


        String url = UrlBaseAssociado + "perfil/pagamento/" + idAssociado + "?mes=" + mes + "&ano=" + ano;

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


    public void BuscarIdAssociadoADM (BuscarIdAssociadoReq buscarIdAssociadoReq,
                                   AuthenticationResponse authenticationResponse , final HttpMain.HttpCallback callback) {
        OkHttpClient client = new OkHttpClient();

        token = authenticationResponse.getToken();

        Gson gson = new Gson();
        String json = gson.toJson(buscarIdAssociadoReq);

        String url = UrlBaseMembro + "membros/buscarAssociadoEmail";

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



    public void ListEventosArquivados(AuthenticationResponse authenticationResponse,final HttpMain.HttpCallback callback) {




        OkHttpClient client = new OkHttpClient.Builder().build();


        token = authenticationResponse.getToken();

        String url = UrlBase + "/eventos/arquivados";

        System.out.println(url);

        System.out.println(token);

        System.out.println();



        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();

                    // Informe o tipo genérico para converter a lista corretamente
                    Type listType = new TypeToken<List<AllEventosListResponse>>() {}.getType();
                    List<AllEventosListResponse> responseEventos = new Gson().fromJson(responseBody, listType);

                    callback.onSuccess(responseBody);
                } else {
                    callback.onFailure(new IOException("Erro ao realizar requisição"));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }
        });
    }


    public void DesarquivaEvento(String idEvento,AuthenticationResponse authenticationResponse,final HttpMain.HttpCallback callback) {




        OkHttpClient client = new OkHttpClient.Builder().build();


        token = authenticationResponse.getToken();

        String url = UrlBase + "/eventos/"+idEvento+"/desarquivar";

        System.out.println(url);

        System.out.println(token);

        System.out.println();



        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
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


    public void DeletarEvento(String idEvento,AuthenticationResponse authenticationResponse,final HttpMain.HttpCallback callback) {




        OkHttpClient client = new OkHttpClient.Builder().build();


        token = authenticationResponse.getToken();

        String url = UrlBase + "/eventos/"+idEvento+"/delete";

        System.out.println(url);

        System.out.println(token);

        System.out.println();



        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
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
