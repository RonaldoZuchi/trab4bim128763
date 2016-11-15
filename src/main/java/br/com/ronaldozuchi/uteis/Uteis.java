package br.com.ronaldozuchi.uteis;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

/**
 * Classe para recuperar o EntityManager criado no JPAFilter.
 *
 * @author Ronaldo Zuchi
 *
 */

public class Uteis {

	public static EntityManager JpaEntityManager() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		return (EntityManager) request.getAttribute("entityManager");

	}
	/*
	 * Método para mostrar a mensagem.
	 * @param mensagem
	 */
	public static void Mensagem (String mensagem){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage("Alerta",mensagem));

	}
	/**
	 * Metodo para mostrar a mensagem
	 * @param mensagem
	 */
	public static void MensagemAtencao(String mensagem){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção:", mensagem));
	}
	/**
	 * Metodo para mostrar a mensagem
	 * @param mensagem
	 */
	public static void MensagemInfo(String mensagem){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensagem));
	}

}
