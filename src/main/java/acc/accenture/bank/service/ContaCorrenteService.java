package acc.accenture.bank.service;

import acc.accenture.bank.dtos.ContaCorrenteDTO;
import acc.accenture.bank.dtos.ExtratoDTO;
import acc.accenture.bank.mapper.ContaCorrenteMapper;
import acc.accenture.bank.mapper.ExtratoMapper;
import acc.accenture.bank.model.ContaCorrente;
import acc.accenture.bank.model.Extrato;
import acc.accenture.bank.repository.ContaCorrenteRepository;
import acc.accenture.bank.repository.ExtratoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaCorrenteService {

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    private ContaCorrenteMapper contaCorrenteMapper;

    @Autowired
    private ExtratoRepository extratoRepository;

    @Autowired
    private ExtratoMapper extratoMapper;

    public List<ContaCorrenteDTO> findAll() {
        return contaCorrenteRepository.findAll().stream()
                .map(contaCorrenteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContaCorrenteDTO findById(Long id) {
        return contaCorrenteRepository.findById(id)
                .map(contaCorrenteMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Conta Corrente não encontrada")); // Exceção customizada recomendada
    }

    public ContaCorrenteDTO save(ContaCorrenteDTO contaCorrenteDTO) {
        ContaCorrente contaCorrente = contaCorrenteMapper.toEntity(contaCorrenteDTO);
        return contaCorrenteMapper.toDTO(contaCorrenteRepository.save(contaCorrente));
    }

    public void deleteById(Long id) {
        contaCorrenteRepository.deleteById(id);
    }

    public ContaCorrenteDTO update(Long id, ContaCorrenteDTO contaCorrenteDTO) {
        ContaCorrente contaCorrente = contaCorrenteMapper.toEntity(contaCorrenteDTO);
        contaCorrente.setId(id);
        return contaCorrenteMapper.toDTO(contaCorrenteRepository.save(contaCorrente));
    }

    @Transactional
    public void depositar(Long id, BigDecimal valor) {
        ContaCorrente contaCorrente = contaCorrenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta Corrente não encontrada"));
        contaCorrente.setSaldo(contaCorrente.getSaldo().add(valor));
        contaCorrenteRepository.save(contaCorrente);
    }

    @Transactional
    public void sacar(Long id, BigDecimal valor) {
        ContaCorrente contaCorrente = contaCorrenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta Corrente não encontrada"));
        if (contaCorrente.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente"); // Exceção customizada recomendada
        }
        contaCorrente.setSaldo(contaCorrente.getSaldo().subtract(valor));
        contaCorrenteRepository.save(contaCorrente);
    }

    @Transactional
    public void transferir(Long idOrigem, Long idDestino, BigDecimal valor) {
        ContaCorrente contaOrigem = contaCorrenteRepository.findById(idOrigem)
                .orElseThrow(() -> new RuntimeException("Conta Corrente de origem não encontrada"));

        ContaCorrente contaDestino = contaCorrenteRepository.findById(idDestino)
                .orElseThrow(() -> new RuntimeException("Conta Corrente de destino não encontrada")); // Exceção customizada

        if (contaOrigem.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        contaCorrenteRepository.save(contaOrigem);
        contaCorrenteRepository.save(contaDestino);
    }

    public BigDecimal recalcularSaldo(Long id) {
        ContaCorrente contaCorrente = contaCorrenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta Corrente não encontrada"));

        List<Extrato> extratos = extratoRepository.findByContaCorrenteId(id);
        BigDecimal saldoRecalculado = BigDecimal.ZERO;

        // Iterar sobre as transações e ajustar o saldo
        for (Extrato extrato : extratos) {
            switch (extrato.getOperacao()) {
                case DEPOSITO:
                    saldoRecalculado = saldoRecalculado.add(extrato.getValor());
                    break;
                case SAQUE:
                case TRANSFERENCIA:
                    saldoRecalculado = saldoRecalculado.subtract(extrato.getValor());
                    break;
                default:
                    throw new RuntimeException("Operação desconhecida: " + extrato.getOperacao());
            }
        }
        contaCorrente.setSaldo(saldoRecalculado);
        contaCorrenteRepository.save(contaCorrente);

        return saldoRecalculado;
    }

    public List<ExtratoDTO> exibirExtrato(Long contaCorrenteId) {
        ContaCorrente contaCorrente = contaCorrenteRepository.findById(contaCorrenteId)
                .orElseThrow(() -> new RuntimeException("Conta Corrente não encontrada"));

        // Recuperar todas as entradas de extrato para a conta corrente especificada
        List<Extrato> extratos = extratoRepository.findByContaCorrenteId(contaCorrenteId);

        // Mapear as entradas do extrato para DTOs
        return extratos.stream()
                .map(extratoMapper::toDTO)
                .collect(Collectors.toList());
    }

}
