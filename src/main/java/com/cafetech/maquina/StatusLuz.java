package com.cafetech.maquina;

public class StatusLuz {
    private CorLuz cor;

    public CorLuz atualizarStatus(Sabor sabor) {
        cor = switch (sabor.verificarNivel()) {
            case ALTO -> CorLuz.VERDE;
            case BAIXO -> CorLuz.AMARELO;
            case VAZIO -> CorLuz.VERMELHO;
        };
        return cor;
    }

    public CorLuz getCor() {
        return cor;
    }
}
