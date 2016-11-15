package br.com.ronaldozuchi.pessoa.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	/**
	 * Variavel do tipo UploadedFile para armazenar o arquivo .xml a ser
	 * utilizado.
	 */
	private UploadedFile file;

	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
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

	/**
	 * Método para realizar o upload de arquivo para persistir dados através de
	 * um .xml
	 */
	public void UploadRegistros() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {

			if (this.file.getFileName().equals("")) {
				Uteis.MensagemAtencao("Nenhum arquivo selecionado!");
				return;
			}

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(this.file.getInputstream());
			Element element = doc.getDocumentElement();
			NodeList nodes = element.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elementPessoa = (Element) node;
					/**
					 * Recebendo os valores do arquivo .xml.
					 */
					String nome = elementPessoa.getElementsByTagName("nome").item(0).getChildNodes().item(0)
							.getNodeValue();
					String sexo = elementPessoa.getElementsByTagName("sexo").item(0).getChildNodes().item(0)
							.getNodeValue();
					String email = elementPessoa.getElementsByTagName("email").item(0).getChildNodes().item(0)
							.getNodeValue();
					String endereco = elementPessoa.getElementsByTagName("endereco").item(0).getChildNodes().item(0)
							.getNodeValue();

					PessoaModel newPessoaModel = new PessoaModel();

					newPessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());
					newPessoaModel.setEmail(email);
					newPessoaModel.setEndereco(endereco);
					newPessoaModel.setNome(nome);
					newPessoaModel.setOrigemCadastro("X");
					newPessoaModel.setSexo(sexo);
					/**
					 * Enviando os dados como parametro para salvar o registro.
					 */
					pessoaRepository.SalvarNovoRegistro(newPessoaModel);

				}

			}

			Uteis.MensagemInfo("Registros cadastrados com sucesso!");

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {

		}

	}

}
