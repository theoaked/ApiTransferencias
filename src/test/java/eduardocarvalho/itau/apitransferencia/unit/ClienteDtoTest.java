package eduardocarvalho.itau.apitransferencia.unit;

import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ClienteDtoTest {
    private Cliente cliente;

    @BeforeEach
    void setUp() {

        cliente = new Cliente(1L, "Alonso", "123", 1000.0);
    }

    @Test
    void testClienteConstructorAndGetters() {
        assertEquals(1L, cliente.getId());
        assertEquals("Alonso", cliente.getNome());
        assertEquals("123", cliente.getConta());
        assertEquals(1000.0, cliente.getSaldo());
    }

    @Test
    void testClienteSetters() {
        cliente.setId(2L);
        cliente.setNome("Alonso");
        cliente.setConta("789");
        cliente.setSaldo(2000.0);

        assertEquals(2L, cliente.getId());
        assertEquals("Alonso", cliente.getNome());
        assertEquals("789", cliente.getConta());
        assertEquals(2000.0, cliente.getSaldo());
    }

    @Test
    void testClienteNoArgsConstructor() {
        Cliente clienteNoArgs = new Cliente();
        clienteNoArgs.setId(3L);
        clienteNoArgs.setNome("Hamilton");
        clienteNoArgs.setConta("456");
        clienteNoArgs.setSaldo(1500.0);

        assertEquals(3L, clienteNoArgs.getId());
        assertEquals("Hamilton", clienteNoArgs.getNome());
        assertEquals("456", clienteNoArgs.getConta());
        assertEquals(1500.0, clienteNoArgs.getSaldo());
    }

    @Test
    void testEqualsAndHashCode() {
        Cliente cliente1 = new Cliente(1L, "Alonso", "123", 1000.0);
        Cliente cliente2 = new Cliente(1L, "Alonso", "123", 1000.0);

        assertEquals(cliente1, cliente2);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }

    @Test
    void testNotEqualsAndHashCode() {
        Cliente cliente1 = new Cliente(1L, "Alonso", "123", 1000.0);
        Cliente cliente2 = new Cliente(2L, "Hamilton", "456", 2000.0);

        assertNotEquals(cliente1, cliente2);
        assertNotEquals(cliente1.hashCode(), cliente2.hashCode());
    }
}
