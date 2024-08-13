package acc.accenture.bank.controller;

import acc.accenture.bank.dtos.ContaCorrenteDTO;
import acc.accenture.bank.dtos.ExtratoDTO;
import acc.accenture.bank.enums.Operacao;
import acc.accenture.bank.service.ContaCorrenteService;
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

@WebMvcTest(ContaCorrenteController.class)
class ContaCorrenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaCorrenteService contaCorrenteService;

    @MockBean
    private ExtratoController extratoController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllContas() throws Exception {
        ContaCorrenteDTO contaCorrenteDTO = new ContaCorrenteDTO("12345", BigDecimal.valueOf(1000), 1L, 1L);
        when(contaCorrenteService.findAll()).thenReturn(Collections.singletonList(contaCorrenteDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Collections.singletonList(contaCorrenteDTO))));
    }

    @Test
    void testGetContaById() throws Exception {
        ContaCorrenteDTO contaCorrenteDTO = new ContaCorrenteDTO("12345", BigDecimal.valueOf(1000), 1L, 1L);
        when(contaCorrenteService.findById(1L)).thenReturn(contaCorrenteDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(contaCorrenteDTO)));
    }

    @Test
    void testCreateConta() throws Exception {
        ContaCorrenteDTO contaCorrenteDTO = new ContaCorrenteDTO("12345", BigDecimal.valueOf(1000), 1L, 1L);
        when(contaCorrenteService.save(any(ContaCorrenteDTO.class))).thenReturn(contaCorrenteDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contaCorrenteDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(contaCorrenteDTO)));
    }

    @Test
    void testUpdateConta() throws Exception {
        ContaCorrenteDTO contaCorrenteDTO = new ContaCorrenteDTO("12345", BigDecimal.valueOf(1000), 1L, 1L);
        when(contaCorrenteService.update(anyLong(), any(ContaCorrenteDTO.class))).thenReturn(contaCorrenteDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/contas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contaCorrenteDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(contaCorrenteDTO)));
    }

    @Test
    void testDeleteConta() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/contas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDepositar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/contas/1/deposito")
                        .param("valor", "500.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testSacar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/contas/1/saque")
                        .param("valor", "200.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testTransferir() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/contas/1/transferencia/2")
                        .param("valor", "300.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRecalcularSaldo() throws Exception {
        when(contaCorrenteService.recalcularSaldo(anyLong())).thenReturn(BigDecimal.valueOf(1000));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contas/1/recalculo-saldo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1000"));
    }

    @Test
    void testExibirExtrato() throws Exception {
        ExtratoDTO extratoDTO = new ExtratoDTO(LocalDateTime.now(), Operacao.DEPOSITO, BigDecimal.valueOf(500), 1L);
        when(contaCorrenteService.exibirExtrato(anyLong())).thenReturn(Collections.singletonList(extratoDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contas/1/extrato")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Collections.singletonList(extratoDTO))));
    }
}