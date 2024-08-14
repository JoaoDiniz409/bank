package acc.accenture.bank.controller;

import acc.accenture.bank.dtos.ContaCorrenteDTO;
import acc.accenture.bank.dtos.ExtratoDTO;
import acc.accenture.bank.enums.Operacao;
import acc.accenture.bank.service.ContaCorrenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaCorrenteController {

    @Autowired
    private ContaCorrenteService contaCorrenteService;
    @Autowired
    private ExtratoController extratoController;

    @GetMapping
    public ResponseEntity<List<ContaCorrenteDTO>> getAllContas() {
        return ResponseEntity.ok(contaCorrenteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaCorrenteDTO> getContaById(@PathVariable Long id) {
        return ResponseEntity.ok(contaCorrenteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ContaCorrenteDTO> createConta(@RequestBody ContaCorrenteDTO contaCorrenteDTO) {
        return ResponseEntity.ok(contaCorrenteService.save(contaCorrenteDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaCorrenteDTO> updateConta(@PathVariable Long id, @RequestBody ContaCorrenteDTO contaCorrenteDTO) {
        return ResponseEntity.ok(contaCorrenteService.update(id, contaCorrenteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        contaCorrenteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // usando POST /api/contas/1/deposito?valor=500.00
    @PostMapping("/{id}/deposito")
    public ResponseEntity<Void> depositar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        contaCorrenteService.depositar(id, valor);
        extratoController.createExtrato(new ExtratoDTO(LocalDateTime.now(),Operacao.DEPOSITO,valor,id));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/saque")
    public ResponseEntity<Void> sacar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        contaCorrenteService.sacar(id, valor);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idOrigem}/transferencia/{idDestino}")
    public ResponseEntity<Void> transferir(@PathVariable Long idOrigem, @PathVariable Long idDestino, @RequestParam BigDecimal valor) {
        contaCorrenteService.transferir(idOrigem, idDestino, valor);
        extratoController.createExtrato(new ExtratoDTO(LocalDateTime.now(), Operacao.TRANSFERENCIA,valor,idOrigem,idDestino));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/recalculo-saldo")
    public ResponseEntity<BigDecimal> recalcularSaldo(@PathVariable Long id) {

        return ResponseEntity.ok(contaCorrenteService.recalcularSaldo(id));
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<ExtratoDTO>> exibirExtrato(@PathVariable Long id) {
        List<ExtratoDTO> extrato = contaCorrenteService.exibirExtrato(id);
        return ResponseEntity.ok(extrato);
    }
}
