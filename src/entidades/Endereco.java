package entidades;

import exception.RegraDeNegocioException;

public class Endereco {
    /*
    Atributos conforme o diagrama.
     */
    private Integer id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;

    /*
    Associações do diagrama: cada endereço pertence a um cliente (lado inverso de Cliente.enderecos)
    e referencia uma cidade cadastrada.
     */
    private Cliente cliente;
    private Cidade cidade;

    private static Integer proxOid = 1;

    /*
    Construtor sem modificador de acesso: o endereço só é criado por Cliente.adicionarEndereco(),
    seguindo a mesma ideia de Estado.adicionarCidade().
     */
    Endereco(String logradouro, String numero, String complemento, String bairro,
             String cep, Cidade cidade, Cliente cliente) {
        if (cidade == null) {
            throw new RegraDeNegocioException("A cidade do endereço não pode ser nula.");
        }
        if (cliente == null) {
            throw new RegraDeNegocioException("O cliente do endereço não pode ser nulo.");
        }
        setLogradouro(logradouro);
        setNumero(numero);
        setComplemento(complemento);
        setBairro(bairro);
        setCep(cep);
        this.cidade = cidade;
        this.cliente = cliente;
        this.id = proxOid++;
    }

    public Integer getId() {
        return id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        if (logradouro == null || logradouro.isBlank()) {
            throw new RegraDeNegocioException("O logradouro não pode ser nulo ou em branco!");
        }
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    /*
    Sem setCliente(): um endereço não muda de dono.
     */
    public Cliente getCliente() {
        return cliente;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        if (cidade == null) {
            throw new RegraDeNegocioException("A cidade do endereço não pode ser nula.");
        }
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        String compl = (complemento == null || complemento.isBlank()) ? "" : ", " + complemento;
        return logradouro + ", nº " + numero + compl
                + " - " + bairro
                + " - CEP " + cep
                + " - " + cidade.getNome() + "/" + cidade.getEstado().getNome();
    }
}
