package acc.accenture.bank.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testClienteCreation() {
        Cliente cliente = new Cliente(1L, "João Silva", "12345678901", "9876-5432");

        assertEquals(1L, cliente.getId());
        assertEquals("João Silva", cliente.getNome());
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("9876-5432", cliente.getTelefone());
    }

    @Test
    void testClienteSetters() {
        Cliente cliente = new Cliente();
        cliente.setId(2L);
        cliente.setNome("Maria Souza");
        cliente.setCpf("10987654321");
        cliente.setTelefone("4321-9876");

        assertEquals(2L, cliente.getId());
        assertEquals("Maria Souza", cliente.getNome());
        assertEquals("10987654321", cliente.getCpf());
        assertEquals("4321-9876", cliente.getTelefone());
    }
}
