package br.com.gearedlogistics.gearedlogistics.modules.funcionarios.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain.Funcionario;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.dto.FuncionarioRequest;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.dto.FuncionarioResponse;
import br.com.gearedlogistics.gearedlogistics.modules.usuarios.domain.Usuario;
import br.com.gearedlogistics.gearedlogistics.shared.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper implements BaseMapper<FuncionarioRequest, FuncionarioResponse, Funcionario> {

    @Override
    public Funcionario toEntity(FuncionarioRequest request) {
        Funcionario funcionario = new Funcionario();
        aplicarDados(funcionario, request);
        return funcionario;
    }

    @Override
    public FuncionarioResponse toResponse(Funcionario entity) {
        Usuario usuario = entity.getUsuario();
        return new FuncionarioResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCpfCnpj(),
                entity.getEndereco(),
                entity.getMatricula(),
                entity.getCargo(),
                entity.getEspecialidade(),
                entity.getNivelAcessoAdmin(),
                entity.getSalario(),
                entity.getDataAdmissao(),
                entity.isAtivo(),
                usuario != null ? usuario.getId() : null,
                usuario != null ? usuario.getEmail() : null,
                entity.getCriadoEm(),
                entity.getAtualizadoEm());
    }

    @Override
    public void updateEntity(Funcionario entity, FuncionarioRequest request) {
        aplicarDados(entity, request);
    }

    private void aplicarDados(Funcionario funcionario, FuncionarioRequest request) {
        funcionario.setNome(request.nome());
        funcionario.setCpfCnpj(request.cpfCnpj());
        funcionario.setEndereco(request.endereco());
        funcionario.setMatricula(request.matricula());
        funcionario.setCargo(request.cargo());
        funcionario.setEspecialidade(request.especialidade());
        funcionario.setNivelAcessoAdmin(request.nivelAcessoAdmin());
        funcionario.setSalario(request.salario());
        funcionario.setDataAdmissao(request.dataAdmissao());
        funcionario.setAtivo(request.ativo() == null || request.ativo());
    }
}
