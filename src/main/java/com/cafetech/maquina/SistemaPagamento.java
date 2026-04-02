package com.cafetech.maquina;

public class SistemaPagamento {
    private TipoPagamento tipoPagamento;

    public boolean validarPagamento(TipoPagamento tipo, double valor, String token) {
        this.tipoPagamento = tipo;
        if (tipo == TipoPagamento.PIX) {
            return valor > 0 && token != null && token.startsWith("PIX-");
        }
        if (tipo == TipoPagamento.CARTAO) {
            return valor > 0 && token != null && token.matches("\\d{16}");
        }
        return false;
    }

    public boolean processarPagamento(TipoPagamento tipo, double valor, String token) {
        this.tipoPagamento = tipo;
        return validarPagamento(tipo, valor, token);
    }

    public void cancelarPagamento() {
        // Em um sistema real, executaria rotina de cancelamento/estorno.
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }
}
