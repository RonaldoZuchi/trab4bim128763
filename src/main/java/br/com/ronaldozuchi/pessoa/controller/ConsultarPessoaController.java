package br.com.ronaldozuchi.pessoa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ronaldozuchi.model.PessoaModel;
import br.com.ronaldozuchi.repository.PessoaRepository;

/**
 * Classe para efetuar a consulta dos dados em banco.
 *
 * @author Ronaldo Zuchi
 *
 */
@Named(value = "consultarPessoaController")
@ViewScoped
public class ConsultarPessoaController implements Serializable {

	/**
	 * Variavel gerada automaticamente para identificar a serialização do
	 * objeto.
	 */
	private static final long serialVersionUID = 6488640299475529929L;

	@Inject
	transient private PessoaModel pessoaModel;

	@Produces
	private List<PessoaModel> pessoas;

	@Inject
	transient private PessoaRepository pessoaRepository;

	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	public List<PessoaModel> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaModel> pessoas) {
		this.pessoas = pessoas;
	}
	/**
	 * Método que carrega os objetos na inicialização e os retorna.
	 */
	@PostConstruct
	public void init() {
		this.pessoas = pessoaRepository.GetPessoas();

	}

}
