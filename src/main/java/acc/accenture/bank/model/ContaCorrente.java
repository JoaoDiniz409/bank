package acc.accenture.bank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID da conta corrente, gerado pelo banco

    @Column(nullable = false)
    private String numero; // Número da conta corrente

    @Column(nullable = false)
    private BigDecimal saldo; // Saldo da conta corrente

    @ManyToOne
    @JoinColumn(name = "agencia_id", nullable = false)
    private Agencia agencia; // Chave estrangeira para a agência

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // Chave estrangeira para o cliente
}
