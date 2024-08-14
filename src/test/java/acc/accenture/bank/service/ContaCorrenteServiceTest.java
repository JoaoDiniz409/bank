package acc.accenture.bank.service;


import acc.accenture.bank.dtos.ContaCorrenteDTO;
import acc.accenture.bank.dtos.ExtratoDTO;
import acc.accenture.bank.enums.Operacao;
import acc.accenture.bank.exception.CampoObrigatorioException;
import acc.accenture.bank.exception.EntidadeNaoEncontradaException;
import acc.accenture.bank.exception.OperacaoDesconhecidaException;
import acc.accenture.bank.exception.SaldoInsuficienteException;
import acc.accenture.bank.mapper.ContaCorrenteMapper;
import acc.accenture.bank.mapper.ExtratoMapper;
import acc.accenture.bank.model.ContaCorrente;
import acc.accenture.bank.model.Extrato;

import acc.accenture.bank.repository.ContaCorrenteRepository;
import acc.accenture.bank.repository.ExtratoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContaCorrenteServiceTest {

    @InjectMocks
    private ContaCorrenteService contaCorrenteService;

    @Mock
    private ContaCorrenteRepository contaCorrenteRepository;

    @Mock
    private ContaCorrenteMapper contaCorrenteMapper;

    @Mock
    private ExtratoRepository extratoRepository;

    @Mock
    private ExtratoMapper extratoMapper;

    private ContaCorrente contaCorrente;
    private ContaCorrenteDTO contaCorrenteDTO;
    private Extrato extrato;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        contaCorrente = new ContaCorrente();
        contaCorrente.setId(1L);
        contaCorrente.setNumero("12345");
        contaCorrente.setSaldo(BigDecimal.valueOf(1000));

        contaCorrenteDTO = new ContaCorrenteDTO();
        //contaCorrenteDTO.setId(1L);
        contaCorrenteDTO.setNumero("12345");
        contaCorrenteDTO.setSaldo(BigDecimal.valueOf(1000));

        extrato = new Extrato();
        extrato.setId(1L);
        extrato.setContaCorrente(contaCorrente);
        extrato.setValor(BigDecimal.valueOf(200));
        extrato.setOperacao(Operacao.DEPOSITO);
    }

    @Test
    void testFindAll() {
        when(contaCorrenteRepository.findAll()).thenReturn(Arrays.asList(contaCorrente));
        when(contaCorrenteMapper.toDTO(any(ContaCorrente.class))).thenReturn(contaCorrenteDTO);

        List<ContaCorrenteDTO> result = contaCorrenteService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("12345", result.get(0).getNumero());
    }

    @Test
    void testFindById_Success() {
        when(contaCorrenteRepository.findById(1L)).thenReturn(Optional.of(contaCorrente));
        when(contaCorrenteMapper.toDTO(any(ContaCorrente.class))).thenReturn(contaCorrenteDTO);

        ContaCorrenteDTO result = contaCorrenteService.findById(1L);

        assertNotNull(result);
        assertEquals("12345", result.getNumero());
    }

    @Test
    void testFindById_NotFound() {
        when(contaCorrenteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> contaCorrenteService.findById(1L));
    }

    @Test
    void testSave_Success() {
        when(contaCorrenteMapper.toEntity(any(ContaCorrenteDTO.class))).thenReturn(contaCorrente);
        when(contaCorrenteRepository.save(any(ContaCorrente.class))).thenReturn(contaCorrente);
        when(contaCorrenteMapper.toDTO(any(ContaCorrente.class))).thenReturn(contaCorrenteDTO);

        ContaCorrenteDTO result = contaCorrenteService.save(contaCorrenteDTO);

        assertNotNull(result);
        assertEquals("12345", result.getNumero());
    }

    @Test
    void testSave_CampoObrigatorioException() {
        contaCorrenteDTO.setNumero(null);

        assertThrows(CampoObrigatorioException.class, () -> contaCorrenteService.save(contaCorrenteDTO));
    }

    @Test
    void testDeleteById_Success() {
        when(contaCorrenteRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> contaCorrenteService.deleteById(1L));
        verify(contaCorrenteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(contaCorrenteRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> contaCorrenteService.deleteById(1L));
    }

    @Test
    void testUpdate_Success() {
        when(contaCorrenteRepository.existsById(1L)).thenReturn(true);
        when(contaCorrenteMapper.toEntity(any(ContaCorrenteDTO.class))).thenReturn(contaCorrente);
        when(contaCorrenteRepository.save(any(ContaCorrente.class))).thenReturn(contaCorrente);
        when(contaCorrenteMapper.toDTO(any(ContaCorrente.class))).thenReturn(contaCorrenteDTO);

        ContaCorrenteDTO result = contaCorrenteService.update(1L, contaCorrenteDTO);

        assertNotNull(result);
        assertEquals("12345", result.getNumero());
    }

    @Test
    void testUpdate_NotFound() {
        when(contaCorrenteRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> contaCorrenteService.update(1L, contaCorrenteDTO));
    }

    @Test
    void testDepositar_Success() {
        when(contaCorrenteRepository.findById(1L)).thenReturn(Optional.of(contaCorrente));

        contaCorrenteService.depositar(1L, BigDecimal.valueOf(200));

        assertEquals(BigDecimal.valueOf(1200), contaCorrente.getSaldo());
    }

    @Test
    void testSacar_Success() {
        when(contaCorrenteRepository.findById(1L)).thenReturn(Optional.of(contaCorrente));

        contaCorrenteService.sacar(1L, BigDecimal.valueOf(200));

        assertEquals(BigDecimal.valueOf(800), contaCorrente.getSaldo());
    }

    @Test
    void testSacar_SaldoInsuficiente() {
        when(contaCorrenteRepository.findById(1L)).thenReturn(Optional.of(contaCorrente));

        assertThrows(SaldoInsuficienteException.class, () -> contaCorrenteService.sacar(1L, BigDecimal.valueOf(2000)));
    }

    @Test
    void testTransferir_Success() {
        ContaCorrente contaDestino = new ContaCorrente();
        contaDestino.setId(2L);
        contaDestino.setNumero("67890");
        contaDestino.setSaldo(BigDecimal.valueOf(500));

        when(contaCorrenteRepository.findById(1L)).thenReturn(Optional.of(contaCorrente));
        when(contaCorrenteRepository.findById(2L)).thenReturn(Optional.of(contaDestino));

        contaCorrenteService.transferir(1L, 2L, BigDecimal.valueOf(200));

        assertEquals(BigDecimal.valueOf(800), contaCorrente.getSaldo());
        assertEquals(BigDecimal.valueOf(700), contaDestino.getSaldo());
    }

    @Test
    void testTransferir_SaldoInsuficiente() {
        ContaCorrente contaDestino = new ContaCorrente();
        contaDestino.setId(2L);
        contaDestino.setNumero("67890");
        contaDestino.setSaldo(BigDecimal.valueOf(500));

        when(contaCorrenteRepository.findById(1L)).thenReturn(Optional.of(contaCorrente));
        when(contaCorrenteRepository.findById(2L)).thenReturn(Optional.of(contaDestino));

        assertThrows(SaldoInsuficienteException.class, () -> contaCorrenteService.transferir(1L, 2L, BigDecimal.valueOf(2000)));
    }

    @Test
    void testRecalcularSaldo_Success() {
        when(contaCorrenteRepository.findById(1L)).thenReturn(Optional.of(contaCorrente));
        when(extratoRepository.findByContaCorrenteId(1L)).thenReturn(Arrays.asList(extrato));

        BigDecimal saldoRecalculado = contaCorrenteService.recalcularSaldo(1L);

        assertEquals(BigDecimal.valueOf(1200), saldoRecalculado);
    }

    @Test
    void testRecalcularSaldo_OperacaoDesconhecidaException() {
        extrato.setOperacao(null); // Operação desconhecida

        when(contaCorrenteRepository.findById(1L)).thenReturn(Optional.of(contaCorrente));
        when(extratoRepository.findByContaCorrenteId(1L)).thenReturn(Arrays.asList(extrato));

        assertThrows(OperacaoDesconhecidaException.class, () -> contaCorrenteService.recalcularSaldo(1L));
    }

    @Test
    void testExibirExtrato_Success() {
        when(contaCorrenteRepository.existsById(1L)).thenReturn(true);
        when(extratoRepository.findByContaCorrenteId(1L)).thenReturn(Arrays.asList(extrato));
        when(extratoMapper.toDTO(any(Extrato.class))).thenReturn(new ExtratoDTO());

        List<ExtratoDTO> result = contaCorrenteService.exibirExtrato(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testExibirExtrato_EntidadeNaoEncontradaException() {
        when(contaCorrenteRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> contaCorrenteService.exibirExtrato(1L));
    }
}
