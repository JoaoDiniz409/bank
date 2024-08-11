package acc.accenture.bank.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AgenciaTest {

    @Test
    void testAgenciaCreation() {
        Agencia agencia = new Agencia(1L, "Agência Central", "Rua Principal, 123", "1234-5678");

        assertEquals(1L, agencia.getId());
        assertEquals("Agência Central", agencia.getNome());
        assertEquals("Rua Principal, 123", agencia.getEndereco());
        assertEquals("1234-5678", agencia.getTelefone());
    }

    @Test
    void testAgenciaSetters() {
        Agencia agencia = new Agencia();
        agencia.setId(2L);
        agencia.setNome("Agência Norte");
        agencia.setEndereco("Avenida Norte, 456");
        agencia.setTelefone("8765-4321");

        assertEquals(2L, agencia.getId());
        assertEquals("Agência Norte", agencia.getNome());
        assertEquals("Avenida Norte, 456", agencia.getEndereco());
        assertEquals("8765-4321", agencia.getTelefone());
    }
}
