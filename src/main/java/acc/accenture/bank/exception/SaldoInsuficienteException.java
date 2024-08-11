package acc.accenture.bank.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar a operação.");
    }
}
