package acc.accenture.bank.exception;

public class CampoObrigatorioException extends RuntimeException{
    public CampoObrigatorioException(String campo) {
        super(campo + " é um campo obrigatório e não pode estar vazio.");
    }
}
