package entidades;

import entidades.enums.TipoCliente;
import exception.RegraDeNegocioException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Cliente {
    /*
    Atributos com os nomes do diagrama: nome, email, cpfOuCnpj e tipo.
     */
    private Integer id;
    private String nome;
    private String email;
    private String cpfOuCnpj;
    private TipoCliente tipo;

    /*
    Associações com os nomes das pontas do diagrama: telefones (1..*), enderecos (1..*) e pedidos (*).
    As listas são criadas aqui dentro e nunca recebidas de fora, para que ninguém consiga alterá-las
    sem passar pelos métodos adicionarX().
     */
    private final List<Telefone> telefones = new ArrayList<>();
    private final List<Endereco> enderecos = new ArrayList<>();
    private final List<Pedido> pedidos = new ArrayList<>();

    private static Integer proxOid = 1;

    public Cliente(String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
        setNome(nome);
        setEmail(email);
        if (cpfOuCnpj == null || cpfOuCnpj.isBlank()) {
            throw new RegraDeNegocioException("O CPF/CNPJ não pode ser nulo ou em branco!");
        }
        if (tipo == null) {
            throw new RegraDeNegocioException("O tipo do cliente não pode ser nulo!");
        }
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo;
        this.id = proxOid++;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("O nome não pode ser nulo ou em branco!");
        }
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new RegraDeNegocioException("O e-mail não pode ser nulo ou em branco!");
        }
        this.email = email;
    }

    /*
    cpfOuCnpj não tem setter: ele é a chave usada em equals() e hashCode(),
    e alterá-lo depois quebraria a verificação de CPF/CNPJ único.
     */
    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    /*
    Telefone só existe vinculado a um cliente. Por isso ele é criado aqui dentro,
    e o construtor de Telefone não é público.
     */
    public Telefone adicionarTelefone(String numero) {
        Telefone telefone = new Telefone(numero);
        telefones.add(telefone);
        return telefone;
    }

    /*
    O endereço é criado por meio do cliente, já vinculado a ele.
     */
    public Endereco adicionarEndereco(String logradouro, String numero, String complemento,
                                      String bairro, String cep, Cidade cidade) {
        Endereco endereco = new Endereco(logradouro, numero, complemento, bairro, cep, cidade, this);
        enderecos.add(endereco);
        return endereco;
    }

    /*
    O construtor de Pedido chama este método,
    garantindo que Pedido conheça o Cliente e o Cliente conheça seus pedidos.
     */
    void adicionarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new RegraDeNegocioException("O pedido não pode ser nulo.");
        }
        if (!pedidos.contains(pedido)) {
            pedidos.add(pedido);
        }
    }

    public List<Telefone> getTelefones() {
        return Collections.unmodifiableList(telefones);
    }

    public List<Endereco> getEnderecos() {
        return Collections.unmodifiableList(enderecos);
    }

    public List<Pedido> getPedidos() {
        return Collections.unmodifiableList(pedidos);
    }

    /*
    equals() e hashCode() pelo cpfOuCnpj, conforme o item 7 da Parte 1,
    para que a regra de CPF/CNPJ único possa ser verificada com contains().
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpfOuCnpj, cliente.cpfOuCnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpfOuCnpj);
    }

    @Override
    public String toString() {
        return "Cliente:\nId: " + id
                + "\nNome: " + nome
                + "\nE-mail: " + email
                + "\nCPF/CNPJ: " + cpfOuCnpj
                + "\nTipo: " + tipo
                + "\nTelefones: " + telefones
                + "\n";
    }
}
