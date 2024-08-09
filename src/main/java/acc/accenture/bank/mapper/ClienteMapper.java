package acc.accenture.bank.mapper;

import acc.accenture.bank.dtos.ClienteDTO;
import acc.accenture.bank.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone()
        );
    }

    public Cliente toEntity(ClienteDTO clienteDTO) {
        return new Cliente(
                clienteDTO.getId(),
                clienteDTO.getNome(),
                clienteDTO.getCpf(),
                clienteDTO.getTelefone()
        );
    }
}
