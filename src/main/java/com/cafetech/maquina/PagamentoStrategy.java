package com.cafetech.maquina;

public interface PagamentoStrategy {
    TipoPagamento getTipo();

    boolean validarPagamento(double valor, String token);

    boolean processarPagamento(double valor, String token);

    void cancelarPagamento(double valor);
}
