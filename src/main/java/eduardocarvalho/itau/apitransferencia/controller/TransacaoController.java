package eduardocarvalho.itau.apitransferencia.controller;

import eduardocarvalho.itau.apitransferencia.dto.Transacao;
import eduardocarvalho.itau.apitransferencia.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public List<Transacao> getAllTransacoes() {
        return transacaoService.getAllTransacoes();
    }

    @GetMapping("/{id}")
    public Transacao getTransacaoById(@PathVariable Long id) {
        return transacaoService.getTransacaoById(id);
    }

    @PostMapping
    public Transacao createTransacao(int contaOrigem, int contaDestino, double valor) {
        return transacaoService.createTransacao(contaOrigem, contaDestino, valor);
    }
}
