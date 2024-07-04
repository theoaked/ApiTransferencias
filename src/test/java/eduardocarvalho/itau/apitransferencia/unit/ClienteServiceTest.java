package eduardocarvalho.itau.apitransferencia.unit;
import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import eduardocarvalho.itau.apitransferencia.repository.ClienteRepository;
import eduardocarvalho.itau.apitransferencia.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllClientes() {
        List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.getAllClientes();

        assertEquals(2, result.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testFindByConta_ContaExiste() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findByConta("123")).thenReturn(cliente);

        Cliente result = clienteService.findByConta("123");

        assertEquals(cliente, result);
        verify(clienteRepository, times(1)).findByConta("123");
    }

    @Test
    void testFindByConta_ContaNaoExiste() {
        when(clienteRepository.findByConta("123")).thenReturn(null);

        Cliente result = clienteService.findByConta("123");

        assertNull(result);
        verify(clienteRepository, times(1)).findByConta("123");
    }

    @Test
    void testCreateCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Edu");
        cliente.setSaldo(100.00);
        cliente.setConta("123");
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.createCliente(cliente);

        assertEquals(cliente, result);
        verify(clienteRepository, times(1)).save(cliente);
    }
}