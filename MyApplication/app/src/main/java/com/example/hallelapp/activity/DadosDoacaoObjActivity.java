package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.model.DoacaoObjetosEventos;
import com.example.hallelapp.payload.requerimento.AlteraRecebimentoRequest;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.payload.resposta.DoacaoObjetosEventosResponse;

import java.io.IOException;

public class DadosDoacaoObjActivity extends AppCompatActivity {



    private TextView inputNomeDoador;
    private TextView inputNumeroCelular;
    private TextView inputSexoDoador;
    private TextView inputEmailDoador;
    private TextView inputItemDoado;
    private Switch switchRecebido;

    private AlertDialog loadingDialog;

    DoacaoObjetosEventos doacaoObjetosEventos;
    AlteraRecebimentoRequest alteraRecebimentoRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dados_doacao_obj);

        DoacaoObjetosEventosResponse doacaoObjeto = (DoacaoObjetosEventosResponse) getIntent().getSerializableExtra("doacaoObjeto");
        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");



        System.out.println(doacaoObjeto.toString());

        doacaoObjetosEventos = new DoacaoObjetosEventos();
        alteraRecebimentoRequest = new AlteraRecebimentoRequest();



        doacaoObjetosEventos.setId(doacaoObjeto.getId());
        doacaoObjetosEventos.setNomeDoObjeto(doacaoObjeto.getNomeDoObjeto());


        System.out.println(doacaoObjetosEventos.toString());

        HttpAdm requisicao = new HttpAdm();

        inputNomeDoador = findViewById(R.id.inputNomeDoador);
        inputNumeroCelular = findViewById(R.id.inputNumeroCelular);
        inputSexoDoador = findViewById(R.id.inputSexoDoador);
        inputEmailDoador = findViewById(R.id.inputEmailDoador);
        inputItemDoado = findViewById(R.id.inputItemDoado);
        switchRecebido = findViewById(R.id.switch1);


        inputNomeDoador.setText(doacaoObjeto.getNomeDoador());
        inputEmailDoador.setText(doacaoObjeto.getEmailDoador());
        inputItemDoado.setText(doacaoObjeto.getNomeDoObjeto());
        switchRecebido.setChecked(doacaoObjeto.isRecebido());

        switchRecebido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                showLoadingDialog();

                doacaoObjetosEventos.setRecebido(isChecked);

                System.out.println("oi"+doacaoObjetosEventos.isRecebido());
                System.out.println("oi 2" +isChecked);


                alteraRecebimentoRequest.setDoacaoObjetosEventos(doacaoObjetosEventos);
                alteraRecebimentoRequest.setRecebido(isChecked);


                requisicao.AlteraRecebimentoObjeto(alteraRecebimentoRequest, evento.getId(), authenticationResponse, new HttpMain.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {


                        hideLoadingDialog();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                             Toast.makeText(DadosDoacaoObjActivity.this, "Alteração feita com sucesso", Toast.LENGTH_LONG).show();
                            }
                        });


                    }

                    @Override
                    public void onFailure(IOException e) {


                        hideLoadingDialog();

                    }
                });



            }
        });

    }

    private void showLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.loading_screen, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        loadingDialog = builder.create();
        loadingDialog.show();
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


}
