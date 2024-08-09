package acc.accenture.bank.service;

import acc.accenture.bank.dtos.ClienteDTO;
import acc.accenture.bank.mapper.ClienteMapper;
import acc.accenture.bank.model.Cliente;
import acc.accenture.bank.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO findById(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")); // Exceção customizada recomendada
    }

    public ClienteDTO save(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        return clienteMapper.toDTO(clienteRepository.save(cliente));
    }

    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    public ClienteDTO update(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente.setId(id);
        return clienteMapper.toDTO(clienteRepository.save(cliente));
    }
}
