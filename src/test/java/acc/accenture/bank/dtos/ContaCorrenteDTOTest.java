package acc.accenture.bank.dtos;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContaCorrenteDTOTest {

    @Test
    void testContaCorrenteDTOCreation() {
        ContaCorrenteDTO contaCorrenteDTO = new ContaCorrenteDTO("12345-6", BigDecimal.valueOf(1000.00), 1L, 2L);

        assertEquals("12345-6", contaCorrenteDTO.getNumero());
        assertEquals(BigDecimal.valueOf(1000.00), contaCorrenteDTO.getSaldo());
        assertEquals(1L, contaCorrenteDTO.getAgenciaId());
        assertEquals(2L, contaCorrenteDTO.getClienteId());
    }

    @Test
    void testContaCorrenteDTOSetters() {
        ContaCorrenteDTO contaCorrenteDTO = new ContaCorrenteDTO();
        contaCorrenteDTO.setNumero("65432-1");
        contaCorrenteDTO.setSaldo(BigDecimal.valueOf(500.00));
        contaCorrenteDTO.setAgenciaId(2L);
        contaCorrenteDTO.setClienteId(3L);

        assertEquals("65432-1", contaCorrenteDTO.getNumero());
        assertEquals(BigDecimal.valueOf(500.00), contaCorrenteDTO.getSaldo());
        assertEquals(2L, contaCorrenteDTO.getAgenciaId());
        assertEquals(3L, contaCorrenteDTO.getClienteId());
    }
}