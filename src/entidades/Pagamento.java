package entidades;

import entidades.enums.EstadoPagamento;
import exception.RegraDeNegocioException;

/*
A implementação foi feita seguindo a documentação. Nela, estava sendo solicitado a criação de uma classe abstrata chamada Pagamento,
e também uma implementação de uma interface chamada Imprimivel, onde foi estabelecido um contrato de classe em Imprimivel com a classe Pagamento,
e dentro dessa classe está associado o método imprimir().
 */

public abstract class Pagamento implements Imprimivel {
    /*
    Conforme estabelecido na entidade pagamento, ela vai receber dois atributos: id e EstadoPagamento.
    A lógica de implementação do ID segue a mesma: Para que ele tenha um ID único, foi criado um atributo estático que, inicialmente, o valor
    do ID é um atributo estático, e inicialmente começa com 1.
     */
    private Integer id;
    private EstadoPagamento estado;

    private static Integer proxOid = 1;

    /*
    No construtor não foi colocado nenhum atributo, mas o ID vai ser inicializado com o valor de proxOid, e depois vai sendo incrementado o valor.
    O valor de estado vai começar como Pendente, conforme solicitado na documentação, e depois será alterado pelos métodos de alteração.
     */
    public Pagamento() {
        this.id = proxOid++;
        this.estado = EstadoPagamento.PENDENTE;
    }

    /*
    Getters para pegar os valores de id e do estado.
     */
    public Integer getId() {
        return id;
    }

    public EstadoPagamento getEstado() {
        return estado;
    }

    /*
    Esse método foi criado para alterar o estado do pagamento. Como possui três valores:
    PENDENTE - Vai ser inicializado com esse valor;
    QUITADO - Vai ser alterado pelo método alterarEstadoParaQuitado(). Para que nenhum pedido que já ESTEJA CANCELADO seja QUITADO,
    criei essa exceção utilizando o exception RegraDeNegocioException, onde se o valor de if for verdadeiro, vai cair na exceção.
    Se não for verdadeiro, o valor de estado vai ser alterado para quitado, e pode ser consultado pelo método getEstado().
     */

    public void alterarEstadoParaQuitado() {
        if (estado == EstadoPagamento.CANCELADO) {
            throw new RegraDeNegocioException("O estado de pagamento não pode ser alterado, porque ele foi CANCELADO!");
        }
        this.estado = EstadoPagamento.QUITADO;
    }

    /*
    Esse método foi criado para alterar o estado de pagamento para cancelado.
     */

    public void alterarEstadoParaCancelado() {
        this.estado = EstadoPagamento.CANCELADO;
    }

    /*
    Esse método foi solicitado para atender a demanda de "Polimorfismo obrigatório", onde foi solicitado a criação de um método abstrato
    em pagamento, tendo como assinatra calcularValorFinal(double totalDoPedido).
     */

    public abstract double calcularValorFinal(double totalDoPedido);

    /*
    Foi criado o método toString() para retornar o relatório de imprimir.
     */
    @Override
    public String toString() {
        return imprimir();
    }

    /*
    Esse método foi usado para retornar o relatorio da classe.
     */

    @Override
    public String imprimir() {
        return "Id: " +
                id
                + "\n"
                + "Estado: " + estado;
    }
}
