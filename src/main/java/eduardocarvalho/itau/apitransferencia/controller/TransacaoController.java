package eduardocarvalho.itau.apitransferencia.controller;

import eduardocarvalho.itau.apitransferencia.dto.Transacao;
import eduardocarvalho.itau.apitransferencia.service.TransacaoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@Api(value = "TransacaoController", tags = {"Transações"})
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
    public Transacao createTransacao(@RequestBody Transacao transacao) {
        return transacaoService.createTransacao(transacao);
    }

    @PutMapping("/{id}")
    public Transacao updateTransacao(@PathVariable Long id, @RequestBody Transacao transacaoDetails) {
        return transacaoService.updateTransacao(id, transacaoDetails);
    }
}
