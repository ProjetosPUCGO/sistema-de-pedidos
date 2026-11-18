package exception;

public class RegraDeNegocioException extends RuntimeException{
    /*

    Essa classe foi criada para mostrar que a regra de negocio extends RuntimeException,
    e com isso criei um construtor que vai receber String msg, e o super() foi usado para reaproveitar
    a atribuição dos valores advindas da classe RuntimeException.

     */
    public RegraDeNegocioException(String msg) {
        super(msg);
    }
}
