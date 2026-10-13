package br.com.gearedlogistics.gearedlogistics.modules.financeiro.service;

import br.com.gearedlogistics.gearedlogistics.modules.clientes.domain.Cliente;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.service.ClienteService;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.AcordoComercial;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.Pagamento;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.StatusPagamento;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.StatusTransacao;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.TipoTransacao;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.TransacaoComercial;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.Venda;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.AcordoComercialRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.PagamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.RegistrarPagamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.TransacaoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.VendaRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.mapper.TransacaoMapper;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.repository.TransacaoComercialRepository;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.OrdemServico;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.service.OrdemServicoService;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import br.com.gearedlogistics.gearedlogistics.shared.exception.BusinessException;
import br.com.gearedlogistics.gearedlogistics.shared.exception.ResourceNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoComercialServiceImpl implements TransacaoComercialService {

    private static final String RECURSO = "Transação comercial";

    private final TransacaoComercialRepository transacaoRepository;
    private final TransacaoMapper transacaoMapper;
    private final ClienteService clienteService;
    private final OrdemServicoService ordemServicoService;

    @Override
    @Transactional
    public TransacaoResponse criarVenda(VendaRequest request) {
        Venda venda = new Venda();
        preencherBase(venda, request.clienteId(), request.ordemServicoId(),
                request.valorTotal(), request.dataRealizacao());
        venda.setFormaPagamento(request.formaPagamento());
        return transacaoMapper.toResponse(transacaoRepository.save(venda));
    }

    @Override
    @Transactional
    public TransacaoResponse criarPagamento(PagamentoRequest request) {
        Pagamento pagamento = new Pagamento();
        preencherBase(pagamento, request.clienteId(), request.ordemServicoId(),
                request.valorTotal(), request.dataRealizacao());
        pagamento.setValorPago(BigDecimal.ZERO);
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        return transacaoMapper.toResponse(transacaoRepository.save(pagamento));
    }

    @Override
    @Transactional
    public TransacaoResponse criarAcordo(AcordoComercialRequest request) {
        AcordoComercial acordo = new AcordoComercial();
        preencherBase(acordo, request.clienteId(), request.ordemServicoId(),
                request.valorTotal(), request.dataRealizacao());
        acordo.setCondicoes(request.condicoes());
        acordo.setNumeroParcelas(request.numeroParcelas());
        return transacaoMapper.toResponse(transacaoRepository.save(acordo));
    }

    @Override
    @Transactional(readOnly = true)
    public TransacaoResponse buscarPorId(Long id) {
        return transacaoMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TransacaoResponse> listar(Long clienteId, Long ordemServicoId, StatusTransacao status,
                                                  TipoTransacao tipo, Pageable pageable) {
        Specification<TransacaoComercial> filtro = montarFiltro(clienteId, ordemServicoId, status, tipo);
        return PageResponse.from(transacaoRepository.findAll(filtro, pageable).map(transacaoMapper::toResponse));
    }

    @Override
    @Transactional
    public TransacaoResponse aprovar(Long id) {
        TransacaoComercial transacao = buscarEntidade(id);
        exigirStatus(transacao, StatusTransacao.PENDENTE, "aprovar");
        transacao.setStatus(StatusTransacao.APROVADA);
        return transacaoMapper.toResponse(transacaoRepository.saveAndFlush(transacao));
    }

    @Override
    @Transactional
    public TransacaoResponse cancelar(Long id) {
        TransacaoComercial transacao = buscarEntidade(id);
        if (transacao.getStatus() == StatusTransacao.PAGA || transacao.getStatus() == StatusTransacao.CANCELADA) {
            throw new BusinessException("Não é possível cancelar uma transação com status " + transacao.getStatus());
        }
        transacao.setStatus(StatusTransacao.CANCELADA);
        return transacaoMapper.toResponse(transacaoRepository.saveAndFlush(transacao));
    }

    @Override
    @Transactional
    public TransacaoResponse pagar(Long id) {
        TransacaoComercial transacao = buscarEntidade(id);
        exigirStatus(transacao, StatusTransacao.APROVADA, "pagar");
        transacao.setStatus(StatusTransacao.PAGA);
        return transacaoMapper.toResponse(transacaoRepository.saveAndFlush(transacao));
    }

    @Override
    @Transactional
    public TransacaoResponse registrarPagamento(Long id, RegistrarPagamentoRequest request) {
        Pagamento pagamento = exigirPagamento(buscarEntidade(id));
        if (pagamento.getStatusPagamento() == StatusPagamento.QUITADO) {
            throw new BusinessException("O pagamento já está quitado");
        }

        BigDecimal valorPago = pagamento.getValorPago().add(request.valor());
        pagamento.setValorPago(valorPago);

        if (valorPago.compareTo(pagamento.getValorTotal()) >= 0) {
            pagamento.setStatusPagamento(StatusPagamento.QUITADO);
            pagamento.setDataQuitacao(LocalDate.now());
            pagamento.setStatus(StatusTransacao.PAGA);
        } else {
            pagamento.setStatusPagamento(StatusPagamento.PARCIALMENTE_PAGO);
        }
        return transacaoMapper.toResponse(transacaoRepository.saveAndFlush(pagamento));
    }

    @Override
    @Transactional
    public TransacaoResponse marcarInadimplente(Long id) {
        Pagamento pagamento = exigirPagamento(buscarEntidade(id));
        if (pagamento.getStatusPagamento() == StatusPagamento.QUITADO) {
            throw new BusinessException("Um pagamento quitado não pode ficar inadimplente");
        }
        pagamento.setStatusPagamento(StatusPagamento.INADIMPLENTE);
        return transacaoMapper.toResponse(transacaoRepository.saveAndFlush(pagamento));
    }

    @Override
    @Transactional
    public TransacaoResponse regularizar(Long id) {
        Pagamento pagamento = exigirPagamento(buscarEntidade(id));
        exigirStatusPagamento(pagamento, StatusPagamento.INADIMPLENTE, "regularizar");
        pagamento.setStatusPagamento(StatusPagamento.QUITADO);
        pagamento.setValorPago(pagamento.getValorTotal());
        pagamento.setDataQuitacao(LocalDate.now());
        pagamento.setStatus(StatusTransacao.PAGA);
        return transacaoMapper.toResponse(transacaoRepository.saveAndFlush(pagamento));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        TransacaoComercial transacao = buscarEntidade(id);
        exigirStatus(transacao, StatusTransacao.PENDENTE, "excluir");
        transacaoRepository.delete(transacao);
    }

    private void preencherBase(TransacaoComercial transacao, Long clienteId, Long ordemServicoId,
                               BigDecimal valorTotal, LocalDate dataRealizacao) {
        Cliente cliente = clienteService.buscarEntidade(clienteId);
        transacao.setCliente(cliente);

        if (ordemServicoId != null) {
            OrdemServico ordem = ordemServicoService.buscarEntidade(ordemServicoId);
            if (!ordem.getCliente().getId().equals(cliente.getId())) {
                throw new BusinessException("A ordem de serviço não pertence ao cliente informado");
            }
            transacao.setOrdemServico(ordem);
        }

        transacao.setValorTotal(valorTotal);
        transacao.setDataRealizacao(dataRealizacao != null ? dataRealizacao : LocalDate.now());
        transacao.setStatus(StatusTransacao.PENDENTE);
    }

    private void exigirStatus(TransacaoComercial transacao, StatusTransacao esperado, String acao) {
        if (transacao.getStatus() != esperado) {
            throw new BusinessException(
                    "Não é possível " + acao + " uma transação com status " + transacao.getStatus());
        }
    }

    private void exigirStatusPagamento(Pagamento pagamento, StatusPagamento esperado, String acao) {
        if (pagamento.getStatusPagamento() != esperado) {
            throw new BusinessException(
                    "Não é possível " + acao + " um pagamento com status " + pagamento.getStatusPagamento());
        }
    }

    private Pagamento exigirPagamento(TransacaoComercial transacao) {
        if (!(transacao instanceof Pagamento pagamento)) {
            throw new BusinessException("A transação informada não é um pagamento");
        }
        return pagamento;
    }

    private TransacaoComercial buscarEntidade(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }

    private Specification<TransacaoComercial> montarFiltro(Long clienteId, Long ordemServicoId,
                                                           StatusTransacao status, TipoTransacao tipo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (clienteId != null) {
                predicates.add(cb.equal(root.get("cliente").get("id"), clienteId));
            }
            if (ordemServicoId != null) {
                predicates.add(cb.equal(root.get("ordemServico").get("id"), ordemServicoId));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (tipo != null) {
                predicates.add(cb.equal(root.type(), classDe(tipo)));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Class<? extends TransacaoComercial> classDe(TipoTransacao tipo) {
        return switch (tipo) {
            case VENDA -> Venda.class;
            case PAGAMENTO -> Pagamento.class;
            case ACORDO -> AcordoComercial.class;
        };
    }
}
