package com.example.hallelapp.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hallelapp.R;
import com.example.hallelapp.model.InformacoesDaSessao;

public class ObterInformacoesDaSecao {

    private Context mContext;

    public ObterInformacoesDaSecao(Context context) {
        mContext = context;
    }

    private String obterIdDeLogin() {
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString("id", ""); // Retorna a string de login, ou uma string vazia se não encontrada
    }

    private String obterTokenDeLogin() {
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString("token", ""); // Retorna a segunda o token , ou uma string vazia se não encontrada
    }

    private boolean obterLembreteDeLogin() {
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        return sharedPref.getBoolean("lembreDeMin", false); // Retorna o valor booleano do lembrete, ou false se não encontrado
    }

    private String obterInformacao1() throws Exception {
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String informacao1Criptografada = sharedPref.getString("informacao1", "");
        return AESExample.descriptografar(informacao1Criptografada);
    }

    private String obterInformacao2() throws Exception {
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String informacao2Criptografada = sharedPref.getString("informacao2", "");
        return AESExample.descriptografar(informacao2Criptografada);
    }





    public InformacoesDaSessao obterDadosSalvos() throws Exception {
        InformacoesDaSessao informacoes = new InformacoesDaSessao();
        informacoes.setId(obterIdDeLogin());
        informacoes.setToken(obterTokenDeLogin());
        informacoes.setLembreDeMin(obterLembreteDeLogin());
        informacoes.setInformacao1(obterInformacao1());
        informacoes.setInformacao2(obterInformacao2());

        return informacoes;
    }

}
