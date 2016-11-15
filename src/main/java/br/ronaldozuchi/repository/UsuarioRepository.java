package br.ronaldozuchi.repository;

import java.io.Serializable;

import javax.persistence.Query;

import br.com.ronaldozuchi.model.UsuarioModel;
import br.com.ronaldozuchi.repository.entity.UsuarioEntity;
import br.com.ronaldozuchi.uteis.Uteis;

public class UsuarioRepository implements Serializable {

	/**
	 * Variável com o valor da serialização.
	 */
	private static final long serialVersionUID = 4097495139003417032L;

	public UsuarioEntity ValidaUsuario(UsuarioModel usuarioModel) {

		/**
		 * Tratando as excessões para possíveis erros.
		 */
		try {
			/**
			 * Consulta a ser executada.
			 */
			Query query = Uteis.JpaEntityManager().createNamedQuery("UsuarioEntity.findUser");
			/**
			 * Parametros passados para a consulta.
			 */
			query.setParameter("usuario", usuarioModel.getUsuario());
			query.setParameter("senha", usuarioModel.getSenha());
			/**
			 * Retorno cado o usuário seja localizado.
			 */
			return (UsuarioEntity)query.getSingleResult();


		} catch (Exception e) {
			return null;
		}

	}

}
