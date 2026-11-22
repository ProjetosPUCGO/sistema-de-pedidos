package utilitarios;

public class Util {
   
  //Cada iD inicia com "0". Logo, "Cliente1" terá iD = 0

    private static int contadorIdCliente = 0;
    private static int contadorIdPagamento = 0;
    private static int contadorIdEndereco = 0;

    //Geradores e iD's
    public static int gerarIdCliente(){
        return  contadorIdCliente++;
    }
    
    public static int gerarIdEndereco() {
        return contadorIdEndereco++;
    }

    public static int gerarIdPagamento() {
        return  contadorIdPagamento++;
    }

}
