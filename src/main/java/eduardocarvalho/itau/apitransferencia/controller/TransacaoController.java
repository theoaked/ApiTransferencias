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

    @GetMapping("/{conta}")
    public List<Transacao> getTransacaoByConta(@PathVariable String conta) {
        return transacaoService.getAllTransacoesByConta(conta);
    }

    @PostMapping
    public Transacao createTransacao(String contaOrigem, String contaDestino, Double valor) {
        return transacaoService.createTransacao(contaOrigem, contaDestino, valor);
    }
}
