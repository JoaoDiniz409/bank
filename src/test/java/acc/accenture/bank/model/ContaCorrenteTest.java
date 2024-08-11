package acc.accenture.bank.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ContaCorrenteTest {

    @Test
    void testContaCorrenteCreation() {
        Agencia agencia = new Agencia(1L, "Agência Central", "Rua Principal, 123", "1234-5678");
        Cliente cliente = new Cliente(1L, "João Silva", "12345678901", "9876-5432");
        ContaCorrente conta = new ContaCorrente(1L, "12345-6", BigDecimal.valueOf(1000.00), agencia, cliente);

        assertEquals(1L, conta.getId());
        assertEquals("12345-6", conta.getNumero());
        assertEquals(BigDecimal.valueOf(1000.00), conta.getSaldo());
        assertEquals(agencia, conta.getAgencia());
        assertEquals(cliente, conta.getCliente());
    }

    @Test
    void testContaCorrenteSetters() {
        ContaCorrente conta = new ContaCorrente();
        Agencia agencia = new Agencia(2L, "Agência Norte", "Avenida Norte, 456", "8765-4321");
        Cliente cliente = new Cliente(2L, "Maria Souza", "10987654321", "4321-9876");

        conta.setId(2L);
        conta.setNumero("65432-1");
        conta.setSaldo(BigDecimal.valueOf(500.00));
        conta.setAgencia(agencia);
        conta.setCliente(cliente);

        assertEquals(2L, conta.getId());
        assertEquals("65432-1", conta.getNumero());
        assertEquals(BigDecimal.valueOf(500.00), conta.getSaldo());
        assertEquals(agencia, conta.getAgencia());
        assertEquals(cliente, conta.getCliente());
    }
}