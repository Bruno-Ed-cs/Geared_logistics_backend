package br.com.gearedlogistics.gearedlogistics.modules.financeiro.service;

import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.NotaFiscal;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.StatusNotaFiscal;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.StatusTransacao;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.TransacaoComercial;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.NotaFiscalRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.NotaFiscalResponse;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.mapper.NotaFiscalMapper;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.repository.NotaFiscalRepository;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.repository.TransacaoComercialRepository;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import br.com.gearedlogistics.gearedlogistics.shared.exception.BusinessException;
import br.com.gearedlogistics.gearedlogistics.shared.exception.ResourceNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotaFiscalServiceImpl implements NotaFiscalService {

    private static final String RECURSO = "Nota fiscal";

    private final NotaFiscalRepository notaFiscalRepository;
    private final TransacaoComercialRepository transacaoComercialRepository;
    private final NotaFiscalMapper notaFiscalMapper;

    @Override
    @Transactional
    public NotaFiscalResponse criar(NotaFiscalRequest request) {
        TransacaoComercial transacao = transacaoComercialRepository.findById(request.transacaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Transação comercial", request.transacaoId()));

        if (transacao.getStatus() == StatusTransacao.CANCELADA) {
            throw new BusinessException("Não é possível emitir nota fiscal de uma transação cancelada");
        }
        if (notaFiscalRepository.existsByTransacaoId(transacao.getId())) {
            throw new BusinessException("Já existe uma nota fiscal para esta transação");
        }
        if (notaFiscalRepository.existsByNumeroDocumento(request.numeroDocumento())) {
            throw new BusinessException("Já existe uma nota fiscal com o número " + request.numeroDocumento());
        }

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setTransacao(transacao);
        notaFiscal.setNumeroDocumento(request.numeroDocumento());
        notaFiscal.setImpostos(request.impostos());
        notaFiscal.setDataEmissao(request.dataEmissao() != null ? request.dataEmissao() : LocalDate.now());
        notaFiscal.setStatus(StatusNotaFiscal.EMITIDA);
        return notaFiscalMapper.toResponse(notaFiscalRepository.save(notaFiscal));
    }

    @Override
    @Transactional(readOnly = true)
    public NotaFiscalResponse buscarPorId(Long id) {
        return notaFiscalMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<NotaFiscalResponse> listar(Long transacaoId, StatusNotaFiscal status, Pageable pageable) {
        Specification<NotaFiscal> filtro = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (transacaoId != null) {
                predicates.add(cb.equal(root.get("transacao").get("id"), transacaoId));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return PageResponse.from(notaFiscalRepository.findAll(filtro, pageable).map(notaFiscalMapper::toResponse));
    }

    @Override
    @Transactional
    public NotaFiscalResponse validar(Long id) {
        NotaFiscal notaFiscal = buscarEntidade(id);
        exigirStatus(notaFiscal, StatusNotaFiscal.EMITIDA, "validar");
        notaFiscal.setStatus(StatusNotaFiscal.VALIDA);
        return notaFiscalMapper.toResponse(notaFiscalRepository.saveAndFlush(notaFiscal));
    }

    @Override
    @Transactional
    public NotaFiscalResponse cancelar(Long id) {
        NotaFiscal notaFiscal = buscarEntidade(id);
        exigirStatus(notaFiscal, StatusNotaFiscal.EMITIDA, "cancelar");
        notaFiscal.setStatus(StatusNotaFiscal.CANCELADA);
        return notaFiscalMapper.toResponse(notaFiscalRepository.saveAndFlush(notaFiscal));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        NotaFiscal notaFiscal = buscarEntidade(id);
        exigirStatus(notaFiscal, StatusNotaFiscal.EMITIDA, "excluir");
        notaFiscalRepository.delete(notaFiscal);
    }

    private void exigirStatus(NotaFiscal notaFiscal, StatusNotaFiscal esperado, String acao) {
        if (notaFiscal.getStatus() != esperado) {
            throw new BusinessException(
                    "Não é possível " + acao + " uma nota fiscal com status " + notaFiscal.getStatus());
        }
    }

    private NotaFiscal buscarEntidade(Long id) {
        return notaFiscalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }
}
