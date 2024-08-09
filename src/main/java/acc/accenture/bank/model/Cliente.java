package acc.accenture.bank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID do cliente, gerado pelo banco

    @Column(nullable = false)
    private String nome; // Nome do cliente

    @Column(nullable = false, length = 14, unique = true)
    private String cpf; // CPF do cliente

    @Column(nullable = false)
    private String telefone; // Telefone do cliente
}
