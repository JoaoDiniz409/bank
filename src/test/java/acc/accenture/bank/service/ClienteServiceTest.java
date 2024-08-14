package acc.accenture.bank.service;

import acc.accenture.bank.dtos.ClienteDTO;
import acc.accenture.bank.exception.CampoObrigatorioException;
import acc.accenture.bank.exception.EntidadeNaoEncontradaException;
import acc.accenture.bank.exception.TamanhoMaximoExcedidoException;
import acc.accenture.bank.mapper.ClienteMapper;
import acc.accenture.bank.model.Cliente;
import acc.accenture.bank.repository.ClienteRepository;
import acc.accenture.bank.service.ClienteService;
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

class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("987654321");

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("João Silva");
        clienteDTO.setCpf("123.456.789-00");
        clienteDTO.setTelefone("987654321");
    }

    @Test
    void testFindAll() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));
        when(clienteMapper.toDTO(any(Cliente.class))).thenReturn(clienteDTO);

        List<ClienteDTO> result = clienteService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("João Silva", result.get(0).getNome());
    }

    @Test
    void testFindById_Success() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDTO(any(Cliente.class))).thenReturn(clienteDTO);

        ClienteDTO result = clienteService.findById(1L);

        assertNotNull(result);
        assertEquals("João Silva", result.getNome());
    }

    @Test
    void testFindById_NotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> clienteService.findById(1L));
    }

    @Test
    void testSave_Success() {
        when(clienteMapper.toEntity(any(ClienteDTO.class))).thenReturn(cliente);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toDTO(any(Cliente.class))).thenReturn(clienteDTO);

        ClienteDTO result = clienteService.save(clienteDTO);

        assertNotNull(result);
        assertEquals("João Silva", result.getNome());
    }

    @Test
    void testSave_CampoObrigatorioException() {
        clienteDTO.setNome(null);

        assertThrows(CampoObrigatorioException.class, () -> clienteService.save(clienteDTO));
    }

    @Test
    void testSave_TamanhoMaximoExcedidoException() {
        clienteDTO.setCpf("123.456.789-000000"); // CPF com mais de 14 caracteres

        assertThrows(TamanhoMaximoExcedidoException.class, () -> clienteService.save(clienteDTO));
    }

    @Test
    void testDeleteById_Success() {
        when(clienteRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> clienteService.deleteById(1L));
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> clienteService.deleteById(1L));
    }

    @Test
    void testUpdate_Success() {
        when(clienteRepository.existsById(1L)).thenReturn(true);
        when(clienteMapper.toEntity(any(ClienteDTO.class))).thenReturn(cliente);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toDTO(any(Cliente.class))).thenReturn(clienteDTO);

        ClienteDTO result = clienteService.update(1L, clienteDTO);

        assertNotNull(result);
        assertEquals("João Silva", result.getNome());
    }

    @Test
    void testUpdate_NotFound() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> clienteService.update(1L, clienteDTO));
    }
}
