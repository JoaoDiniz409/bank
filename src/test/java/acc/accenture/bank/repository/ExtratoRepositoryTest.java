package acc.accenture.bank.repository;

import acc.accenture.bank.enums.Operacao;
import acc.accenture.bank.model.Agencia;
import acc.accenture.bank.model.Cliente;
import acc.accenture.bank.model.ContaCorrente;
import acc.accenture.bank.model.Extrato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ExtratoRepositoryTest {

    @Autowired
    private ExtratoRepository extratoRepository;

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private ContaCorrente contaCorrente;
    private Extrato extrato;

    @BeforeEach
    void setUp() {
        Agencia agencia = new Agencia(null, "Agencia Central", "Rua Central, 123", "123456789");
        Cliente cliente = new Cliente(null, "João Silva", "12345678901", "987654321");

        agenciaRepository.save(agencia);
        clienteRepository.save(cliente);

        contaCorrente = new ContaCorrente();
        contaCorrente.setNumero("12345-6");
        contaCorrente.setSaldo(BigDecimal.valueOf(1000.00));
        contaCorrente.setAgencia(agencia);
        contaCorrente.setCliente(cliente);
        contaCorrenteRepository.save(contaCorrente);

        extrato = new Extrato();
        extrato.setDataHoraMovimento(LocalDateTime.now());
        extrato.setOperacao(Operacao.DEPOSITO);
        extrato.setValor(BigDecimal.valueOf(500.00));
        extrato.setContaCorrente(contaCorrente);

        extratoRepository.save(extrato);
    }

    @Test
    void shouldFindByContaCorrenteId() {
        List<Extrato> extratos = extratoRepository.findByContaCorrenteId(contaCorrente.getId());
        assertTrue(!extratos.isEmpty());
        assertEquals(extrato.getValor(), extratos.get(0).getValor());
    }

    @Test
    void shouldReturnEmptyListForNonExistingContaCorrenteId() {
        List<Extrato> extratos = extratoRepository.findByContaCorrenteId(-1L);
        assertTrue(extratos.isEmpty());
    }
}