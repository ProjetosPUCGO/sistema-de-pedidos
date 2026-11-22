package entidades;

import exception.RegraDeNegocioException;

/*
Conforme solicitado na documentação, a classe PagamentoComCartao é subclasse de Pagamento.
 */
public class PagamentoComCartao extends Pagamento {

    /*
    O atributo numeroDeParcelas foi definido, conforme solicitado na documentação.
     */

    private Integer numeroDeParcelas;

    /*
    Foi criado um construtor, onde foi reaproveitado os atributos inicializados dentro dele, e também foi colocado um parâmetro, que vai
    ser utilizado dentro do numeroDeParcelas.
     */

    public PagamentoComCartao(Integer numeroDeParcelas) {
        super();
        setNumeroDeParcelas(numeroDeParcelas);
    }

    /*
    Foi criado um setNumeroParcelas, para poder ser setado, e também criado uma validação, onde o numeroDeParcelas não pode ser nulo
    e tem que estar entre 1 e 12 meses.
     */

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        if (numeroDeParcelas == null) {
            throw new RegraDeNegocioException("O número de parcelas não pode ser nulo!");
        }

        if (numeroDeParcelas < 1 || numeroDeParcelas > 12) {
            throw new RegraDeNegocioException("A quantidade de parcelas precisa ser entre 1 até 12 meses!");
        }
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    /*
    Foi sobrescrito o método abstrato calcularValorFinal() de pagamentos, e foi aplicado a regra de como vai funcionar o pagamento
    com cartão.
     */
    @Override
    public double calcularValorFinal(double totalDoPedido) {
        if (numeroDeParcelas >= 1 && numeroDeParcelas <= 3) {
            return totalDoPedido;
        } else {
            double jurosSimples = 0;
            jurosSimples = totalDoPedido * 0.02 * numeroDeParcelas;

            return jurosSimples + totalDoPedido;
        }
    }

    @Override
    public String toString() {
        return imprimir();
    }

    @Override
    public String imprimir() {
        return "Pagamento com cartão:\n"
                + super.imprimir()
                + "\n" +
                "Número de parcelas: " + numeroDeParcelas;
    }
}
