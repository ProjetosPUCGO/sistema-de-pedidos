package aplicacao;

import entidades.Estado;
import entidades.Pagamento;
import entidades.PagamentoComBoleto;
import entidades.PagamentoComCartao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Principal {
    static void main(String[] args) {

        PagamentoComBoleto pagamento1 = new PagamentoComBoleto("22/11/2026 01:00:00");

        pagamento1.alterarEstadoParaQuitado();

        pagamento1.alterarEstadoParaQuitado();

        System.out.println(pagamento1.imprimir());

    }
}