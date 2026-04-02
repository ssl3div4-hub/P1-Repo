package com.cafetech.maquina;

public class PagamentoCartao implements PagamentoStrategy {
    @Override
    public TipoPagamento getTipo() {
        return TipoPagamento.CARTAO;
    }

    @Override
    public boolean validarPagamento(double valor, String token) {
        return valor > 0 && token != null && token.matches("\\d{16}");
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
