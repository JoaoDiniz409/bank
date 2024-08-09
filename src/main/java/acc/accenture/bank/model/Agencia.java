package acc.accenture.bank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID da agência, gerado pelo banco

    @Column(nullable = false)
    private String nome; // Nome da agência

    @Column(nullable = false)
    private String endereco; // Endereço da agência

    @Column(nullable = false)
    private String telefone; // Telefone da agência

}
