package acc.accenture.bank.service;

import acc.accenture.bank.dtos.AgenciaDTO;
import acc.accenture.bank.exception.EntidadeNaoEncontradaException;
import acc.accenture.bank.mapper.AgenciaMapper;
import acc.accenture.bank.model.Agencia;
import acc.accenture.bank.repository.AgenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgenciaService {

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private AgenciaMapper agenciaMapper;

    public List<AgenciaDTO> findAll() {
        return agenciaRepository.findAll().stream()
                .map(agenciaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AgenciaDTO findById(Long id) {
        return agenciaRepository.findById(id)
                .map(agenciaMapper::toDTO)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Agencia"));
    }

    public AgenciaDTO save(AgenciaDTO agenciaDTO) {
        Agencia agencia = agenciaMapper.toEntity(agenciaDTO);
        return agenciaMapper.toDTO(agenciaRepository.save(agencia));
    }

    public void deleteById(Long id) {
        if (!agenciaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Agência");
        }
        agenciaRepository.deleteById(id);
    }

    public AgenciaDTO update(Long id, AgenciaDTO agenciaDTO) {
        if (!agenciaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Agência");
        }
        Agencia agencia = agenciaMapper.toEntity(agenciaDTO);
        agencia.setId(id);
        return agenciaMapper.toDTO(agenciaRepository.save(agencia));
    }
}