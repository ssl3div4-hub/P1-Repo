package com.cafetech.maquina;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaquinaDeCafe {
    private final List<Sabor> sabores;
    private final SistemaPagamento sistemaPagamento;
    private final StatusLuz statusLuz;
    private final Notificacao notificacao;

    public MaquinaDeCafe(List<Sabor> sabores, SistemaPagamento sistemaPagamento, StatusLuz statusLuz, Notificacao notificacao) {
        if (sabores == null || sabores.size() != 6) {
            throw new IllegalArgumentException("A máquina deve possuir exatamente 6 sabores.");
        }
        this.sabores = new ArrayList<>(sabores);
        this.sistemaPagamento = sistemaPagamento;
        this.statusLuz = statusLuz;
        this.notificacao = notificacao;
    }

    public Optional<Sabor> selecionarSabor(int idSabor) {
        return sabores.stream().filter(s -> s.getId() == idSabor).findFirst();
    }

    public boolean verificarDisponibilidade(Sabor sabor) {
        boolean disponivel = sabor.disponivel();
        if (!disponivel) {
            notificacao.exibirMensagem("Sabor indisponível: " + sabor.getNome());
        }
        return disponivel;
    }

    public boolean processarPagamento(TipoPagamento tipoPagamento, double valor, String tokenPagamento) {
        boolean pagamentoAprovado = sistemaPagamento.processarPagamento(tipoPagamento, valor, tokenPagamento);
        if (!pagamentoAprovado) {
            notificacao.exibirMensagem("Pagamento recusado via " + tipoPagamento + ".");
            sistemaPagamento.cancelarPagamento();
        }
        return pagamentoAprovado;
    }

    public boolean processarPedido(int idSabor, TipoPagamento tipoPagamento, double valor, String tokenPagamento) {
        Optional<Sabor> saborOpt = selecionarSabor(idSabor);
        if (saborOpt.isEmpty()) {
            notificacao.exibirMensagem("Sabor não encontrado.");
            return false;
        }

        Sabor sabor = saborOpt.get();
        Pedido pedido = new Pedido(sabor);

        if (!verificarDisponibilidade(sabor)) {
            pedido.cancelar();
            return false;
        }

        if (!processarPagamento(tipoPagamento, valor, tokenPagamento)) {
            pedido.cancelar();
            return false;
        }

        pedido.marcarComoPago();
        liberarCafe(sabor);
        return true;
    }

    public void liberarCafe(Sabor sabor) {
        boolean sucesso = sabor.diminuirDose();
        if (!sucesso) {
            notificacao.exibirMensagem("Produto esgotado no momento da liberação.");
            return;
        }
        CorLuz cor = atualizarStatusLuz(sabor);
        notificacao.exibirMensagem("Café liberado: " + sabor.getNome() + " | Luz: " + cor);
    }

    public CorLuz atualizarStatusLuz(Sabor sabor) {
        CorLuz cor = statusLuz.atualizarStatus(sabor);
        if (cor == CorLuz.AMARELO) {
            notificacao.exibirMensagem("Nível baixo para o sabor: " + sabor.getNome());
        } else if (cor == CorLuz.VERMELHO) {
            notificacao.exibirMensagem("Sabor esgotado: " + sabor.getNome());
        }
        return cor;
    }

    public List<Sabor> getSabores() {
        return List.copyOf(sabores);
    }
}
