package acc.accenture.bank.controller;

import acc.accenture.bank.dtos.AgenciaDTO;
import acc.accenture.bank.service.AgenciaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AgenciaController.class)
class AgenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgenciaService agenciaService;

    @Test
    void getAllAgencias_ShouldReturnListOfAgencias() throws Exception {
        AgenciaDTO agenciaDTO = new AgenciaDTO(1L, "Agência 1", "Rua 123", "123456789");

        Mockito.when(agenciaService.findAll()).thenReturn(Arrays.asList(agenciaDTO));

        mockMvc.perform(get("/api/agencias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is("Agência 1")));
    }

    @Test
    void getAgenciaById_ShouldReturnAgenciaDTO_WhenAgenciaExists() throws Exception {
        AgenciaDTO agenciaDTO = new AgenciaDTO(1L, "Agência 1", "Rua 123", "123456789");

        Mockito.when(agenciaService.findById(anyLong())).thenReturn(agenciaDTO);

        mockMvc.perform(get("/api/agencias/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Agência 1")));
    }

    @Test
    void createAgencia_ShouldReturnCreatedAgenciaDTO() throws Exception {
        AgenciaDTO agenciaDTO = new AgenciaDTO(1L, "Agência 1", "Rua 123", "123456789");

        Mockito.when(agenciaService.save(any(AgenciaDTO.class))).thenReturn(agenciaDTO);

        mockMvc.perform(post("/api/agencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nome\": \"Agência 1\", \"endereco\": \"Rua 123\", \"telefone\": \"123456789\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Agência 1")));
    }

    @Test
    void updateAgencia_ShouldReturnUpdatedAgenciaDTO_WhenAgenciaExists() throws Exception {
        AgenciaDTO agenciaDTO = new AgenciaDTO(1L, "Agência Atualizada", "Rua 456", "987654321");

        Mockito.when(agenciaService.update(anyLong(), any(AgenciaDTO.class))).thenReturn(agenciaDTO);

        mockMvc.perform(put("/api/agencias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nome\": \"Agência Atualizada\", \"endereco\": \"Rua 456\", \"telefone\": \"987654321\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Agência Atualizada")))
                .andExpect(jsonPath("$.endereco", is("Rua 456")))
                .andExpect(jsonPath("$.telefone", is("987654321")));
    }

    @Test
    void deleteAgencia_ShouldReturnNoContent_WhenAgenciaExists() throws Exception {
        Mockito.doNothing().when(agenciaService).deleteById(anyLong());

        mockMvc.perform(delete("/api/agencias/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
