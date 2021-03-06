package br.com.ronaldozuchi.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ronaldozuchi.model.PessoaModel;
import br.com.ronaldozuchi.model.UsuarioModel;
import br.com.ronaldozuchi.repository.entity.PessoaEntity;
import br.com.ronaldozuchi.repository.entity.UsuarioEntity;
import br.com.ronaldozuchi.uteis.Uteis;

/**
 * Repositório da classe pessoa.
 *
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
	 *
	 * @param pessoaModel
	 */
	public void SalvarNovoRegistro(PessoaModel pessoaModel) {
		entityManager = Uteis.JpaEntityManager();

		pessoaEntity = new PessoaEntity();
		pessoaEntity.setDataCadastro(LocalDateTime.now());
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setOrigemCadastro(pessoaModel.getOrigemCadastro());
		pessoaEntity.setSexo(pessoaModel.getSexo());

		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class,
				pessoaModel.getUsuarioModel().getCodigo());

		pessoaEntity.setUsuarioEntity(usuarioEntity);

		entityManager.persist(pessoaEntity);

	}

	/**
	 * Método para consulda do objeto.
	 *
	 * @return
	 */
	public List<PessoaModel> GetPessoas() {

		List<PessoaModel> pessoasModel = new ArrayList<PessoaModel>();
		entityManager = Uteis.JpaEntityManager();
		Query query = entityManager.createNamedQuery("PessoaEntity.findAll");

		@SuppressWarnings("unchecked")
		Collection<PessoaEntity> pessoasEntity = (Collection<PessoaEntity>) query.getResultList();

		PessoaModel pessoaModel = null;

		for (PessoaEntity pessoaEntity : pessoasEntity) {
			pessoaModel = new PessoaModel();
			pessoaModel.setCodigo(pessoaEntity.getCodigo());
			pessoaModel.setDataCadastro(pessoaEntity.getDataCadastro());
			pessoaModel.setEmail(pessoaEntity.getEmail());
			pessoaModel.setEndereco(pessoaEntity.getEndereco());
			pessoaModel.setNome(pessoaEntity.getNome());

			if (pessoaEntity.getOrigemCadastro().equals("X"))
				pessoaModel.setOrigemCadastro("XML");
			else
				pessoaModel.setOrigemCadastro("INPUT");

			if (pessoaEntity.getSexo().equals("M"))
				pessoaModel.setSexo("Masculino");
			else
				pessoaModel.setSexo("Feminino");

			UsuarioEntity usuarioEntity = pessoaEntity.getUsuarioEntity();

			UsuarioModel usuarioModel = new UsuarioModel();
			usuarioModel.setUsuario(usuarioEntity.getUsuario());

			pessoaModel.setUsuarioModel(usuarioModel);

			pessoasModel.add(pessoaModel);

		}

		return pessoasModel;

	}

	/**
	 * Método para consultar o objeto pelo seu código
	 *
	 * @param codigo
	 * @return
	 */
	private PessoaEntity GetPessoa(int codigo) {
		entityManager = Uteis.JpaEntityManager();
		return entityManager.find(PessoaEntity.class, codigo);
	}

	/**
	 * Método para alterar um cadastro.
	 *
	 * @param pessoaModel
	 */
	public void AlterarRegistro(PessoaModel pessoaModel) {
		entityManager = Uteis.JpaEntityManager();

		PessoaEntity pessoaEntity = this.GetPessoa(pessoaModel.getCodigo());

		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setSexo(pessoaModel.getSexo());

		entityManager.merge(pessoaEntity);

	}

	/**
	 * Método para excluir um objeto pelo código
	 *
	 * @param codigo
	 */
	public void ExcluirRegistro(int codigo) {
		entityManager = Uteis.JpaEntityManager();
		PessoaEntity pessoaEntity = this.GetPessoa(codigo);
		entityManager.remove(pessoaEntity);
	}
	/**
	 * Método retorna o tipo de objetos agrupados por categoria.
	 * @return
	 */
	public Hashtable<String, Integer> GetOrigemPessoa() {
		Hashtable<String, Integer> hashtableRegistros = new Hashtable<String, Integer>();
		entityManager = Uteis.JpaEntityManager();
		Query query = entityManager.createNamedQuery("PessoaEntity.GroupByOrigemCadastro");

		@SuppressWarnings("unchecked")
		Collection<Object[]> collectionRegistros = (Collection<Object[]>) query.getResultList();

		for (Object[] objects : collectionRegistros) {

			String tipoPessoa = (String) objects[0];
			int totalDeRegistros = ((Number) objects[1]).intValue();

			if (tipoPessoa.equals("X"))
				tipoPessoa = "XML";
			else
				tipoPessoa = "INPUT";
			hashtableRegistros.put(tipoPessoa, totalDeRegistros);
		}
		return hashtableRegistros;
	}
}
