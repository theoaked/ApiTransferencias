package eduardocarvalho.itau.apitransferencia.integrated;

import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import eduardocarvalho.itau.apitransferencia.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class ClienteIntegrationTests {

    private String generateFullURL(String uri) {
        return "http://localhost:" + 8080 + uri;
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClienteRepository clienteRepository;

    Cliente clienteTeste;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
        clienteTeste = new Cliente();
        clienteTeste.setConta("123");
        clienteTeste.setNome("Edu");
        clienteTeste.setSaldo(1200.00);
    }

    @Test
    void getAllClientes() {
        // Given
        clienteRepository.save(clienteTeste);

        // When
        ResponseEntity<List<Cliente>> response = restTemplate.exchange(
                generateFullURL("/v1/clientes"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Cliente>>() {});

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        Cliente returnedCliente = response.getBody().get(0);
        assertThat(returnedCliente.getNome()).isEqualTo(clienteTeste.getNome());
        assertThat(returnedCliente.getConta()).isEqualTo(clienteTeste.getConta());
        assertThat(returnedCliente.getSaldo()).isEqualTo(clienteTeste.getSaldo());
    }

    @Test
    void getClienteByConta() {
        // Given
        clienteRepository.save(clienteTeste);

        // When
        ResponseEntity<Cliente> response = restTemplate.getForEntity(generateFullURL("/v1/clientes/"+clienteTeste.getConta()), Cliente.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Cliente returnedCliente = response.getBody();
        assert returnedCliente != null;
        assertThat(returnedCliente.getNome()).isEqualTo(clienteTeste.getNome());
        assertThat(returnedCliente.getConta()).isEqualTo(clienteTeste.getConta());
        assertThat(returnedCliente.getSaldo()).isEqualTo(clienteTeste.getSaldo());
    }

    @Test
    void createCliente() {
        // When
        ResponseEntity<Cliente> response = restTemplate.postForEntity(generateFullURL("/v1/clientes?conta=123&nome=Edu&saldo=1200.00"), null, Cliente.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Cliente returnedCliente = response.getBody();
        assert returnedCliente != null;
        assertThat(returnedCliente.getNome()).isEqualTo(clienteTeste.getNome());
        assertThat(returnedCliente.getConta()).isEqualTo(clienteTeste.getConta());
        assertThat(returnedCliente.getSaldo()).isEqualTo(clienteTeste.getSaldo());
    }
}
