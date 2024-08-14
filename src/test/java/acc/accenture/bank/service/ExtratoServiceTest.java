package acc.accenture.bank.service;

import acc.accenture.bank.dtos.ExtratoDTO;
import acc.accenture.bank.enums.Operacao;
import acc.accenture.bank.exception.EntidadeNaoEncontradaException;
import acc.accenture.bank.mapper.ExtratoMapper;
import acc.accenture.bank.model.Extrato;
import acc.accenture.bank.repository.ExtratoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExtratoServiceTest {

    @InjectMocks
    private ExtratoService extratoService;

    @Mock
    private ExtratoRepository extratoRepository;

    @Mock
    private ExtratoMapper extratoMapper;

    private Extrato extrato;
    private ExtratoDTO extratoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        extrato = new Extrato(1L, LocalDateTime.now(), Operacao.DEPOSITO, BigDecimal.valueOf(100.00), null, null);
        extratoDTO = new ExtratoDTO(LocalDateTime.now(), Operacao.DEPOSITO, BigDecimal.valueOf(100.00), 1L, null);
    }

    @Test
    void findAll_ShouldReturnListOfExtratoDTOs() {
        when(extratoRepository.findAll()).thenReturn(Arrays.asList(extrato));
        when(extratoMapper.toDTO(any(Extrato.class))).thenReturn(extratoDTO);

        List<ExtratoDTO> result = extratoService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(extratoRepository, times(1)).findAll();
        verify(extratoMapper, times(1)).toDTO(any(Extrato.class));
    }

    @Test
    void findById_ShouldReturnExtratoDTO_WhenExtratoExists() {
        when(extratoRepository.findById(anyLong())).thenReturn(Optional.of(extrato));
        when(extratoMapper.toDTO(any(Extrato.class))).thenReturn(extratoDTO);

        ExtratoDTO result = extratoService.findById(1L);

        assertNotNull(result);
        assertEquals(extratoDTO, result);
        verify(extratoRepository, times(1)).findById(1L);
        verify(extratoMapper, times(1)).toDTO(any(Extrato.class));
    }

    @Test
    void findById_ShouldThrowException_WhenExtratoDoesNotExist() {
        when(extratoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> extratoService.findById(1L));
        verify(extratoRepository, times(1)).findById(1L);
        verify(extratoMapper, times(0)).toDTO(any(Extrato.class));
    }

    @Test
    void findByContaCorrenteId_ShouldReturnListOfExtratoDTOs() {
        when(extratoRepository.findByContaCorrenteId(anyLong())).thenReturn(Arrays.asList(extrato));
        when(extratoMapper.toDTO(any(Extrato.class))).thenReturn(extratoDTO);

        List<ExtratoDTO> result = extratoService.findByContaCorrenteId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(extratoRepository, times(1)).findByContaCorrenteId(1L);
        verify(extratoMapper, times(1)).toDTO(any(Extrato.class));
    }

    @Test
    void save_ShouldReturnSavedExtratoDTO() {
        when(extratoMapper.toEntity(any(ExtratoDTO.class))).thenReturn(extrato);
        when(extratoRepository.save(any(Extrato.class))).thenReturn(extrato);
        when(extratoMapper.toDTO(any(Extrato.class))).thenReturn(extratoDTO);

        ExtratoDTO result = extratoService.save(extratoDTO);

        assertNotNull(result);
        assertEquals(extratoDTO, result);
        verify(extratoMapper, times(1)).toEntity(any(ExtratoDTO.class));
        verify(extratoRepository, times(1)).save(any(Extrato.class));
        verify(extratoMapper, times(1)).toDTO(any(Extrato.class));
    }

    @Test
    void deleteById_ShouldDeleteExtrato_WhenExists() {
        when(extratoRepository.existsById(anyLong())).thenReturn(true);

        extratoService.deleteById(1L);

        verify(extratoRepository, times(1)).existsById(1L);
        verify(extratoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ShouldThrowException_WhenExtratoDoesNotExist() {
        when(extratoRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> extratoService.deleteById(1L));
        verify(extratoRepository, times(1)).existsById(1L);
        verify(extratoRepository, times(0)).deleteById(anyLong());
    }
}