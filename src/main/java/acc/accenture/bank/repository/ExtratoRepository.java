package acc.accenture.bank.repository;

import acc.accenture.bank.model.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {
    List<Extrato> findByContaCorrenteId(Long contaCorrenteId);
}
