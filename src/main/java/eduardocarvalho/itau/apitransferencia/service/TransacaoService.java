package eduardocarvalho.itau.apitransferencia.service;

import eduardocarvalho.itau.apitransferencia.dto.Transacao;
import eduardocarvalho.itau.apitransferencia.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> getAllTransacoes() {
        return transacaoRepository.findAll();
    }

    public Transacao getTransacaoById(Long id) {
        return transacaoRepository.findById(id).orElse(null);
    }

    public Transacao createTransacao(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public Transacao updateTransacao(Long id, Transacao transacaoDetails) {
        Transacao transacao = transacaoRepository.findById(id).orElse(null);
        if (transacao != null) {
            transacao.setContaOrigem(transacaoDetails.getContaOrigem());
            transacao.setContaDestino(transacaoDetails.getContaDestino());
            transacao.setValor(transacaoDetails.getValor());
            transacao.setStatus(transacaoDetails.getStatus());
            return transacaoRepository.save(transacao);
        }
        return null;
    }
}
