package eduardocarvalho.itau.apitransferencia.repository;

import eduardocarvalho.itau.apitransferencia.dto.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
