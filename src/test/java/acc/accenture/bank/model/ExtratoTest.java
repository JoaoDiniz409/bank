package acc.accenture.bank.model;

import acc.accenture.bank.enums.Operacao;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExtratoTest {

    @Test
    void testExtratoCreation() {
        ContaCorrente contaOrigem = new ContaCorrente();
        ContaCorrente contaDestino = new ContaCorrente();
        Extrato extrato = new Extrato(1L, LocalDateTime.now(), Operacao.DEPOSITO, BigDecimal.valueOf(200.00), contaOrigem, contaDestino);

        assertEquals(1L, extrato.getId());
        assertNotNull(extrato.getDataHoraMovimento());
        assertEquals(Operacao.DEPOSITO, extrato.getOperacao());
        assertEquals(BigDecimal.valueOf(200.00), extrato.getValor());
        assertEquals(contaOrigem, extrato.getContaCorrente());
        assertEquals(contaDestino, extrato.getContaDestino());
    }

    @Test
    void testExtratoSetters() {
        Extrato extrato = new Extrato();
        ContaCorrente contaOrigem = new ContaCorrente();
        ContaCorrente contaDestino = new ContaCorrente();
        LocalDateTime dataHora = LocalDateTime.now();

        extrato.setId(2L);
        extrato.setDataHoraMovimento(dataHora);
        extrato.setOperacao(Operacao.SAQUE);
        extrato.setValor(BigDecimal.valueOf(150.00));
        extrato.setContaCorrente(contaOrigem);
        extrato.setContaDestino(contaDestino);

        assertEquals(2L, extrato.getId());
        assertEquals(dataHora, extrato.getDataHoraMovimento());
        assertEquals(Operacao.SAQUE, extrato.getOperacao());
        assertEquals(BigDecimal.valueOf(150.00), extrato.getValor());
        assertEquals(contaOrigem, extrato.getContaCorrente());
        assertEquals(contaDestino, extrato.getContaDestino());
    }
}