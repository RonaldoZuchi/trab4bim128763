package br.com.ronaldozuchi.repository;

import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ronaldozuchi.model.PessoaModel;
import br.com.ronaldozuchi.repository.entity.PessoaEntity;
import br.com.ronaldozuchi.repository.entity.UsuarioEntity;
import br.com.ronaldozuchi.uteis.Uteis;

/**
 * Repositório da classe pessoa.
 * @author Ronaldo Zuchi
 *
 */
public class PessoaRepository {
/**
 * Dependencia a ser injetada.
 */
	@Inject
	PessoaEntity pessoaEntity;

	EntityManager entityManager;
	/**
	 * Método para salvar um novo usuário
	 * @param pessoaModel
	 */
	public void SalvarNovoRegistro(PessoaModel pessoaModel){
		entityManager =  Uteis.JpaEntityManager();

		pessoaEntity = new PessoaEntity();
		pessoaEntity.setDataCadastro(LocalDateTime.now());
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setOrigemCadastro(pessoaModel.getOrigemCadastro());
		pessoaEntity.setSexo(pessoaModel.getSexo());

		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class, pessoaModel.getUsuarioModel().getCodigo());

		pessoaEntity.setUsuarioEntity(usuarioEntity);

		entityManager.persist(pessoaEntity);


	}


}
