package acc.accenture.bank.dtos;

import acc.accenture.bank.enums.Operacao;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExtratoDTOTest {

    @Test
    void testExtratoDTOCreation() {
        LocalDateTime now = LocalDateTime.now();
        ExtratoDTO extratoDTO = new ExtratoDTO(now, Operacao.DEPOSITO, BigDecimal.valueOf(200.00), 1L, 2L);

        assertNotNull(extratoDTO.getDataHoraMovimento());
        assertEquals(Operacao.DEPOSITO, extratoDTO.getOperacao());
        assertEquals(BigDecimal.valueOf(200.00), extratoDTO.getValor());
        assertEquals(1L, extratoDTO.getContaCorrenteId());
        assertEquals(2L, extratoDTO.getContaDestinoId());
    }

    @Test
    void testExtratoDTOSetters() {
        ExtratoDTO extratoDTO = new ExtratoDTO();
        LocalDateTime now = LocalDateTime.now();
        extratoDTO.setDataHoraMovimento(now);
        extratoDTO.setOperacao(Operacao.SAQUE);
        extratoDTO.setValor(BigDecimal.valueOf(150.00));
        extratoDTO.setContaCorrenteId(1L);
        extratoDTO.setContaDestinoId(2L);

        assertEquals(now, extratoDTO.getDataHoraMovimento());
        assertEquals(Operacao.SAQUE, extratoDTO.getOperacao());
        assertEquals(BigDecimal.valueOf(150.00), extratoDTO.getValor());
        assertEquals(1L, extratoDTO.getContaCorrenteId());
        assertEquals(2L, extratoDTO.getContaDestinoId());
    }

    @Test
    void testExtratoDTOConstructorWithPartialParameters() {
        LocalDateTime now = LocalDateTime.now();
        ExtratoDTO extratoDTO = new ExtratoDTO(now, Operacao.TRANSFERENCIA, BigDecimal.valueOf(300.00), 1L);

        assertEquals(now, extratoDTO.getDataHoraMovimento());
        assertEquals(Operacao.TRANSFERENCIA, extratoDTO.getOperacao());
        assertEquals(BigDecimal.valueOf(300.00), extratoDTO.getValor());
        assertEquals(1L, extratoDTO.getContaCorrenteId());
    }
}