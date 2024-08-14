package acc.accenture.bank.controller;

import acc.accenture.bank.dtos.ClienteDTO;
import acc.accenture.bank.exception.EntidadeNaoEncontradaException;
import acc.accenture.bank.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void getAllClientes_ShouldReturnListOfClienteDTOs() throws Exception {
        List<ClienteDTO> clientes = Arrays.asList(
                new ClienteDTO(1L, "Cliente 1", "123.456.789-00", "123456789"),
                new ClienteDTO(2L, "Cliente 2", "987.654.321-00", "987654321")
        );

        Mockito.when(clienteService.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Cliente 1")))
                .andExpect(jsonPath("$[1].nome", is("Cliente 2")));
    }

    @Test
    void getClienteById_ShouldReturnClienteDTO_WhenClienteExists() throws Exception {
        ClienteDTO cliente = new ClienteDTO(1L, "Cliente 1", "123.456.789-00", "123456789");

        Mockito.when(clienteService.findById(anyLong())).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Cliente 1")))
                .andExpect(jsonPath("$.cpf", is("123.456.789-00")))
                .andExpect(jsonPath("$.telefone", is("123456789")));
    }

    @Test
    void getClienteById_ShouldReturnNotFound_WhenClienteDoesNotExist() throws Exception {
        Mockito.when(clienteService.findById(anyLong())).thenThrow(new EntidadeNaoEncontradaException("Cliente não encontrado"));;

        mockMvc.perform(get("/api/clientes/500000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCliente_ShouldReturnCreatedClienteDTO() throws Exception {
        ClienteDTO cliente = new ClienteDTO(1L, "Cliente 1", "123.456.789-00", "123456789");

        Mockito.when(clienteService.save(any(ClienteDTO.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nome\": \"Cliente 1\", \"cpf\": \"123.456.789-00\", \"telefone\": \"123456789\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Cliente 1")))
                .andExpect(jsonPath("$.cpf", is("123.456.789-00")))
                .andExpect(jsonPath("$.telefone", is("123456789")));
    }

    @Test
    void updateCliente_ShouldReturnUpdatedClienteDTO_WhenClienteExists() throws Exception {
        ClienteDTO cliente = new ClienteDTO(1L, "Cliente Atualizado", "123.456.789-00", "987654321");

        Mockito.when(clienteService.update(anyLong(), any(ClienteDTO.class))).thenReturn(cliente);

        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nome\": \"Cliente Atualizado\", \"cpf\": \"123.456.789-00\", \"telefone\": \"987654321\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Cliente Atualizado")))
                .andExpect(jsonPath("$.cpf", is("123.456.789-00")))
                .andExpect(jsonPath("$.telefone", is("987654321")));
    }

    @Test
    void deleteCliente_ShouldReturnNoContent_WhenClienteIsDeleted() throws Exception {
        mockMvc.perform(delete("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(clienteService, Mockito.times(1)).deleteById(1L);
    }

}
