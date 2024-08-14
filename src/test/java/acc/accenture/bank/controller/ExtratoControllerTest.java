package acc.accenture.bank.controller;

import acc.accenture.bank.dtos.ExtratoDTO;
import acc.accenture.bank.enums.Operacao;
import acc.accenture.bank.service.ExtratoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(ExtratoController.class)
class ExtratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExtratoService extratoService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllExtratos() throws Exception {
        ExtratoDTO extratoDTO = new ExtratoDTO(LocalDateTime.now(), Operacao.DEPOSITO, BigDecimal.valueOf(500), 1L);
        when(extratoService.findAll()).thenReturn(Collections.singletonList(extratoDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/extratos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Collections.singletonList(extratoDTO))));
    }

    @Test
    void testGetExtratoById() throws Exception {
        ExtratoDTO extratoDTO = new ExtratoDTO(LocalDateTime.now(), Operacao.DEPOSITO, BigDecimal.valueOf(500), 1L);
        when(extratoService.findById(1L)).thenReturn(extratoDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/extratos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(extratoDTO)));
    }

    @Test
    void testGetExtratosByContaCorrenteId() throws Exception {
        ExtratoDTO extratoDTO = new ExtratoDTO(LocalDateTime.now(), Operacao.DEPOSITO, BigDecimal.valueOf(500), 1L);
        when(extratoService.findByContaCorrenteId(1L)).thenReturn(Collections.singletonList(extratoDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/extratos/conta/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Collections.singletonList(extratoDTO))));
    }

    @Test
    void testCreateExtrato() throws Exception {
        ExtratoDTO extratoDTO = new ExtratoDTO(LocalDateTime.now(), Operacao.DEPOSITO, BigDecimal.valueOf(500), 1L);
        when(extratoService.save(any(ExtratoDTO.class))).thenReturn(extratoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/extratos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extratoDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(extratoDTO)));
    }

    @Test
    void testDeleteExtrato() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/extratos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}