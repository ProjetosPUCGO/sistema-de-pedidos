package entidades;

import exception.RegraDeNegocioException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Categoria {
    /*
    Os atributos id e nome foram declarados conforme a documentação.
     */
    private Integer id;
    private String nome;

    /*
    A regra do id segue a mesma. Vou utilizar um atributo estático, que será responsável por atribuir
    o valor do próximo id para a variável de instância id.
     */
    private static Integer proxOid = 1;

    /*
    A lista recebendo a classe Produto foi feita para atender o requisito da multiplicidade entre Categoria
    e Produtos. No caso, uma categoria tem zero ou vários produtos.
     */

    private List<Produto> produtos = new ArrayList<>();

    /*
    No construtor eu coloquei como parâmetro o String nome, e coloquei uma validação para verificar se nome é nulo
    ou se é uma string formada somente por espaços. Também atribui o id para o proximo id, começando por 1.
    Não coloquei o método adicionarProduto() dentro do construtor, porque uma categoria pode nascer sem produto.
    Portanto, a lógica para adição de um produto relacionado a categoria foi atribuída no método adicionarProduto().
     */

    public Categoria(String nome) {
        setNome(nome);
        this.id = proxOid++;
    }

    /*
    Foram criados os getters e setters dos atributos. No setNome() eu criei uma validação, verificando se é NULO ou se tem ESPAÇOS. Se o valor for verdadeiro, o resultado
    gerará uma exceção, definida pelo exception RegraDeNegocioException.
     */
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("O nome não pode ser nulo ou em branco!");
        }

        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    /*
    Conforme solicitado na documentação, foi criado um get para a lista de produtos, que retornará somente uma view das informações das listas, sem a possibilidade de
    alteração.
     */
    public List<Produto> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }

    /*
    No adicionarProduto(), ele vai receber o parâmetro Produto produto, e vai fazer a verificação se o produto é nulo. Se for nulo, não será adicionado na lista de produtos.
    Também fiz uma outra validação para verificar se produtos NÃO contém o produto colocado como parâmetro. Caso dê valor verdadeiro, o produto será adicionado na lista.
    Uma informação interessante é que eu deixei esse método sem modificador de acesso, porque dentro da regra de negócio que estabeleci, a relação bidirecional será feita
    na classe Produto, e para evitar que adicionarProduto() seja chamado dentro do main, eu tirei o modificador de acesso.
     */

    void adicionarProduto(Produto produto) {
        if (produto == null) {
            throw new RegraDeNegocioException("O produto não pode ser nulo.");
        }

        if (!produtos.contains(produto)) {
            produtos.add(produto);
        }
    }
    /*
    Método criado para mostrar os produtos relacionados as categorias.
     */
    public void mostrarProduto() {
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    /*
    Método toString() responsável por retornar um relatório dos atributos dessa classe.
     */
    @Override
    public String toString() {
        return "Categoria:\nId: " + id
                + "\n"
                + "Nome: " + nome
                + "\n";
    }
}