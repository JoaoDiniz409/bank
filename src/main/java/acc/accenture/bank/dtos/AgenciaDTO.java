package acc.accenture.bank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgenciaDTO {

    private Long id; // ID da agência
    private String nome; // Nome da agência
    private String endereco; // Endereço da agência
    private String telefone; // Telefone da agência
}
