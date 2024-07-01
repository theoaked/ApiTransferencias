package eduardocarvalho.itau.apitransferencia.service;

import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import eduardocarvalho.itau.apitransferencia.dto.Transacao;
import eduardocarvalho.itau.apitransferencia.repository.ClienteRepository;
import eduardocarvalho.itau.apitransferencia.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Transacao> getAllTransacoes() {
        return transacaoRepository.findAll();
    }

    public Transacao getTransacaoById(Long id) {
        return transacaoRepository.findById(id).orElse(null);
    }

    public Transacao createTransacao(int contaOrigem, int contaDestino, double valor) {
        Cliente clienteOrigem = clienteRepository.findByConta(contaOrigem);
        Cliente clienteDestino = clienteRepository.findByConta(contaDestino);
        Transacao transacao = new Transacao();
        transacao.setDataHoraTransacao(LocalDateTime.now());
        transacao.setContaOrigem(contaOrigem);
        transacao.setContaDestino(contaDestino);
        transacao.setValor(valor);
        if (clienteOrigem == null || clienteDestino == null ){
            transacao.setStatus("Falha na Transferencia! - Conta(s) Origem e/ou Destino não existe(m)!");
        } else if (valor > 1000){
            transacao.setStatus("Falha na Transferencia! - Valor acima de R$1.000,00!");
        } else if (clienteOrigem.getSaldo() < valor){
            transacao.setStatus("Falha na Transferencia! - Saldo Insuficiente!");
        } else {
            clienteOrigem.setSaldo(clienteOrigem.getSaldo() - valor);
            clienteDestino.setSaldo(clienteDestino.getSaldo() + valor);
            transacao.setStatus("Sucesso!");
        }
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
