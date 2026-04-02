package com.cafetech.maquina;

public class PagamentoPix implements PagamentoStrategy {
    @Override
    public TipoPagamento getTipo() {
        return TipoPagamento.PIX;
    }

    @Override
    public boolean validarPagamento(double valor, String token) {
        return valor > 0 && token != null && token.startsWith("PIX-");
    }

    @Override
    public boolean processarPagamento(double valor, String token) {
        return validarPagamento(valor, token);
    }

    @Override
    public void cancelarPagamento(double valor) {
        // Em um sistema real, chamaria estorno/reversão.
    }
}
