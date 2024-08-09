package acc.accenture.bank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private Long id; // ID do cliente
    private String nome; // Nome do cliente
    private String cpf; // CPF do cliente
    private String telefone; // Telefone do cliente
}
