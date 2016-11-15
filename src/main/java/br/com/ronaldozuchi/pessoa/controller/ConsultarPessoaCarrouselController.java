package br.com.ronaldozuchi.pessoa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ronaldozuchi.model.PessoaModel;
import br.com.ronaldozuchi.repository.PessoaRepository;

/**
 * Classe para efetuar a consultas de objetos em carrousel.
 *
 * @author Ronaldo Zuchi
 *
 */
@Named(value = "consultarPessoaCarouselController")
@ViewScoped
public class ConsultarPessoaCarrouselController implements Serializable {

	/**
	 * Variavel gerada automaticamente para identificar a serialização do
	 * objeto.
	 */
	private static final long serialVersionUID = -3046440967369718077L;

	@Inject transient
	private PessoaRepository pessoaRepository;

	@Produces
	private List<PessoaModel> pessoas;

	public List<PessoaModel> getPessoas(){
		return pessoas;
	}

	@PostConstruct
	private void init(){
		this.pessoas = pessoaRepository.GetPessoas();

	}

}
