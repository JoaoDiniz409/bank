package acc.accenture.bank.controller;

import acc.accenture.bank.dtos.AgenciaDTO;
import acc.accenture.bank.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agencias")
public class AgenciaController {

    @Autowired
    private AgenciaService agenciaService;

    @GetMapping
    public ResponseEntity<List<AgenciaDTO>> getAllAgencias() {
        return ResponseEntity.ok(agenciaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenciaDTO> getAgenciaById(@PathVariable Long id) {
        return ResponseEntity.ok(agenciaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AgenciaDTO> createAgencia(@RequestBody AgenciaDTO agenciaDTO) {
        return ResponseEntity.ok(agenciaService.save(agenciaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgenciaDTO> updateAgencia(@PathVariable Long id, @RequestBody AgenciaDTO agenciaDTO) {
        return ResponseEntity.ok(agenciaService.update(id, agenciaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgencia(@PathVariable Long id) {
        agenciaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
