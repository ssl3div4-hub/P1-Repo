package com.cafetech.maquina;

public class Pedido {
    private final Sabor sabor;
    private StatusPedido status;

    public Pedido(Sabor sabor) {
        this.sabor = sabor;
        this.status = StatusPedido.PENDENTE;
    }

    public Sabor getSabor() {
        return sabor;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void marcarComoPago() {
        this.status = StatusPedido.PAGO;
    }

    public void cancelar() {
        this.status = StatusPedido.CANCELADO;
    }
}
