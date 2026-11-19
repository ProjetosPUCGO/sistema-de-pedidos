package entities;

import utilitarios.Util;

public class Endereco {
    private int iD;
    private String logradouro;
    private String numero;
    private String complemnto;
    private String bairro;
    private String cep;

    public Endereco(String logradouro, String numero, 
        String complemnto, String bairro, String cep) {
        this.iD = Util.gerarIdEndereco();
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemnto = complemnto;
        this.bairro = bairro;
        this.cep = cep;
    }

}
