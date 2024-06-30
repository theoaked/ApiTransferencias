package eduardocarvalho.itau.apitransferencia.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@Data // Gera getters, setters, toString, equals, e hashCode
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com todos os argumentos
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String nome;
    int conta;
    double saldo;
}
