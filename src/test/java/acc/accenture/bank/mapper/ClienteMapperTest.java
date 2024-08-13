package acc.accenture.bank.mapper;

import acc.accenture.bank.dtos.ClienteDTO;
import acc.accenture.bank.model.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteMapperTest {

    private final ClienteMapper clienteMapper = new ClienteMapper();

    @Test
    void testToDTO() {
        Cliente cliente = new Cliente(1L, "João Silva", "12345678901", "9876-5432");
        ClienteDTO clienteDTO = clienteMapper.toDTO(cliente);

        assertEquals(1L, clienteDTO.getId());
        assertEquals("João Silva", clienteDTO.getNome());
        assertEquals("12345678901", clienteDTO.getCpf());
        assertEquals("9876-5432", clienteDTO.getTelefone());
    }

    @Test
    void testToEntity() {
        ClienteDTO clienteDTO = new ClienteDTO(2L, "Maria Souza", "10987654321", "4321-9876");
        Cliente cliente = clienteMapper.toEntity(clienteDTO);

        assertEquals(2L, cliente.getId());
        assertEquals("Maria Souza", cliente.getNome());
        assertEquals("10987654321", cliente.getCpf());
        assertEquals("4321-9876", cliente.getTelefone());
    }
}
