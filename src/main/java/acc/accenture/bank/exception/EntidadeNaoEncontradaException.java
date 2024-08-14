package acc.accenture.bank.exception;

public class EntidadeNaoEncontradaException extends RuntimeException{
    public EntidadeNaoEncontradaException(String entidade) {
        super(entidade + " não encontrada.");
    }
}
