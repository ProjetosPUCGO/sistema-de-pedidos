package entities;
import java.util.List;

import enums.TipoCliente;
import utilitarios.Util;

public class Cliente {
    private int iD;
    private String nome;
    private String cpfOuCnpj;
    private String email;
    private List<Telefone> telefone;
    private List<Endereco> endereco;
    private TipoCliente tipoCliente;

    public Cliente(String nome, String cpfOuCnpj, String email, 
        List<Telefone> telefone, List<Endereco> endereco) {
        this.iD = Util.gerarIdCliente();
        this.nome = nome;
        this.cpfOuCnpj = cpfOuCnpj;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;

    } 

}
