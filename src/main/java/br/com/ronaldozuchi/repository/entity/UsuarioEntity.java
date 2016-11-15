package br.com.ronaldozuchi.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Classe para realizar a persistencia dos usuários cadastrados
 * Dados desta classe são serializados
 * @author Ronaldo Zuchi
 *
 */

/**
 *
 * Anotacions para a persistencia dos dados, informado a tabela, que esta classe
 * é uma entidade no banco, e a consulta a ser realizada no banco
 */
@Table(name = "tb_usuario")
@Entity
@NamedQuery(name = "UsuarioEntity.findUser", query = "SELECT u FROM UsuarioEntity u WHERE u.usuario = :usuario AND u.senha = :senha")
public class UsuarioEntity implements Serializable {

	/**
	 * Variavel gerada automaticamente para identificar a serialização do objeto.
	 */
	private static final long serialVersionUID = 627273154263706024L;

	@Id
	@GeneratedValue
	@Column(name="id_usuario")
	private String codigo;

	@Column(name="ds_login")
	private String usuario;

	@Column(name="ds_senha")
	private String senha;

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
