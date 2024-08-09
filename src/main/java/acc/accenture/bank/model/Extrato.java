package acc.accenture.bank.model;

import acc.accenture.bank.enums.Operacao;
import acc.accenture.bank.repository.ContaCorrenteRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Extrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID do extrato, gerado pelo banco

    @Column(nullable = false)
    private LocalDateTime dataHoraMovimento; // Data e hora do movimento

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Operacao operacao; // Tipo de operação (SAQUE, DEPOSITO, TRANSFERENCIA)

    @Column(nullable = false)
    private BigDecimal valor; // Valor da operação

    @ManyToOne
    @JoinColumn(name = "conta_corrente_id", nullable = false)
    private ContaCorrente contaCorrente; // Chave estrangeira para a conta corrente

    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    private ContaCorrente contaDestino; // Chave estrangeira para a conta de destino (para transferências)


}
