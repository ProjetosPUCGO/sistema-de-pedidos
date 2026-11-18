package entidades;

import exception.RegraDeNegocioException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Produto {
    /*
    Os atributos id, nome e preco foram declarados conforme a documentação.
    */
    private Integer id;
    private String nome;
    private Double preco;

    /*
    A regra do id segue a mesma. Vou utilizar um atributo estático, que será responsável por atribuir
    o valor do próximo id para a variável de instância id.
     */

    private static Integer proxOid = 1;

    /*
    Essa lista chamada categorias foi criada para atender a multiplicidade entre produto e categorias. No caso, um produto tem 1 ou MAIS CATEGORIAS.
     */

    private List<Categoria> categorias = new ArrayList<>();

    /*
    O construtor foi criado recebendo os parâmetros nome, preco e a classe Categoria, pois seguindo a regra de domínio, um produto tem um ou mais produtos,
    então obrigatoriamente é iniciado com uma categoria.
     */

    public Produto(String nome, Double preco, Categoria categoria) {
        setNome(nome);
        setPreco(preco);
        this.id = proxOid++;
        adicionarCategoria(categoria);
    }

    /*
    Os getters e setters foram criados, seguindo a mesma lógica de categoria.
     */

    public Integer getId() {
        return id;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("O nome não pode ser nulo ou em branco!");
        }

        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setPreco(Double preco) {
        if (preco == null || preco <= 0) {
            throw new RegraDeNegocioException("O preço não pode ser nulo ou menor ou igual a zero.");
        }

        this.preco = preco;
    }

    public Double getPreco() {
        return preco;
    }

    /*
    O método adicionarCategoria foi criado recebendo o parâmetro categoria. Fiz uma validação para verificar se categoria está nulo, e se estiver nulo, vai lançar
    uma exceção. Também fiz uma validação para verificar se a lista categorias NÃO CONTÉM o parâmetro de categoria. Se não tiver, ele vai adicionar na lista, e também
    utilizei o próprio método de categorias para fazer a garantia da persistência bidirecional, onde categoria conhece produto, e produto conhece categoria.
     */

    public void adicionarCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new RegraDeNegocioException("A categoria não pode ser nula");
        }

        if (!categorias.contains(categoria)) {
            categorias.add(categoria);
            categoria.adicionarProduto(this);
        }
    }

    public List<Categoria> getCategorias() {
        return Collections.unmodifiableList(categorias);
    }

    public void mostrarCategoria() {
        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }


    /*
    O equals é utilizado para verificar se um produto é idêntico ao outro pelo ID.
     */
    @Override
    public boolean equals(Object o) {
        /*
        Na primeira linha, verifiquei se o objeto comparado é nulo, ou se ele é de uma classe diferente. Se o resultado for verdadeiro, ele retorna falso.
         */
        if (o == null || getClass() != o.getClass()) return false;
        /*
        Nessa linha abaixo, o objeto Object o genérico vai ser convertido através de um casting para acessar o atributo ID.
         */
        Produto produto = (Produto) o;

        /*
        Na última linha, faz a comparação do id do produto atual do id de outro produto de forma segura e evitando NullPointerException.
         */
        return Objects.equals(id, produto.id);
    }

    /*
    Método usado para gerar um código numérico baseado no id.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Produto: \nId: " + id
                + "\n"
                + "Nome: " + nome
                + "\n"
                + "Preço: R$ " + String.format("%.2f", preco)
                + "\n";
    }
}