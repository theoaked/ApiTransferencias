package eduardocarvalho.itau.apitransferencia.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data // gera get e set dos parametros
@NoArgsConstructor // gera um construtor sem argumentos
@AllArgsConstructor // gera um construtor com argumentos
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    String nome;
    @NonNull
    @Column(unique = true)
    String conta;
    @NonNull
    Double saldo;
}
