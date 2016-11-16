package br.com.ronaldozuchi.pessoa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.ronaldozuchi.model.PessoaModel;
import br.com.ronaldozuchi.repository.PessoaRepository;

/**
 * Classe para exportar o objeto em .xml.
 *
 * @author Ronaldo Zuchi
 *
 */
@Named(value = "exportarRegistrosXmlController")
@RequestScoped
public class ExportarRegistrosXmlController implements Serializable {

	/**
	 * Variavel gerada automaticamente para identificar a serialização do
	 * objeto.
	 */
	private static final long serialVersionUID = -7552487723236128083L;

	@Inject
	transient PessoaRepository pessoaRepository;

	private StreamedContent arquivoDownload;

	/**
	 * Método para realizar o download do arquivo .xml
	 * @return
	 */
	public StreamedContent getArquivoDownload() {
		this.DownlaodArquivoXmlPessoa();
		return arquivoDownload;

	}

	/**
	 * Método para gerar o arquivo .xml
	 * @return
	 */
	private File GerarXmlPessoas() {
		/**
		 * Linha de código para formatar a data no arquivo .xml
		 */
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		List<PessoaModel> pessoasModel = pessoaRepository.GetPessoas();
		/**
		 * Elemento raiz do arquivo .xml
		 */
		Element elementPessoas = new Element("Pessoas");
		Document documentoPessoas = new Document(elementPessoas);
		pessoasModel.forEach(pessoa -> {
			/**
			 * Tags do arquivo .xml
			 */
			Element elementPessoa = new Element("Pessoa");
			elementPessoa.addContent(new Element("codigo").setText(pessoa.getCodigo().toString()));
			elementPessoa.addContent(new Element("nome").setText(pessoa.getNome()));
			elementPessoa.addContent(new Element("sexo").setText(pessoa.getSexo()));
			/**
			 * Formatando a data.
			 */
			String dataCadastroFormatada = pessoa.getDataCadastro().format(dateTimeFormatter);

			elementPessoa.addContent(new Element("dataCadastro").setText(dataCadastroFormatada));

			elementPessoa.addContent(new Element("email").setText(pessoa.getEmail()));
			elementPessoa.addContent(new Element("endereco").setText(pessoa.getEndereco()));
			elementPessoa.addContent(new Element("origemCadastro").setText(pessoa.getOrigemCadastro()));
			elementPessoa.addContent(new Element("usuarioCadastro").setText(pessoa.getUsuarioModel().getUsuario()));

			elementPessoas.addContent(elementPessoa);

		});

		XMLOutputter xmlGerado = new XMLOutputter();

		try {
			/**
			 * Gerando o nome do arquivo .xml
			 */
			String nomeArquivo = "pessoas_".concat(java.util.UUID.randomUUID().toString()).concat(".xml");
			/**
			 * Definição do caminho onde será gravado o arquivo .xml
			 */
			File arquivo = new File("user.dir".concat(nomeArquivo));
			FileWriter fileWriter = new FileWriter(arquivo);
			xmlGerado.output(documentoPessoas, fileWriter);
			return arquivo;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	/**
	 * Prepara o arquivo para download
	 */
	public void DownlaodArquivoXmlPessoa() {
		File arquivoXml = this.GerarXmlPessoas();
		InputStream inputStream;

		try {
			inputStream = new FileInputStream(arquivoXml.getPath());
			arquivoDownload = new DefaultStreamedContent(inputStream, "application/xml", arquivoXml.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
