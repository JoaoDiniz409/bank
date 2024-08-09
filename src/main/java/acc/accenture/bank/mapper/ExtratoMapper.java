package acc.accenture.bank.mapper;

import acc.accenture.bank.dtos.ExtratoDTO;
import acc.accenture.bank.model.Extrato;
import acc.accenture.bank.repository.ContaCorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExtratoMapper {

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    public ExtratoDTO toDTO(Extrato extrato) {
        return new ExtratoDTO(
                extrato.getId(),
                extrato.getDataHoraMovimento(),
                extrato.getOperacao(),
                extrato.getValor(),
                extrato.getContaCorrente().getId(),
                extrato.getContaDestino() != null ? extrato.getContaDestino().getId() : null
        );
    }

    public Extrato toEntity(ExtratoDTO extratoDTO) {
        Extrato extrato = new Extrato();
        extrato.setId(extratoDTO.getId());
        extrato.setDataHoraMovimento(extratoDTO.getDataHoraMovimento());
        extrato.setOperacao(extratoDTO.getOperacao());
        extrato.setValor(extratoDTO.getValor());

        Optional.ofNullable(extratoDTO.getContaCorrenteId())
                .flatMap(contaCorrenteRepository::findById)
                .ifPresent(extrato::setContaCorrente);

        Optional.ofNullable(extratoDTO.getContaDestinoId())
                .flatMap(contaCorrenteRepository::findById)
                .ifPresent(extrato::setContaDestino);

        return extrato;
    }
}
