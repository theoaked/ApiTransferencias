package eduardocarvalho.itau.apitransferencia.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data // Gera getters, setters, toString, equals, e hashCode
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com todos os argumentos
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NonNull
    int contaOrigem;

    @NonNull
    int contaDestino;

    @NonNull
    double valor;

    @NonNull
    String status;

    @CreationTimestamp
    private LocalDateTime dataHoraTransacao;
}
