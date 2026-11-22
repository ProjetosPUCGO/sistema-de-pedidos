package entidades;

public class Endereco {
    private int id;
    private String logradouro;
    private String numero;
    private String complemnto;
    private String bairro;
    private String cep;

    private static Integer proxOid = 1;

    public Endereco(String logradouro, String numero,
                    String complemnto, String bairro, String cep) {
        this.id = proxOid++;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemnto = complemnto;
        this.bairro = bairro;
        this.cep = cep;
    }

}