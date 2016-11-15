package br.com.ronaldozuchi.pessoa.controller;

import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import br.com.ronaldozuchi.repository.PessoaRepository;

/**
 * Classe para criar o objeto gráfico.
 *
 * @author Ronaldo Zuchi
 *
 */
@Named(value = "graficoPizzaPessoaController")
@RequestScoped
public class GraficoPizzaPessoaController {
	/**
	 * Variável da dependencia a ser injetada.
	 */
	@Inject
	private PessoaRepository pessoaRepository;

	private PieChartModel pieChartModel;

	public PieChartModel getPieChartModel() {
		return pieChartModel;
	}

	@PostConstruct
	public void init() {
		this.pieChartModel = new PieChartModel();
		this.MontaGrafico();
	}
	/**
	 * Método para criação do gráfico.
	 * Consulta das informações para o gráfico.
	 * informando os valores para a criação do gráfico.
	 */
	private void MontaGrafico() {
		Hashtable<String, Integer> hashtableRegistros = pessoaRepository.GetOrigemPessoa();
		hashtableRegistros.forEach((chave, valor) -> {
			pieChartModel.set(chave, valor);
		});
		pieChartModel.setTitle("Total de Pessoas cadastrado por Tipo");
		pieChartModel.setShowDataLabels(true);
		pieChartModel.setLegendPosition("e");
	}

}
