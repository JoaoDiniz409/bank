package acc.accenture.bank.mapper;

import acc.accenture.bank.dtos.AgenciaDTO;
import acc.accenture.bank.model.Agencia;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AgenciaMapperTest {

    private final AgenciaMapper agenciaMapper = new AgenciaMapper();

    @Test
    void testToDTO() {
        Agencia agencia = new Agencia(1L, "Agência Central", "Rua Principal, 123", "1234-5678");
        AgenciaDTO agenciaDTO = agenciaMapper.toDTO(agencia);

        assertEquals(1L, agenciaDTO.getId());
        assertEquals("Agência Central", agenciaDTO.getNome());
        assertEquals("Rua Principal, 123", agenciaDTO.getEndereco());
        assertEquals("1234-5678", agenciaDTO.getTelefone());
    }

    @Test
    void testToEntity() {
        AgenciaDTO agenciaDTO = new AgenciaDTO(2L, "Agência Norte", "Avenida Norte, 456", "8765-4321");
        Agencia agencia = agenciaMapper.toEntity(agenciaDTO);

        assertEquals(2L, agencia.getId());
        assertEquals("Agência Norte", agencia.getNome());
        assertEquals("Avenida Norte, 456", agencia.getEndereco());
        assertEquals("8765-4321", agencia.getTelefone());
    }
}
