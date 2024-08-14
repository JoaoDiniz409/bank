package acc.accenture.bank.repository;

import acc.accenture.bank.model.Agencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AgenciaRepositoryTest {

    @Autowired
    private AgenciaRepository agenciaRepository;

    private Agencia agencia;

    @BeforeEach
    void setUp() {
        agencia = new Agencia();
        agencia.setNome("Agencia Central");
        agencia.setEndereco("Rua Central, 123");
        agencia.setTelefone("123456789");

        agenciaRepository.save(agencia);
    }

    @Test
    void shouldFindById() {
        Optional<Agencia> foundAgencia = agenciaRepository.findById(agencia.getId());
        assertTrue(foundAgencia.isPresent());
        assertEquals(agencia.getNome(), foundAgencia.get().getNome());
    }

    @Test
    void shouldReturnEmptyForNonExistingId() {
        Optional<Agencia> foundAgencia = agenciaRepository.findById(-1L);
        assertFalse(foundAgencia.isPresent());
    }
}
