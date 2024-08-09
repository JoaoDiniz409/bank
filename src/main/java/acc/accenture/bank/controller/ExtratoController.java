package acc.accenture.bank.controller;

import acc.accenture.bank.dtos.ExtratoDTO;
import acc.accenture.bank.service.ExtratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/extratos")
public class ExtratoController {

    @Autowired
    private ExtratoService extratoService;

    @GetMapping
    public ResponseEntity<List<ExtratoDTO>> getAllExtratos() {
        return ResponseEntity.ok(extratoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtratoDTO> getExtratoById(@PathVariable Long id) {
        return ResponseEntity.ok(extratoService.findById(id));
    }

    @GetMapping("/conta/{contaCorrenteId}")
    public ResponseEntity<List<ExtratoDTO>> getExtratosByContaCorrenteId(@PathVariable Long contaCorrenteId) {
        return ResponseEntity.ok(extratoService.findByContaCorrenteId(contaCorrenteId));
    }

    @PostMapping
    public ResponseEntity<ExtratoDTO> createExtrato(@RequestBody ExtratoDTO extratoDTO) {
        return ResponseEntity.ok(extratoService.save(extratoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExtrato(@PathVariable Long id) {
        extratoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
