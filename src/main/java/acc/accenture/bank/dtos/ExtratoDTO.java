package acc.accenture.bank.dtos;

import acc.accenture.bank.enums.Operacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoDTO {

    private Long id; // ID do extrato
    private LocalDateTime dataHoraMovimento; // Data e hora do movimento
    private Operacao operacao; // Tipo de operação (SAQUE, DEPOSITO, TRANSFERENCIA)
    private BigDecimal valor; // Valor da operação
    private Long contaCorrenteId; // ID da conta corrente
    private Long contaDestinoId; // ID da conta de destino (para transferências)
}
