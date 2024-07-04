package eduardocarvalho.itau.apitransferencia.integrated;

import com.fasterxml.jackson.databind.ObjectMapper;
import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import eduardocarvalho.itau.apitransferencia.dto.Transacao;
import eduardocarvalho.itau.apitransferencia.repository.ClienteRepository;
import eduardocarvalho.itau.apitransferencia.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class TransacaoIntegrationTests {

    private String generateFullURL(String uri) {
        return "http://localhost:" + 8080 + uri;
    }

    private String generateURI(String contaOrigem, String contaDestino, Double valor) {
        return "/v1/transacoes?contaOrigem="+contaOrigem+"&contaDestino="+contaDestino+"&valor="+valor;
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    Cliente clienteTeste1;
    Cliente clienteTeste2;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


    @BeforeEach
    void setUp() {
        // limpeza da base
        clienteRepository.deleteAll();
        transacaoRepository.deleteAll();

        // cadastro de massa real de clientes
        clienteTeste1 = new Cliente();
        clienteTeste1.setConta("123");
        clienteTeste1.setNome("Edu");
        clienteTeste1.setSaldo(100.00);
        clienteRepository.save(clienteTeste1);

        clienteTeste2 = new Cliente();
        clienteTeste2.setConta("456");
        clienteTeste2.setNome("Sam");
        clienteTeste2.setSaldo(100.00);
        clienteRepository.save(clienteTeste2);
    }

    // validação em caso de conta origem com saldo insuficiente
    @Test
    void createTransacaoWhenSaldoInsuficiente() {
        // Given
        Double saldoInicialConta1 = clienteTeste1.getSaldo();
        Double saldoInicialConta2 = clienteTeste2.getSaldo();

        // When
        ResponseEntity<Transacao> response = restTemplate.postForEntity(generateFullURL(
                generateURI(clienteTeste1.getConta(), clienteTeste2.getConta(), 900.00)), null, Transacao.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Transacao returnedTransacao = response.getBody();
        assert returnedTransacao != null;
        assertThat(returnedTransacao.getStatus()).isEqualTo("Falha na Transferencia! - Saldo Insuficiente!");
        assertThat(clienteRepository.findByConta(clienteTeste1.getConta()).getSaldo()).isEqualTo(saldoInicialConta1);
        assertThat(clienteRepository.findByConta(clienteTeste2.getConta()).getSaldo()).isEqualTo(saldoInicialConta2);
    }

    // validação em caso de valor de transferencia acima de R$1000,00
    @Test
    void createTransacaoWhenValorAcimaDe1000() {
        // Given
        Double saldoInicialConta1 = clienteTeste1.getSaldo();
        Double saldoInicialConta2 = clienteTeste2.getSaldo();

        // When
        ResponseEntity<Transacao> response = restTemplate.postForEntity(generateFullURL(
                generateURI(clienteTeste1.getConta(), clienteTeste2.getConta(), 1200.00)), null, Transacao.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Transacao returnedTransacao = response.getBody();
        assert returnedTransacao != null;
        assertThat(returnedTransacao.getStatus()).isEqualTo("Falha na Transferencia! - Valor acima de R$1.000,00!");
        assertThat(clienteRepository.findByConta(clienteTeste1.getConta()).getSaldo()).isEqualTo(saldoInicialConta1);
        assertThat(clienteRepository.findByConta(clienteTeste2.getConta()).getSaldo()).isEqualTo(saldoInicialConta2);
    }

    // validacao de transacao em caso de sucesso
    @Test
    void createTransacaoWhenSuccess() {
        // Given
        Double saldoInicialConta1 = clienteTeste1.getSaldo();
        Double saldoInicialConta2 = clienteTeste2.getSaldo();

        // When
        ResponseEntity<Transacao> response = restTemplate.postForEntity(generateFullURL(
                generateURI(clienteTeste1.getConta(), clienteTeste2.getConta(), 50.00)), null, Transacao.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Transacao returnedTransacao = response.getBody();
        assert returnedTransacao != null;
        assertThat(returnedTransacao.getStatus()).isEqualTo("Sucesso!");
        assertThat(clienteRepository.findByConta(clienteTeste1.getConta()).getSaldo()).isEqualTo(saldoInicialConta1-50.00);
        assertThat(clienteRepository.findByConta(clienteTeste2.getConta()).getSaldo()).isEqualTo(saldoInicialConta2+50.00);
    }


    // validacao em caso de conta destino não existente
    @Test
    void createTransacaoWhenContaDestinoNaoExiste() {
        // Given
        Double saldoInicialConta1 = clienteTeste1.getSaldo();

        // When
        ResponseEntity<Transacao> response = restTemplate.postForEntity(generateFullURL(
                generateURI(clienteTeste1.getConta(), "000", 50.00)), null, Transacao.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Transacao returnedTransacao = response.getBody();
        assert returnedTransacao != null;
        assertThat(returnedTransacao.getStatus()).isEqualTo("Falha na Transferencia! - Conta(s) Origem e/ou Destino não existe(m)!");
        assertThat(clienteRepository.findByConta(clienteTeste1.getConta()).getSaldo()).isEqualTo(saldoInicialConta1);
    }

    // validacao em caso de conta destino não existente
    @Test
    void createTransacaoWhenContaOrigemNaoExiste() {
        // Given
        Double saldoInicialConta1 = clienteTeste1.getSaldo();

        // When
        ResponseEntity<Transacao> response = restTemplate.postForEntity(generateFullURL(
                generateURI("000", clienteTeste1.getConta(), 50.00)), null, Transacao.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Transacao returnedTransacao = response.getBody();
        assert returnedTransacao != null;
        assertThat(returnedTransacao.getStatus()).isEqualTo("Falha na Transferencia! - Conta(s) Origem e/ou Destino não existe(m)!");
        assertThat(clienteRepository.findByConta(clienteTeste1.getConta()).getSaldo()).isEqualTo(saldoInicialConta1);
    }

}
