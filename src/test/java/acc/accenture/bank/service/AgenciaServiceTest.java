package acc.accenture.bank.service;

import acc.accenture.bank.dtos.AgenciaDTO;
import acc.accenture.bank.exception.CampoObrigatorioException;
import acc.accenture.bank.exception.EntidadeNaoEncontradaException;
import acc.accenture.bank.mapper.AgenciaMapper;
import acc.accenture.bank.model.Agencia;
import acc.accenture.bank.repository.AgenciaRepository;
import acc.accenture.bank.service.AgenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AgenciaServiceTest {

    @InjectMocks
    private AgenciaService agenciaService;

    @Mock
    private AgenciaRepository agenciaRepository;

    @Mock
    private AgenciaMapper agenciaMapper;

    private Agencia agencia;
    private AgenciaDTO agenciaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        agencia = new Agencia();
        agencia.setId(1L);
        agencia.setNome("Agência Central");
        agencia.setEndereco("Rua Principal, 123");
        agencia.setTelefone("123456789");

        agenciaDTO = new AgenciaDTO();
        agenciaDTO.setId(1L);
        agenciaDTO.setNome("Agência Central");
        agenciaDTO.setEndereco("Rua Principal, 123");
        agenciaDTO.setTelefone("123456789");
    }

    @Test
    void testFindAll() {
        when(agenciaRepository.findAll()).thenReturn(Arrays.asList(agencia));
        when(agenciaMapper.toDTO(any(Agencia.class))).thenReturn(agenciaDTO);

        List<AgenciaDTO> result = agenciaService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Agência Central", result.get(0).getNome());
    }

    @Test
    void testFindById_Success() {
        when(agenciaRepository.findById(1L)).thenReturn(Optional.of(agencia));
        when(agenciaMapper.toDTO(any(Agencia.class))).thenReturn(agenciaDTO);

        AgenciaDTO result = agenciaService.findById(1L);

        assertNotNull(result);
        assertEquals("Agência Central", result.getNome());
    }

    @Test
    void testFindById_NotFound() {
        when(agenciaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> agenciaService.findById(1L));
    }

    @Test
    void testSave_Success() {
        when(agenciaMapper.toEntity(any(AgenciaDTO.class))).thenReturn(agencia);
        when(agenciaRepository.save(any(Agencia.class))).thenReturn(agencia);
        when(agenciaMapper.toDTO(any(Agencia.class))).thenReturn(agenciaDTO);

        AgenciaDTO result = agenciaService.save(agenciaDTO);

        assertNotNull(result);
        assertEquals("Agência Central", result.getNome());
    }

    @Test
    void testSave_CampoObrigatorioException() {
        agenciaDTO.setNome(null);

        assertThrows(CampoObrigatorioException.class, () -> agenciaService.save(agenciaDTO));
    }

    @Test
    void testDeleteById_Success() {
        when(agenciaRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> agenciaService.deleteById(1L));
        verify(agenciaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(agenciaRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> agenciaService.deleteById(1L));
    }

    @Test
    void testUpdate_Success() {
        when(agenciaRepository.existsById(1L)).thenReturn(true);
        when(agenciaMapper.toEntity(any(AgenciaDTO.class))).thenReturn(agencia);
        when(agenciaRepository.save(any(Agencia.class))).thenReturn(agencia);
        when(agenciaMapper.toDTO(any(Agencia.class))).thenReturn(agenciaDTO);

        AgenciaDTO result = agenciaService.update(1L, agenciaDTO);

        assertNotNull(result);
        assertEquals("Agência Central", result.getNome());
    }

    @Test
    void testUpdate_NotFound() {
        when(agenciaRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> agenciaService.update(1L, agenciaDTO));
    }
}
