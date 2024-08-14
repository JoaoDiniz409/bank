package acc.accenture.bank.exception;

public class TamanhoMaximoExcedidoException extends RuntimeException{
    public TamanhoMaximoExcedidoException(String campo, int tamanhoMaximo) {
        super(campo + " excede o tamanho máximo permitido de " + tamanhoMaximo + " caracteres.");
    }
}
