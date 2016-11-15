package br.com.ronaldozuchi.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filtro para ser chamado quando uma requisição para o Faces Servelet for
 * solicitada.
 *
 * @author Ronaldo Zuchi
 */
@WebFilter(servletNames = { "Faces Servlet" })
public class JPAFilter implements Filter {

	/**
	 * Variáveis para persistencia dos dados.
	 */
	private EntityManagerFactory entityManagerFactory;
	private String unidadeDePersistencia = "unit_app";

	/**
	 * Construtor vazio.
	 */
	public JPAFilter() {

	}

	/**
	 * @see Filter#destroy()
	 * Método para ser sobrescrito, para destruir a sessão.
	 */
	public void destroy() {
		this.entityManagerFactory.close();
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 * Método para as realizar as requisições e respostas.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		/**
		 * Criando um Entity Manager.
		 */
		EntityManager entityManager =  this.entityManagerFactory.createEntityManager();
		/**
		 * Adicionando o Entyty Manager na requisição.
		 */
		request.setAttribute("entityManager", entityManager);
		/**
		 * Iniciando a transação.
		 */
		entityManager.getTransaction().begin();
		/**
		 * Iniciando o Faces Servlet.
		 */
		chain.doFilter(request, response);

		/**
		 * Tratando as excessões para possíveis erros.
		 */
		try{
			/**
			 * Linha de código será executada caso nao haja erro, executa-se então o commit.
			 */
			entityManager.getTransaction().commit();
		} catch (Exception e){
			/**
			 * Caso haja erro é chamado o rollback para desfazer a operação.
			 */
			entityManager.getTransaction().rollback();
		} finally{
				/**
				 * Finalização da operação caso tenha ocorrido o Commit ou Rollback.
				 */
			entityManager.close();
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 * Metodo para criar o Entity Manager através dos parametros do persistence.xml.
	 */
	public void init(FilterConfig fConfig) throws ServletException {

		this.entityManagerFactory = Persistence.createEntityManagerFactory(this.unidadeDePersistencia);
	}

}
