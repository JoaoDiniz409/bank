package acc.accenture.bank.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteDTOTest {

    @Test
    void testClienteDTOCreation() {
        ClienteDTO clienteDTO = new ClienteDTO(1L, "João Silva", "12345678901", "9876-5432");

        assertEquals(1L, clienteDTO.getId());
        assertEquals("João Silva", clienteDTO.getNome());
        assertEquals("12345678901", clienteDTO.getCpf());
        assertEquals("9876-5432", clienteDTO.getTelefone());
    }

    @Test
    void testClienteDTOSetters() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(2L);
        clienteDTO.setNome("Maria Souza");
        clienteDTO.setCpf("10987654321");
        clienteDTO.setTelefone("4321-9876");

        assertEquals(2L, clienteDTO.getId());
        assertEquals("Maria Souza", clienteDTO.getNome());
        assertEquals("10987654321", clienteDTO.getCpf());
        assertEquals("4321-9876", clienteDTO.getTelefone());
    }
}