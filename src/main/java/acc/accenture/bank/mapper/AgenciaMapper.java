package acc.accenture.bank.mapper;

import acc.accenture.bank.dtos.AgenciaDTO;
import acc.accenture.bank.model.Agencia;
import org.springframework.stereotype.Component;

@Component
public class AgenciaMapper {

    public AgenciaDTO toDTO(Agencia agencia) {
        return new AgenciaDTO(
                agencia.getId(),
                agencia.getNome(),
                agencia.getEndereco(),
                agencia.getTelefone()
        );
    }

    public Agencia toEntity(AgenciaDTO agenciaDTO) {
        return new Agencia(
                agenciaDTO.getId(),
                agenciaDTO.getNome(),
                agenciaDTO.getEndereco(),
                agenciaDTO.getTelefone()
        );
    }
}
