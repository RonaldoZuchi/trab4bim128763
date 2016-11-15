package br.com.ronaldozuchi.model;

import java.io.Serializable;

/**
 * Classe Utilizada pelo Managed Beans e serve para informar os dados que o objeto Usuario deverá possuir.
 * @author Ronaldo Zuchi
 *
 */
public class UsuarioModel implements Serializable{

	/**
	 * Variável com o valor da serialização.
	 * Variáveis necessárias para se obter um usuário.
	 */
	private static final long serialVersionUID = 3282850966303139833L;
	private String codigo;
	private String usuario;
	private String senha;

	/**
	 * Métodos Get e Set.
	 * @return
	 */
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

}
