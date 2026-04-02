package com.cafetech.maquina;

public class Sabor {
    public static final int CAPACIDADE_MAXIMA = 30;
    public static final int LIMITE_BAIXO = 5;

    private final int id;
    private final String nome;
    private int quantidade;
    private NivelEstoque nivel;

    public Sabor(int id, String nome, int quantidadeInicial) {
        if (quantidadeInicial < 0 || quantidadeInicial > CAPACIDADE_MAXIMA) {
            throw new IllegalArgumentException("Quantidade inicial inválida para o sabor " + nome);
        }
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidadeInicial;
        this.nivel = calcularNivel();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public boolean diminuirDose() {
        if (quantidade <= 0) {
            return false;
        }
        quantidade--;
        nivel = calcularNivel();
        return true;
    }

    public NivelEstoque verificarNivel() {
        nivel = calcularNivel();
        return nivel;
    }

    public boolean disponivel() {
        return quantidade > 0;
    }

    private NivelEstoque calcularNivel() {
        if (quantidade == 0) {
            return NivelEstoque.VAZIO;
        }
        if (quantidade <= LIMITE_BAIXO) {
            return NivelEstoque.BAIXO;
        }
        return NivelEstoque.ALTO;
    }
}
