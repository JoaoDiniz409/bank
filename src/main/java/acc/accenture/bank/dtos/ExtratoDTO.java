package acc.accenture.bank.dtos;

import acc.accenture.bank.enums.Operacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoDTO {

    // ID do extrato
    private LocalDateTime dataHoraMovimento; // Data e hora do movimento
    private Operacao operacao; // Tipo de operação (SAQUE, DEPOSITO, TRANSFERENCIA)
    private BigDecimal valor; // Valor da operação
    private Long contaCorrenteId; // ID da conta corrente
    private Long contaDestinoId; // ID da conta de destino (para transferências)

    public ExtratoDTO(LocalDateTime data,Operacao operacao, BigDecimal valor,Long id) {
        this.dataHoraMovimento = data;
        this.operacao = operacao;
        this.valor = valor;
        this.contaCorrenteId = id;

    };
}

