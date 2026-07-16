package com.example.kafka.architecture.icompras.pedidos.service;

import com.example.kafka.architecture.icompras.pedidos.client.ClientesCLient;
import com.example.kafka.architecture.icompras.pedidos.client.ProdutosClient;
import com.example.kafka.architecture.icompras.pedidos.client.ServicoBancarioClient;
import com.example.kafka.architecture.icompras.pedidos.model.DadosPagamento;
import com.example.kafka.architecture.icompras.pedidos.model.ItemPedido;
import com.example.kafka.architecture.icompras.pedidos.model.Pedido;
import com.example.kafka.architecture.icompras.pedidos.model.enums.Status;
import com.example.kafka.architecture.icompras.pedidos.model.enums.TipoPagamento;
import com.example.kafka.architecture.icompras.pedidos.model.enums.exception.ItemNaoEncontradoException;
import com.example.kafka.architecture.icompras.pedidos.publisher.PagamentoPublisher;
import com.example.kafka.architecture.icompras.pedidos.repository.ItemPedidoRepository;
import com.example.kafka.architecture.icompras.pedidos.repository.PedidoRepository;
import com.example.kafka.architecture.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioClient;
    private final ClientesCLient clientesCLient;
    private final ProdutosClient produtosClient;
    private final PagamentoPublisher pagamentoPublisher;


    @Transactional
    public Pedido salvarPedido(Pedido pedido) {
        validator.validar(pedido);
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
        pedido.setChavePagamento(servicoBancarioClient.solicitarPagamento(pedido));
        return pedido;

    }

    public Optional<Pedido> obterPedido(Long pedido) {
        return repository.findById(pedido);
    }

    public void atualizarStatusPagamento(
            Long codigoPedido, String chavePagamento,
            boolean sucesso, String observacoes
    ) {
        var pedidoEncontrado = repository.findByCodigoAndChavePagamento(codigoPedido, chavePagamento);

        if (pedidoEncontrado.isEmpty()) {
            var msg = String.format("Pedido não encontrado para o código %d e a chave de pagamento %s", codigoPedido, chavePagamento);

            log.error(msg);

            return;
        }

        Pedido pedido = pedidoEncontrado.get();

        if(sucesso){
            prepararEPublicarPedidoPago(observacoes, pedido);

        }else{
            pedido.setStatus(Status.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }

        repository.save(pedido);

    }

    private void prepararEPublicarPedidoPago(String observacoes, Pedido pedido) {
        pedido.setStatus(Status.PAGO);
        pedido.setObservacoes(observacoes);
        carregarDadosCliente(pedido);
        carregarItensPedido(pedido);
        pagamentoPublisher.publicar(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(
            Long codigoPedido,
            String dadosCartao,
            TipoPagamento tipoPagamento){

        var pedidoEncontrado = repository.findById(codigoPedido);

        if(pedidoEncontrado.isEmpty()){
           throw new ItemNaoEncontradoException("Pedido não encontrado!");
        }

        var pedido = pedidoEncontrado.get();

        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setTipoPagamento(tipoPagamento);
        dadosPagamento.setDados(dadosCartao);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(Status.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando processamento.");

        pedido.setChavePagamento(servicoBancarioClient.solicitarPagamento(pedido));

        //redundancia de código para deixar visual que está sendo persistido todos os dados de processamento do método
        repository.save(pedido);
    }

    public Optional<Pedido> carregarDadosCompletosPedido(Long codigo){
        Optional<Pedido> pedido = repository.findById(codigo);
        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregarItensPedido);
        return pedido;
    }

    private void carregarDadosCliente(Pedido pedido){
        pedido.setDadosCliente(clientesCLient.obterDadosClientes(pedido.getCodigoCliente()).getBody());
    }

    private void carregarItensPedido(Pedido pedido){
        pedido.setItens(itemPedidoRepository.findByPedido(pedido));
        pedido.getItens().forEach(this::carregarDadosProduto);
    }


    private void carregarDadosProduto(ItemPedido item){
        item.setNome(produtosClient.obterDadosProduto(item.getCodigoProduto()).getBody().nome());
    }

}
