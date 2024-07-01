package eduardocarvalho.itau.apitransferencia.config;

import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import eduardocarvalho.itau.apitransferencia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MassaClientes implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    @Autowired
    public MassaClientes(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Inserir dados iniciais
        Cliente cliente1 = new Cliente();
        cliente1.setSaldo(1200.97);
        cliente1.setNome("Eduardo");
        cliente1.setConta(123);

        Cliente cliente2 = new Cliente();
        cliente2.setSaldo(350.12);
        cliente2.setNome("Samantha");
        cliente2.setConta(456);

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        System.out.println("Dados iniciais inseridos no banco de dados.");
    }
}
