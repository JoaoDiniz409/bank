package acc.accenture.bank.service;

import acc.accenture.bank.dtos.ExtratoDTO;
import acc.accenture.bank.exception.EntidadeNaoEncontradaException;
import acc.accenture.bank.mapper.ExtratoMapper;
import acc.accenture.bank.model.Extrato;
import acc.accenture.bank.repository.ExtratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtratoService {

    @Autowired
    private ExtratoRepository extratoRepository;

    @Autowired
    private ExtratoMapper extratoMapper;

    public List<ExtratoDTO> findAll() {
        return extratoRepository.findAll().stream()
                .map(extratoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ExtratoDTO findById(Long id) {
        return extratoRepository.findById(id)
                .map(extratoMapper::toDTO)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Extrato"));
    }

    public List<ExtratoDTO> findByContaCorrenteId(Long contaCorrenteId) {
        return extratoRepository.findByContaCorrenteId(contaCorrenteId).stream()
                .map(extratoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ExtratoDTO save(ExtratoDTO extratoDTO) {
        Extrato extrato = extratoMapper.toEntity(extratoDTO);
        return extratoMapper.toDTO(extratoRepository.save(extrato));
    }

    public void deleteById(Long id) {
        if (!extratoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Extrato");
        }
        extratoRepository.deleteById(id);
    }
}
