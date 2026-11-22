package entidades;

import exception.RegraDeNegocioException;

public class ItemPedido {
    /*
    Declara os atributos de estado (desconto, quantidade, preco) e as referências de associação (produto e pedido), conforme especificado no diagrama de classes.
    */
    private Double desconto;
    private Integer quantidade = 1;
    private Double preco;
    private Produto produto;
//    private Pedido pedido;

    /*
    Construtor de ItemPedido que inicializa os atributos desconto, quantidade, preco, produto e pedido. Exige as instâncias de Produto e Pedido para refletir a classe
    associativa definida no diagrama de classes.
     */
    public ItemPedido(Double desconto, Integer quantidade, Double preco, Produto produto) {// Pedido pedido) {
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.produto = produto;
        // this.pedido = pedido;
    }

    /*
    Calcula e atribui o preço do ItemPedido o valor unitário do Produto, conforme regra de negócio.
     */
    public void setPreco(Produto produto) {
        this.preco = produto.getPreco();
    }

    public Double getPreco() {
        return preco;
    }

    /*
    O método atualizarDesconto() calcula o desconto progressivo do ItemPedido, concedendo 10% a cada 10 unidades adicionadas, até o limite máximo de 50%.
    */
    private void atualizarDesconto() {
        int quantitativoDecimal = quantidade / 10;
        double percentual = quantitativoDecimal * 0.10;

        if (percentual > 0.5)
            percentual = 0.5;

        if (desconto < 0)
            throw new RegraDeNegocioException("Desconto não pode ser menor que zero.");

        if (desconto > preco)
            throw new RegraDeNegocioException("Desconto não pode ser maior que valor unitário do produto.");

    this.desconto = percentual;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    @Override
    public String toString() {
        return "Pedido: " + pedido
               + "\n"
               + "Item pedido: \n"
               + "Produto: " + produto
               + "\n"
               + "Preço: R$" + preco.toString()
               + "\n"
               + "Quantidade: " + quantidade.toString()
               + "\n"
               + "Desconto: R$" + desconto.toString();
    }

}
