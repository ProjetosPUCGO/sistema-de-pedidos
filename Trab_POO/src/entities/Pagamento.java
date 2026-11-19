package entities;
import enums.EstadoPagamento;
import enums.TipoCliente;
import utilitarios.Util;

public abstract class Pagamento {
    private EstadoPagamento estadoPagamento;
    private int iD;

    public Pagamento(){
        this.estadoPagamento = EstadoPagamento.PENDENTE;
        this.iD = Util.gerarIdPagamento();
    }
}
