package acc.accenture.bank.mapper;

import acc.accenture.bank.dtos.ContaCorrenteDTO;
import acc.accenture.bank.model.Agencia;
import acc.accenture.bank.model.ContaCorrente;
import acc.accenture.bank.repository.AgenciaRepository;
import acc.accenture.bank.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContaCorrenteMapper {

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public ContaCorrenteDTO toDTO(ContaCorrente contaCorrente) {
        return new ContaCorrenteDTO(
                contaCorrente.getNumero(),
                contaCorrente.getSaldo(),
                contaCorrente.getAgencia().getId(),
                contaCorrente.getCliente().getId()
        );
    }

    public ContaCorrente toEntity(ContaCorrenteDTO contaCorrenteDTO) {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setNumero(contaCorrenteDTO.getNumero());
        contaCorrente.setSaldo(contaCorrenteDTO.getSaldo());
        Optional<Agencia> agencia = agenciaRepository.findById(contaCorrenteDTO.getAgenciaId());
        System.out.println(agencia);
        Optional.ofNullable(contaCorrenteDTO.getAgenciaId())
                .flatMap(agenciaRepository::findById)
                .ifPresent(contaCorrente::setAgencia);

        Optional.ofNullable(contaCorrenteDTO.getClienteId())
                .flatMap(clienteRepository::findById)
                .ifPresent(contaCorrente::setCliente);

        return contaCorrente;
    }
}
