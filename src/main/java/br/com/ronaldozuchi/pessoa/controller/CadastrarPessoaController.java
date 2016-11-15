package br.com.ronaldozuchi.pessoa.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ronaldozuchi.model.PessoaModel;
import br.com.ronaldozuchi.repository.PessoaRepository;
import br.com.ronaldozuchi.usuario.controller.UsuarioController;
import br.com.ronaldozuchi.uteis.Uteis;

/**
 * Classe para efetuar o cadastro de uma pessoa.
 *
 * @author Ronaldo Zuchi
 *
 */
@Named(value = "cadastrarPessoaController")
@RequestScoped
public class CadastrarPessoaController {
	/**
	 * Dependencias a serem injetadas.
	 */
	@Inject
	PessoaModel pessoaModel;
	@Inject
	UsuarioController usuarioController;
	@Inject
	PessoaRepository pessoaRepository;

	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	/**
	 * Método para cadastrar nova pessoa.
	 */
	public void SalvarNovaPessoa() {
		pessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());
		/**
		 * Informação de que o cadastro foi via Input.
		 */
		pessoaModel.setOrigemCadastro("I");
		pessoaRepository.SalvarNovoRegistro(this.pessoaModel);
		this.pessoaModel = null;
		Uteis.MensagemInfo("Registro cadastrado com sucesso");

	}

}
