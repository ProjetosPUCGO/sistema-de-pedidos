package entidades;

import exception.RegraDeNegocioException;

/*
Classe de associação entre Pedido e Produto: cada item liga um pedido a um produto
e guarda seus próprios dados (quantidade, preço praticado e desconto).
 */
public class ItemPedido implements Imprimivel {
    private Double desconto;
    private Integer quantidade;
    private Double preco;
    private Produto produto;
    private Pedido pedido;

    ItemPedido(Pedido pedido, Produto produto, Integer quantidade, Double desconto) {
        if (pedido == null) {
            throw new RegraDeNegocioException("O pedido do item não pode ser nulo.");
        }
        if (produto == null) {
            throw new RegraDeNegocioException("O produto do item não pode ser nulo.");
        }
        this.pedido = pedido;
        this.produto = produto;
        this.preco = produto.getPreco();
        setQuantidade(quantidade);
        validarDesconto(desconto);
        this.desconto = desconto;
    }

    /*
    preco não tem setter: é o preço praticado no momento do pedido e não pode mudar.
     */
    public Double getPreco() {
        return preco;
    }

    public Double getDesconto() {
        return desconto;
    }

    private void validarDesconto(Double desconto) {
        if (desconto == null || desconto < 0) {
            throw new RegraDeNegocioException("O desconto não pode ser nulo ou menor que zero.");
        }
        if (desconto > preco) {
            throw new RegraDeNegocioException("O desconto não pode ser maior que o preço unitário do produto.");
        }
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    /*
    a quantidade só é alterada pelo próprio Pedido,
    quando o mesmo produto é adicionado novamente.
     */
    void setQuantidade(Integer quantidade) {
        if (quantidade == null || quantidade < 1) {
            throw new RegraDeNegocioException("A quantidade deve ser maior ou igual a 1.");
        }
        this.quantidade = quantidade;
    }

    public Double getSubtotal() {
        return (preco - desconto) * quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    /*
    O item não imprime o pedido, mostra só os seus próprios dados.
     */
    @Override
    public String imprimir() {
        return produto.getNome()
                + " | Quantidaded: " + quantidade
                + " | Preço: R$ " + String.format("%.2f", preco)
                + " | Desconto: R$ " + String.format("%.2f", desconto)
                + " | Subtotal: R$ " + String.format("%.2f", getSubtotal());
    }

    @Override
    public String toString() {
        return imprimir();
    }
}
