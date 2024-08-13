package acc.accenture.bank.repository;

import acc.accenture.bank.model.Agencia;
import acc.accenture.bank.model.Cliente;
import acc.accenture.bank.model.ContaCorrente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContaCorrenteRepositoryTest {

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private ContaCorrente contaCorrente;
    private Agencia agencia;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        agencia = new Agencia(null, "Agencia Central", "Rua Central, 123", "123456789");
        cliente = new Cliente(null, "João Silva", "12345678901", "987654321");

        agenciaRepository.save(agencia);
        clienteRepository.save(cliente);

        contaCorrente = new ContaCorrente();
        contaCorrente.setNumero("12345-6");
        contaCorrente.setSaldo(BigDecimal.valueOf(1000.00));
        contaCorrente.setAgencia(agencia);
        contaCorrente.setCliente(cliente);

        contaCorrenteRepository.save(contaCorrente);
    }

    @Test
    void shouldFindById() {
        Optional<ContaCorrente> foundContaCorrente = contaCorrenteRepository.findById(contaCorrente.getId());
        assertTrue(foundContaCorrente.isPresent());
        assertEquals(contaCorrente.getNumero(), foundContaCorrente.get().getNumero());
    }

    @Test
    void shouldReturnEmptyForNonExistingId() {
        Optional<ContaCorrente> foundContaCorrente = contaCorrenteRepository.findById(-1L);
        assertFalse(foundContaCorrente.isPresent());
    }
}