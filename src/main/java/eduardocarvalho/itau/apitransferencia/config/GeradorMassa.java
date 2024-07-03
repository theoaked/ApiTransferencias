package eduardocarvalho.itau.apitransferencia.config;

import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import eduardocarvalho.itau.apitransferencia.dto.Transacao;
import eduardocarvalho.itau.apitransferencia.repository.ClienteRepository;
import eduardocarvalho.itau.apitransferencia.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


// gerador de massa para facilitar os testes
@Component
public class GeradorMassa implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    private final TransacaoRepository transacaoRepository;

    @Autowired
    public GeradorMassa(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Inserir dados iniciais
        Cliente cliente1 = new Cliente();
        cliente1.setSaldo(1200.97);
        cliente1.setNome("Eduardo");
        cliente1.setConta("123");

        Cliente cliente2 = new Cliente();
        cliente2.setSaldo(350.12);
        cliente2.setNome("Samantha");
        cliente2.setConta("456");

        Cliente cliente3 = new Cliente();
        cliente3.setSaldo(8000.56);
        cliente3.setNome("Malik");
        cliente3.setConta("789");

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        clienteRepository.save(cliente3);

        Transacao transacao1 = new Transacao();
        transacao1.setValor(580.78);
        transacao1.setStatus("Falha na Transferencia! - Conta(s) Origem e/ou Destino não existe(m)!");
        transacao1.setDataHoraTransacao(LocalDateTime.now());
        transacao1.setContaOrigem("456");
        transacao1.setContaDestino("000");

        transacaoRepository.save(transacao1);

        System.out.println("Dados iniciais inseridos no banco de dados.");
    }
}
