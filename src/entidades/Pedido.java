package entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import exception.RegraDeNegocioException;

public class Pedido implements Imprimivel {
    // Identificador único do pedido.
    private Integer id;
    // Momento em que o pedido foi criado.
    private LocalDateTime instante;

    // Lista de itens do pedido; cada pedido deve possuir pelo menos um item.
    private List <ItemPedido> itens = new ArrayList<>();

    // Dados associados ao pedido.
    private Cliente cliente;
    private Endereco enderecoDeEntrega;
    private Pagamento pagamento;

    // Contador usado para gerar o próximo identificador do pedido.
    private static Integer proxOid = 1;
    // Formato usado ao converter a data e hora em texto.
    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Inicializa o pedido e adiciona o primeiro item.
    public Pedido (ItemPedido itemPedido, Cliente cliente, Endereco enderecoDeEntrega, Pagamento pagamento) {
        this.id = proxOid ++;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
        this.pagamento = pagamento;
        this.instante = LocalDateTime.now();
        adicionarItens(itemPedido);
    }

    // Retorna o identificador do pedido.
    public Integer getId() {
        return id;
    }

    // Retorna a data e a hora de criação já formatadas.
    public String getInstante() {
        return instante.format(fmt);
    }

    // Retorna os itens em uma coleção somente para leitura.
    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    // Converte todos os itens do pedido em uma única representação de texto.
    public String mostrarItensPedido() {
        StringBuilder sb = new StringBuilder();

        // Adiciona a descrição de cada item separada por vírgulas.
        for (ItemPedido listaItemPedido : itens) {
            sb.append(listaItemPedido.toString()).append(", ");
        }

        // Remove os dois caracteres finais usados após o último item.
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }

    // Retorna o cliente associado ao pedido.
    public Cliente getCliente() {
        return cliente;
    }

    // Retorna o endereço escolhido para entrega.
    public Endereco getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    // Retorna o pagamento associado ao pedido.
    public Pagamento getPagamento() {
        return pagamento;
    }

    // Retorna o próximo identificador disponível para um pedido.
    public static Integer getProxOid() {
        return proxOid;
    }

    // Valida e adiciona um item ao pedido, agrupando produtos repetidos.
    public void adicionarItens(ItemPedido itemPedido) {
        // Impede a inclusão de um item nulo.
        if (itemPedido == null)
            throw new RegraDeNegocioException("O item do pedido não pode estar nulo.");

        // Garante que o item pertença a este pedido.
        if (itemPedido.getPedido() != this)
            throw new RegraDeNegocioException("O item do pedido não pertente ao pedido.");

        // Verifica se o produto já existe na lista de itens.
        for (ItemPedido difItemPedido : itens) {
            if (difItemPedido.getProduto().equals(itemPedido.getProduto())) {
                // Soma as quantidades dos itens do mesmo produto.
                itemPedido.setQuantidade(difItemPedido.getQuantidade() + itemPedido.getQuantidade());
                return;
            }
        }

        // Adiciona o item somente quando o produto ainda não existe no pedido.
        itens.add(itemPedido);
    }

    // Soma o valor de cada item após a aplicação do desconto.
    public Double calcularSubtotalPedido() {
        Double subtotal = 0.0;
        for (ItemPedido listaItemPedido : itens)
            subtotal += (listaItemPedido.getPreco() - listaItemPedido.getDesconto()) * listaItemPedido.getQuantidade();

        return subtotal;
    }

    // Calcula o valor final do pedido.
    public Double calcularTotalPedido() {
        Double total = calcularSubtotalPedido();
        return total;
    }

    // Monta um relatório completo com os dados e valores do pedido.
    @Override
    public String imprimir() {
        return "Pedido: " + id
               + "\n"
               + "Data de Criação: " + getInstante()
               + "\n"
               + "Cliente: " + cliente
               + "\n"
               + "Endereço de Entrega: " + enderecoDeEntrega
               + "\n"
               + "Subtotal do Pedido: R$" + String.format("%.2f", calcularSubtotalPedido())
               + "\n"
               + "Total do Pedido: R$" + String.format("%.2f", calcularTotalPedido())
               + "\n"
               + "Método de Pagamento: " + pagamento
               + "\n"
               + "Itens do Pedido: " + mostrarItensPedido();
    }

    @Override
    public String toString() {
        return imprimir();
    }

}
