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
import java.util.List;

@Entity
@Data // gera get e set dos parametros
@NoArgsConstructor // gera um construtor sem argumentos
@AllArgsConstructor // gera um construtor com argumentos
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    String contaOrigem;

    @NonNull
    String contaDestino;

    @NonNull
    Double valor;

    @NonNull
    String status;

    @NonNull
    @CreationTimestamp
    private LocalDateTime dataHoraTransacao;

    // metodo responsavel por ordenar de forma decrescente as transacoes usando dataHoraTransacao como chave
    public static void ordenarPorDataHoraDecrescente(List<Transacao> transacoes) {
        transacoes.sort((t1, t2) -> t2.getDataHoraTransacao().compareTo(t1.getDataHoraTransacao()));
    }
}
