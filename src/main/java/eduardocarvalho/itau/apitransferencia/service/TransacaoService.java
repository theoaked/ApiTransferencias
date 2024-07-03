package eduardocarvalho.itau.apitransferencia.service;

import eduardocarvalho.itau.apitransferencia.dto.Cliente;
import eduardocarvalho.itau.apitransferencia.dto.Transacao;
import eduardocarvalho.itau.apitransferencia.repository.ClienteRepository;
import eduardocarvalho.itau.apitransferencia.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    //transactional garante que a operação inteira ocorra em uma unica transacao, ou seja, se houver alguma falha no
    //metodo, toda transaçao é cancelada
    @Transactional
    public Transacao createTransacao(String contaOrigem, String contaDestino, double valor) {
        Cliente clienteOrigem = clienteRepository.findByConta(contaOrigem);
        Cliente clienteDestino = clienteRepository.findByConta(contaDestino);
        Transacao transacao = new Transacao();
        transacao.setContaOrigem(contaOrigem);
        transacao.setContaDestino(contaDestino);
        transacao.setValor(valor);

        // regras associadas a conta
        if (clienteOrigem == null || clienteDestino == null ){
            transacao.setStatus("Falha na Transferencia! - Conta(s) Origem e/ou Destino não existe(m)!");
        } else if (valor > 1000){
            transacao.setStatus("Falha na Transferencia! - Valor acima de R$1.000,00!");
        } else if (clienteOrigem.getSaldo() < valor){
            transacao.setStatus("Falha na Transferencia! - Saldo Insuficiente!");
        } else {
            //synchronized faz com qu apenas uma thread possa executar o bloco de codigo por vez
            synchronized (this) {
                clienteOrigem.setSaldo(clienteOrigem.getSaldo() - valor);
                clienteDestino.setSaldo(clienteDestino.getSaldo() + valor);
                clienteRepository.save(clienteOrigem);
                clienteRepository.save(clienteDestino);
                transacao.setStatus("Sucesso!");
            }
        }

        transacao.setDataHoraTransacao(LocalDateTime.now());
        transacaoRepository.save(transacao);
        return transacao;

    }

    //coletar todas transacoes executadas chaveadas por conta (origem ou destino)
    public List<Transacao> getAllTransacoesByConta(String conta) {
        List<Transacao> transacoesTotais = transacaoRepository.findAll();
        List<Transacao> transacoesByConta = new ArrayList<>();
        //para cada transacao no banco de dados é feita uma analise se a conta origem ou destino é igual a conta buscada
        for (Transacao transacao : transacoesTotais) {
            if (transacao.getContaDestino().equals(conta) || transacao.getContaOrigem().equals(conta)){
                transacoesByConta.add(transacao);
            }
        }

        //a lista de transacoes é ordenada de forma decrescente por datahoratransacao
        Transacao.ordenarPorDataHoraDecrescente(transacoesByConta);
        return transacoesByConta;
    }
}
