package entidades;

import exception.RegraDeNegocioException;

public class Cidade {
    /*
  Os atributos id e nome foram declarados, conforme a especificação na
  documentação.
   */
    private int oid;
    private String nome;

    /*
    Esse atributo foi implementado para mostrar que a CIDADE é criada por meio
    de um ESTADO. Portanto, é necessário com que o ESTADO seja colocado como atributo de referência,
    e o relacionamento TEM-UM (composição) seja atendido.
     */
    private Estado estado;

        /*
    Esse atributo estático chamado proxOid é responsável por atribuir
    os id's únicos para cada Estado. O primeiro Estado começa com o ID 1,
    e depois vai sendo incrementado cada ID para cada Estado.
     */

    private static int proxOid = 1;

    /*
    No construtor, eu tirei o modificador de acesso, porque a documentação exige que uma cidade
    seja criada por meio de um Estado. Colocando o modificador public, por exemplo,
    o construtor poderia ser retornado dentro da classe main, e uma classe poderia ter sido criada sem
    a utilização do método adicionarCidade. Portanto, para evitar essa ambiguidade, eu tirei o modificador de acesso,
    e só está visível nas classes pertencentes ao mesmo pacote.

    Ela recebe como parâmetro o nome da cidade, e também a variável de referência estado.

    Coloquei uma validação, verificando se o estado == null. Se for null, significa que o Estado não existe e,
    por isso, vai cair em uma exceção chamada RegraDeNegocioException, que extends de RuntimeException.

    Caso a validação passe, o estado vai ser atribuído o valor, e o id segue a mesma lógica de Estado.

     */
    Cidade(String nome, Estado estado) {
        setNome(nome);
        if (estado == null) {
            throw new RegraDeNegocioException("O estado não pode ser nulo.");
        }
        this.estado = estado;
        this.oid = proxOid++;
    }

    /*
    Nessa parte, os getters e setters foram implementados de acordo com a necessidade.

    Evitei colocar setEstado(), porque o Estado poderia ser alterado através desse método.
     */

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("O nome não pode ser nulo ou em branco!");
        }

        this.nome = nome;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getNome() {
        return nome;
    }

    /*
    Por último, coloquei o toString() para retornar o relatório.
     */
    @Override
    public String toString() {
        return "Cidade:\nId: " + oid
                + "\n"
                + "Nome: " + nome
                + "\n";
    }
}
