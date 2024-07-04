package eduardocarvalho.itau.apitransferencia.service;

import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import eduardocarvalho.itau.apitransferencia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente findByConta(String conta) {
        return clienteRepository.findByConta(conta);
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
