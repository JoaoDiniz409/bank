package acc.accenture.bank.exception;

public class OperacaoDesconhecidaException extends RuntimeException{
    public OperacaoDesconhecidaException(String operacao) {
        super("Operação desconhecida: " + operacao);
    }
}
