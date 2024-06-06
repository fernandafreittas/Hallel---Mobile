package com.example.hallelapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PagamentosAssociado extends EntradasFinanceiro implements Serializable {

    private List<Associado> para;

    private Date dataPaga;

    private String idAssociadoPagador;
}
