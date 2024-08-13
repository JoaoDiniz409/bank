package acc.accenture.bank.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AgenciaDTOTest {

    @Test
    void testAgenciaDTOCreation() {
        AgenciaDTO agenciaDTO = new AgenciaDTO(1L, "Agência Central", "Rua Principal, 123", "1234-5678");

        assertEquals(1L, agenciaDTO.getId());
        assertEquals("Agência Central", agenciaDTO.getNome());
        assertEquals("Rua Principal, 123", agenciaDTO.getEndereco());
        assertEquals("1234-5678", agenciaDTO.getTelefone());
    }

    @Test
    void testAgenciaDTOSetters() {
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setId(2L);
        agenciaDTO.setNome("Agência Norte");
        agenciaDTO.setEndereco("Avenida Norte, 456");
        agenciaDTO.setTelefone("8765-4321");

        assertEquals(2L, agenciaDTO.getId());
        assertEquals("Agência Norte", agenciaDTO.getNome());
        assertEquals("Avenida Norte, 456", agenciaDTO.getEndereco());
        assertEquals("8765-4321", agenciaDTO.getTelefone());
    }
}
