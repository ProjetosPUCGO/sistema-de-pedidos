package entidades;

import exception.RegraDeNegocioException;

public class Cidade {
    /*
    Os atributos id e nome foram declarados, conforme a especificação na
    documentação (o atributo se chamava "oid"; foi renomeado para "id", como nas demais classes).
     */
    private Integer id;
    private String nome;

    /*
    Esse atributo foi implementado para mostrar que a CIDADE é criada por meio
    de um ESTADO. Portanto, é necessário com que o ESTADO seja colocado como atributo de referência,
    e o relacionamento TEM-UM (composição) seja atendido.
     */
    private Estado estado;

    /*
    Esse atributo estático chamado proxOid é responsável por atribuir
    os id's únicos para cada Cidade. A primeira Cidade começa com o ID 1,
    e depois vai sendo incrementado cada ID para cada Cidade.
     */
    private static Integer proxOid = 1;

    /*
    No construtor, eu tirei o modificador de acesso, porque a documentação exige que uma cidade
    seja criada por meio de um Estado (estado.adicionarCidade(nome)). Assim, o construtor
    só está visível nas classes pertencentes ao mesmo pacote.
     */
    Cidade(String nome, Estado estado) {
        setNome(nome);
        if (estado == null) {
            throw new RegraDeNegocioException("O estado não pode ser nulo.");
        }
        this.estado = estado;
        this.id = proxOid++;
    }

    /*
    Evitei colocar setEstado(), porque o Estado poderia ser alterado através desse método.
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

    public Estado getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Cidade:\nId: " + id
                + "\n"
                + "Nome: " + nome
                + "\n"
                + "Estado: " + estado.getNome()
                + "\n";
    }
}
