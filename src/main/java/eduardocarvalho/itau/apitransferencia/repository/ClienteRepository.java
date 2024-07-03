package eduardocarvalho.itau.apitransferencia.repository;

import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByConta(String conta);
}
