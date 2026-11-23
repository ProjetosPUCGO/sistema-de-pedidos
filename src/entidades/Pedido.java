package entidades;

import exception.RegraDeNegocioException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido implements Imprimivel {
    // Identificador único do pedido.
    private Integer id;
    // Momento em que o pedido foi criado (sem setter).
    private LocalDateTime instante;

    // Associações com os nomes do diagrama.
    private final List<ItemPedido> itens = new ArrayList<>();
    private Cliente cliente;
    private Endereco enderecoDeEntrega;
    private Pagamento pagamento;

    // Contador usado para gerar o próximo identificador do pedido.
    private static Integer proxOid = 1;
    // Formato usado ao converter a data e hora em texto.
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /*
    O construtor recebe só os dados obrigatórios que já existem: cliente e endereço de entrega.
    Os itens entram depois, por adicionarItem(), e o pagamento por setPagamento().
    O endereço precisa ser um dos endereços do cliente do pedido.
     */
    public Pedido(Cliente cliente, Endereco enderecoDeEntrega) {
        if (cliente == null) {
            throw new RegraDeNegocioException("O cliente do pedido não pode ser nulo.");
        }
        if (enderecoDeEntrega == null) {
            throw new RegraDeNegocioException("O endereço de entrega não pode ser nulo.");
        }
        if (!cliente.equals(enderecoDeEntrega.getCliente())) {
            throw new RegraDeNegocioException("O endereço de entrega não pertence ao cliente do pedido.");
        }
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
        this.instante = LocalDateTime.now();
        this.id = proxOid++;
        // Mantém a associação bidirecional: o cliente passa a conhecer este pedido.
        cliente.adicionarPedido(this);
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Endereco getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    /*
    O pagamento é criado já apontando para o pedido (new PagamentoComCartao(pedido, 6))
    e depois associado aqui. A verificação garante que os dois lados fiquem consistentes.
     */
    public void setPagamento(Pagamento pagamento) {
        if (pagamento == null) {
            throw new RegraDeNegocioException("O pagamento não pode ser nulo.");
        }
        if (pagamento.getPedido() != this) {
            throw new RegraDeNegocioException("O pagamento não pertence a este pedido.");
        }
        this.pagamento = pagamento;
    }

    // Retorna os itens em uma lista somente para leitura.
    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    /*
    Cria o item dentro do pedido (passando "this"). Se o produto já estiver no pedido,
    soma a quantidade no item que já existe, em vez de criar outro.
     */
    public void adicionarItem(Produto produto, Integer quantidade, Double desconto) {
        if (produto == null) {
            throw new RegraDeNegocioException("O produto não pode ser nulo.");
        }
        if (quantidade == null || quantidade < 1) {
            throw new RegraDeNegocioException("A quantidade deve ser maior ou igual a 1.");
        }
        for (ItemPedido item : itens) {
            if (item.getProduto().equals(produto)) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }
        itens.add(new ItemPedido(this, produto, quantidade, desconto));
    }

    // Soma dos subtotais dos itens.
    public Double getTotal() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    /*
    O resumo é montado a partir de imprimir() de cada parte, usando Imprimivel como tipo,
    para que Pedido não conheça a formatação das outras classes.
     */
    @Override
    public String imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido nº ").append(id).append("\n");
        sb.append("Data: ").append(instante.format(fmt)).append("\n");
        sb.append("Cliente: ").append(cliente.getNome()).append("\n");
        sb.append("Itens:\n");
        for (Imprimivel item : itens) {
            sb.append("  ").append(item.imprimir()).append("\n");
        }
        sb.append("Total: R$ ").append(String.format("%.2f", getTotal())).append("\n");
        Imprimivel pag = pagamento;
        sb.append(pag == null ? "Pagamento: não definido" : pag.imprimir()).append("\n");
        sb.append("Entrega: ").append(enderecoDeEntrega);
        return sb.toString();
    }

    @Override
    public String toString() {
        return imprimir();
    }
}
