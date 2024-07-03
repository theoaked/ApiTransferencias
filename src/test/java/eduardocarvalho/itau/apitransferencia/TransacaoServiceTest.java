package eduardocarvalho.itau.apitransferencia;

import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import eduardocarvalho.itau.apitransferencia.dto.Transacao;
import eduardocarvalho.itau.apitransferencia.repository.ClienteRepository;
import eduardocarvalho.itau.apitransferencia.repository.TransacaoRepository;
import eduardocarvalho.itau.apitransferencia.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransacaoServiceTest {
    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTransacoes() {
        List<Transacao> transacoes = Arrays.asList(new Transacao(), new Transacao());
        when(transacaoRepository.findAll()).thenReturn(transacoes);

        List<Transacao> result = transacaoService.getAllTransacoes();

        assertEquals(2, result.size());
        verify(transacaoRepository, times(1)).findAll();
    }

    @Test
    void testCreateTransacao_ContaOrigemNaoExiste() {
//        Cliente clienteOrigem = new Cliente();
//        clienteOrigem.setNome("Verstappen");
//        clienteOrigem.setConta("123");
//        clienteOrigem.setSaldo(250.00);
        when(clienteRepository.findByConta("123")).thenReturn(null);

        Transacao result = transacaoService.createTransacao("123", "456", 500);

        assertEquals("Falha na Transferencia! - Conta(s) Origem e/ou Destino não existe(m)!", result.getStatus());
        verify(transacaoRepository, times(1)).save(result);
    }

    @Test
    void testCreateTransacao_ContaDestinoNaoExiste() {
        when(clienteRepository.findByConta("123")).thenReturn(new Cliente());
        when(clienteRepository.findByConta("456")).thenReturn(null);

        Transacao result = transacaoService.createTransacao("123", "456", 500);

        assertEquals("Falha na Transferencia! - Conta(s) Origem e/ou Destino não existe(m)!", result.getStatus());
        verify(transacaoRepository, times(1)).save(result);
    }

    @Test
    void testCreateTransacao_ValorAcimaDoLimite() {
        Cliente clienteOrigem = new Cliente();
        Cliente clienteDestino = new Cliente();
        when(clienteRepository.findByConta("123")).thenReturn(clienteOrigem);
        when(clienteRepository.findByConta("456")).thenReturn(clienteDestino);

        Transacao result = transacaoService.createTransacao("123", "456", 1500);

        assertEquals("Falha na Transferencia! - Valor acima de R$1.000,00!", result.getStatus());
        verify(transacaoRepository, times(1)).save(result);
    }

    @Test
    void testCreateTransacao_SaldoInsuficiente() {
        Cliente clienteOrigem = new Cliente();
        clienteOrigem.setSaldo(500.00);
        Cliente clienteDestino = new Cliente();
        when(clienteRepository.findByConta("123")).thenReturn(clienteOrigem);
        when(clienteRepository.findByConta("456")).thenReturn(clienteDestino);

        Transacao result = transacaoService.createTransacao("123", "456", 600);

        assertEquals("Falha na Transferencia! - Saldo Insuficiente!", result.getStatus());
        verify(transacaoRepository, times(1)).save(result);
    }

    @Test
    void testCreateTransacao_Sucesso() {
        Cliente clienteOrigem = new Cliente();
        clienteOrigem.setSaldo(1500.00);
        Cliente clienteDestino = new Cliente();
        clienteDestino.setSaldo(0.00);
        when(clienteRepository.findByConta("123")).thenReturn(clienteOrigem);
        when(clienteRepository.findByConta("456")).thenReturn(clienteDestino);

        Transacao result = transacaoService.createTransacao("123", "456", 500.00);

        assertEquals("Sucesso!", result.getStatus());
        assertEquals(1000.00, clienteOrigem.getSaldo());
        assertEquals(500.00, clienteDestino.getSaldo());
        verify(clienteRepository, times(1)).save(clienteOrigem);
        verify(clienteRepository, times(1)).save(clienteDestino);
        verify(transacaoRepository, times(1)).save(result);
    }

    @Test
    void testGetAllTransacoesByConta() {
        Transacao transacao1 = new Transacao();
        transacao1.setId(1L);
        transacao1.setStatus("Sucesso!");
        transacao1.setContaOrigem("000");
        transacao1.setContaDestino("123");
        transacao1.setDataHoraTransacao(LocalDateTime.now().minusDays(1));

        Transacao transacao2 = new Transacao();
        transacao2.setId(2L);
        transacao2.setStatus("Sucesso!");
        transacao2.setContaOrigem("123");
        transacao2.setContaDestino("000");
        transacao2.setDataHoraTransacao(LocalDateTime.now().minusDays(2));

        Transacao transacao3 = new Transacao();
        transacao3.setId(3L);
        transacao3.setStatus("Sucesso!");
        transacao3.setContaOrigem("456");
        transacao3.setContaDestino("789");
        transacao3.setDataHoraTransacao(LocalDateTime.now().minusDays(3));

        List<Transacao> transacoes = Arrays.asList(transacao1, transacao2, transacao3);
        when(transacaoRepository.findAll()).thenReturn(transacoes);

        List<Transacao> result = transacaoService.getAllTransacoesByConta("123");

        assertEquals(2, result.size());
        assertEquals(transacao1, result.get(0));
        assertEquals(transacao2, result.get(1));
        verify(transacaoRepository, times(1)).findAll();
    }
}
