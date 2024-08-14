package acc.accenture.bank.repository;

import acc.accenture.bank.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("12345678901");
        cliente.setTelefone("987654321");

        clienteRepository.save(cliente);
    }

    @Test
    void shouldFindById() {
        Optional<Cliente> foundCliente = clienteRepository.findById(cliente.getId());
        assertTrue(foundCliente.isPresent());
        assertEquals(cliente.getNome(), foundCliente.get().getNome());
    }

    @Test
    void shouldReturnEmptyForNonExistingId() {
        Optional<Cliente> foundCliente = clienteRepository.findById(-1L);
        assertFalse(foundCliente.isPresent());
    }
}
