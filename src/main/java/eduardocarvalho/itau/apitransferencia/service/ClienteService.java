package eduardocarvalho.itau.apitransferencia.service;

import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import eduardocarvalho.itau.apitransferencia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    ArrayList<Cliente> clientes = new ArrayList<>();

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente findByConta(int conta) {
        return clienteRepository.findByConta(conta);
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, Cliente clienteDetails) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
            cliente.setConta(clienteDetails.getConta());
            cliente.setNome(clienteDetails.getNome());
            cliente.setSaldo(clienteDetails.getSaldo());
            return clienteRepository.save(cliente);
        }
        return null;
    }
}
