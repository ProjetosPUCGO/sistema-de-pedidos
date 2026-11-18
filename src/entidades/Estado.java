package entidades;

import exception.RegraDeNegocioException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Estado {
    /*
    Os atributos id e nome foram declarados, conforme a especificação na
    documentação.
     */
    private Integer id;
    private String nome;

    /*
    Esse atributo estático chamado proxOid é responsável por atribuir
    os id's únicos para cada Estado. O primeiro Estado começa com o ID 1,
    e depois vai sendo incrementado cada ID para cada Estado.
     */

    private static int proxOid = 1;

    /*
    No atributo responsável por guardar a lista de suas cidades,
    responsabilizei a lista como final, porque a referência da lista
    não pode ser alterada, mas o conteúdo dela pode ser modificado. Resumidamente,
    não pode ser instanciado um novo objeto da lista nessa mesma variável.
     */

    private final List<Cidade> cidades;

    /*
    No construtor, coloquei como parâmetro o nome do Estado,
    e o atributo id vai receber o valor inicial de proxOid = 1,
    e depois vai sendo incrementado cada valor a cada instanciação feita.

     Também inicializei a lista no construtor, para evitar o erro
     "NullPointerException", pois caso essa inicialização fosse feita fora
     do escopo do construtor, a lista inicializaria vazia. Com ela sendo inicializada
     dentro do Construtor, essa lista não vai ser inicializada vazia.
     */
    public Estado(String nome) {
        setNome(nome);
        this.id = proxOid++;
        this.cidades = new ArrayList<>();
    }

    /*
    Coloquei os getters e setters específicos, e evitei colocar o setId,
    porque o Id não pode ser alterado.
     */

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("O nome não pode ser nulo ou em branco!");
        }

        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Integer getId() {
        return id;
    }

    /*
    Na regra de negócio, as cidades são criadas por meio do estado.
    Para que essa regra seja adotada, criei um método chamado adicionarCidade,
    que vai receber um parâmetro chamado nome, e nele vai ser responsável por inicializar
    o nome da cidade.

    Dentro desse método eu criei um objeto Cidade chamando o construtor dela, onde inicialmente vai receber
    o nome da cidade, e o "this" vai ser responsável por mostrar que a cidade PERTENCE ao ESTADO,
    garantindo a regra de negócio estabelecida.
    Posteriormente, a lista de cidades foi usada para adicionar o nome da cidade.
     */
    public void adicionarCidade(String nome) {
        Cidade cidade = new Cidade(nome, this);
        cidades.add(cidade);
    }

    /*
    Esse método foi utilizado para validar a necessidade da criação
    de um get que devolve uma lista não modificável. Para atender essa necessidade,
    eu implementei essa view que vai somente retornar a leitura de uma lista especifica,
    e também vai evitar com que a lista seja limpa:

    estado.getCidades().clear() > clear é um método disponível de list,
    onde se você utilizar, vai limpar todas as informações da lista. Com a view implementada,
    não é possível fazer a limpa.
     */

    public List<Cidade> getCidades() {
        return Collections.unmodifiableList(cidades);
    }

    /*
    Já nesse método, eu utilizei o for-each, para que percorresse toda a lista,
    e a cada posição da lista percorrida, vai retornar o toString() de cidade.
     */
    public void mostrarCidade() {
        for(Cidade cidade : cidades) {
            System.out.print(cidade);
        }
    }

    /*
    Por último, implementei o toString() conforme solicitado pela documentação,
    e utilizei a anotação @Override, que é uma anotação de sobrescrita de método. Com essa anotação,
    estou informado ao compilador que estou reaproveitando o método vindo da classe Object,
    que tem como responsabilidade retornar o relatório das informações dos atributos.
     */

    @Override
    public String toString() {
        return "Estado\nId: " + id +
                "\n"
                + "Nome: " + nome
                + "\n";
    }
}