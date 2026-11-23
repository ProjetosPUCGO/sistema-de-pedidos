package entidades;

import exception.RegraDeNegocioException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
Conforme solicitado na documentação, o PagamentoComBoleto é uma subclasse de Pagamento.
 */
public class PagamentoComBoleto extends Pagamento{

    /*
    Na documentação, foram solicitados dois atributos: dataVencimento e dataPagamento, e também foi solicitado o tipo de variável Date.
    No entanto, após uma observação e definição, achei válido colocar a variável de instância como LocalDateTime, pois a dataPagamento será definida
    quando o PAGAMENTO for quitado, e nas informações serão apresentados: dia/mes/ano hora:minuto:segundo. Para implementar essa validação,
    coloquei o LocalDateTime que comporta data e hora.
     */
    private LocalDateTime dataVencimento;
    private LocalDateTime dataPagamento;

    /*
    Coloquei o DateTimeFormatter para formatar como será tratado a informação do localdate.
     */

    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /*
    No construtor, vai ser solicitado a dataVencimento. No entanto, como é do tipo LocalDateTime, o formato é: yyyy/MM/dd. Para estar no formato
    em que o utilizador brasileiro está acostumado (dd/MM/yyyy), eu coloquei como parâmetro uma ‘String’ chamada dataVencimentoStr.
    Nele, utilizei o método .parse() para que seja feito a interpretação de (String > data, formato), estabelecido na variável estática fmt.
     */

    public PagamentoComBoleto(Pedido pedido, String dataVencimentoStr) {
        super(pedido);
        if (dataVencimentoStr == null) {
            throw new RegraDeNegocioException("A data de vencimento não pode ser nula.");
        }
        this.dataVencimento = LocalDateTime.parse(dataVencimentoStr, fmt);
        this.dataPagamento = null;
    }

    /*
    Posteriormente, sobrescrevi o método alterarEstadoParaQuitado(), especificado em Pagamento, e atribui uma especificação de PagamentoComBoleto.
    Nessa especificação, o dataPagamento será a data no ATO em que a quitação foi feita, ou seja, no momento em que alterarEstadoParaQuitado() foi
    chamado.
     */
    @Override
    public void alterarEstadoParaQuitado() {
        if (dataPagamento != null) {
            throw new RegraDeNegocioException("O pagamento já foi realizado na data: " + dataPagamento.format(fmt));
        }

        super.alterarEstadoParaQuitado();

        LocalDateTime now = LocalDateTime.now();
        this.dataPagamento = now;
    }

    /*
    Esse getter eu coloquei como string somente para que seja retornado o formato da data de vencimento no esquema de fmt.
     */
    public String getDataVencimento() {
        return dataVencimento.format(fmt);
    }

    /*
    Criei esse outro getter para que primeiro verificasse se o valor está nulo, e retornasse a informação de acordo com o que está escrito
    para que depois possa ser reaproveitado no método imprimir(). Caso não esteja nulo, vai retornar a data de pagamento no formato definido
    na variável estática fmt.
     */
    public String getDataPagamento() {
        if (dataPagamento == null) {
            return "O pagamento ainda não foi feito.";
        } else {
            return dataPagamento.format(fmt);
        }
    }

    /*
    O cálculo do valorFinal é: totalDoPedido - (totalDoPedido * 0.05), ou seja, 5% no valor total.
     */
    @Override
    public double calcularValorFinal(double totalDoPedido) {
        return totalDoPedido - (totalDoPedido * 0.05);
    }

    @Override
    public String toString() {
        return imprimir();
    }

    @Override
    public String imprimir() {
        return "Pagamento com boleto: \n"
                + super.imprimir()
                + "\n"
                + "Data de vencimento: " + getDataVencimento()
                + "\n"
                + "Data de pagamento: " + getDataPagamento();
    }
}