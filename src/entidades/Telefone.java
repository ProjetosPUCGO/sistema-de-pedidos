package entidades;

import exception.RegraDeNegocioException;

/*
Telefone não tem id próprio e só existe vinculado a um cliente.
Por isso o construtor não é público e o telefone é criado por Cliente.adicionarTelefone().
 */
public class Telefone {
    private String numero;

    Telefone(String numero) {
        setNumero(numero);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        if (numero == null || numero.isBlank()) {
            throw new RegraDeNegocioException("O número do telefone não pode ser nulo ou em branco!");
        }
        this.numero = numero;
    }

    @Override
    public String toString() {
        return numero;
    }
}
