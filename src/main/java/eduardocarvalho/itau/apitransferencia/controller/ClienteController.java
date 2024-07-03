package eduardocarvalho.itau.apitransferencia.controller;

import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import eduardocarvalho.itau.apitransferencia.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Obter todos os clientes")
    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @GetMapping("/{conta}")
    public Cliente getClienteByConta(@PathVariable String conta) {
        return clienteService.findByConta(conta);
    }

    @PostMapping
    public Cliente createCliente(String conta, String nome, Double saldo) {
        Cliente cliente = new Cliente();
        cliente.setSaldo(saldo);
        cliente.setNome(nome);
        cliente.setConta(conta);
        return clienteService.createCliente(cliente);
    }
}
