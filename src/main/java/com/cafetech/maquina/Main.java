package com.cafetech.maquina;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Sabor> sabores = List.of(
                new Sabor(1, "Espresso", 30),
                new Sabor(2, "Cappuccino", 30),
                new Sabor(3, "Latte", 30),
                new Sabor(4, "Mocha", 4),   // baixo (amarelo)
                new Sabor(5, "Macchiato", 2), // baixo (amarelo)
                new Sabor(6, "Chocolate", 0)  // esgotado (vermelho)
        );

        MaquinaDeCafe maquina = new MaquinaDeCafe(
                sabores,
                new SistemaPagamento(),
                new StatusLuz(),
                new Notificacao()
        );

        maquina.getSabores().forEach(s -> {
            CorLuz cor = maquina.atualizarStatusLuz(s);
            System.out.printf("Sabor: %-10s | Quantidade: %2d | Luz: %s%n", s.getNome(), s.getQuantidade(), cor);
        });

        System.out.println("\n--- Simulações de compra ---");
        maquina.processarPedido(1, TipoPagamento.PIX, 7.50, "PIX-CHAVE-CLIENTE");
        maquina.processarPedido(6, TipoPagamento.CARTAO, 8.00, "1234567812345678"); // sem estoque
        maquina.processarPedido(2, TipoPagamento.CARTAO, 9.00, "0000"); // cartão inválido
    }
}
