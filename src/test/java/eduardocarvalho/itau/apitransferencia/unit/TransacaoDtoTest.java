package eduardocarvalho.itau.apitransferencia.unit;

import eduardocarvalho.itau.apitransferencia.dto.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransacaoDtoTest {
    private Transacao transacao1;
    private Transacao transacao2;
    private Transacao transacao3;

    @BeforeEach
    void setUp() {
        transacao1 = new Transacao(1L, "123", "456", 100.0, "Sucesso", LocalDateTime.now().minusDays(1));
        transacao2 = new Transacao(2L, "123", "789", 200.0, "Sucesso", LocalDateTime.now().minusDays(2));
        transacao3 = new Transacao(3L, "456", "789", 300.0, "Sucesso", LocalDateTime.now().minusDays(3));
    }

    @Test
    void testOrdenarPorDataHoraDecrescente() {
        List<Transacao> transacoes = Arrays.asList(transacao1, transacao2, transacao3);
        Transacao.ordenarPorDataHoraDecrescente(transacoes);

        assertEquals(transacao1, transacoes.get(0));
        assertEquals(transacao2, transacoes.get(1));
        assertEquals(transacao3, transacoes.get(2));
    }

    @Test
    void testTransacaoConstructorAndGetters() {
        Transacao transacao = new Transacao(1L, "123", "456", 100.0, "Sucesso", LocalDateTime.now());

        assertEquals(1L, transacao.getId());
        assertEquals("123", transacao.getContaOrigem());
        assertEquals("456", transacao.getContaDestino());
        assertEquals(100.0, transacao.getValor());
        assertEquals("Sucesso", transacao.getStatus());
    }

    @Test
    void testTransacaoSetters() {
        Transacao transacao = new Transacao();
        transacao.setId(1L);
        transacao.setContaOrigem("123");
        transacao.setContaDestino("456");
        transacao.setValor(100.0);
        transacao.setStatus("Sucesso");
        LocalDateTime now = LocalDateTime.now();
        transacao.setDataHoraTransacao(now);

        assertEquals(1L, transacao.getId());
        assertEquals("123", transacao.getContaOrigem());
        assertEquals("456", transacao.getContaDestino());
        assertEquals(100.0, transacao.getValor());
        assertEquals("Sucesso", transacao.getStatus());
        assertEquals(now, transacao.getDataHoraTransacao());
    }
}
