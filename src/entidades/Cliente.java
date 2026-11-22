package entidades;
import java.util.List;

import entidades.enums.TipoCliente;

public class Cliente {
    private int id;
    private String nome;
    private String cpfOuCnpj;
    private String email;
    private List<Telefone> telefone;
    private List<Endereco> endereco;
    private TipoCliente tipoCliente;

    private static Integer proxOid = 1;

    public Cliente(String nome, String cpfOuCnpj, String email,
                   List<Telefone> telefone, List<Endereco> endereco) {
        this.id = proxOid++;
        this.nome = nome;
        this.cpfOuCnpj = cpfOuCnpj;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;

        //Validação automática de caracteres
        if (cpfOuCnpj.length() == 11){
            tipoCliente = TipoCliente.PESSOA_FISICA;
        }else if (cpfOuCnpj.length() == 14){
            tipoCliente = TipoCliente.PESSOA_JURIDICA;
        }

    }

}