package acc.accenture.bank.service;

import acc.accenture.bank.dtos.ClienteDTO;
import acc.accenture.bank.exception.CampoObrigatorioException;
import acc.accenture.bank.exception.EntidadeNaoEncontradaException;
import acc.accenture.bank.exception.TamanhoMaximoExcedidoException;
import acc.accenture.bank.mapper.ClienteMapper;
import acc.accenture.bank.model.Cliente;
import acc.accenture.bank.repository.ClienteRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado"));
        return clienteMapper.toDTO(cliente);
    }

    public ClienteDTO save(ClienteDTO clienteDTO) {
        validarCamposObrigatorios(clienteDTO);
        validarCPF(clienteDTO.getCpf());
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        return clienteMapper.toDTO(clienteRepository.save(cliente));
    }

    public void deleteById(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Cliente");
        }
        clienteRepository.deleteById(id);
    }

    public ClienteDTO update(Long id, ClienteDTO clienteDTO) {
        if (!clienteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Cliente");
        }
        validarCamposObrigatorios(clienteDTO);
        validarCPF(clienteDTO.getCpf());
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente.setId(id);
        return clienteMapper.toDTO(clienteRepository.save(cliente));
    }

    private void validarCamposObrigatorios(ClienteDTO clienteDTO) {
        if (clienteDTO.getNome() == null || clienteDTO.getNome().trim().isEmpty()) {
            throw new CampoObrigatorioException("Nome do cliente");
        }
        if (clienteDTO.getTelefone() == null || clienteDTO.getTelefone().trim().isEmpty()) {
            throw new CampoObrigatorioException("Telefone do cliente");
        }
    }

    private void validarCPF(String cpf) {
        // Remover caracteres não numéricos (separadores e pontos)
        String cpfNumeros = cpf.replaceAll("\\D", "");

        // Verificar se tem mais que 14 caracteres
        if (cpf.length() > 14) {
            throw new TamanhoMaximoExcedidoException("CPF", 14);
        }
    }

}
