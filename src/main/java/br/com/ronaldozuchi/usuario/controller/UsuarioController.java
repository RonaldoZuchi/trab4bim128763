package br.com.ronaldozuchi.usuario.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.ronaldozuchi.model.UsuarioModel;
import br.com.ronaldozuchi.repository.UsuarioRepository;
import br.com.ronaldozuchi.repository.entity.UsuarioEntity;
import br.com.ronaldozuchi.uteis.Uteis;

/**
 * Classe gerenciada pelo Bean CDI com espopo de sessão.
 *
 * @author Ronaldo Zuchi
 *
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

	/**
	 * Variavel gerada automaticamente para identificar a serialização do
	 * objeto.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Objetos a serem injetados (injeção de dependência).
	 */
	@Inject
	private UsuarioModel usuarioModel;
	@Inject
	private UsuarioRepository usuarioRepository;
	@Inject
	private UsuarioEntity usuarioEntity;

	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	/**
	 * Metodo para retornar o usuario logado.
	 *
	 * @return
	 */
	public UsuarioModel GetUsuarioSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return (UsuarioModel) facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}

	/**
	 * Metodo para sair do login com retorno redirecionando para a páginade
	 * login.
	 *
	 * @return
	 */
	public String Logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";

	}

	/**
	 * Faz a atenticação do login e loga o usuario.
	 * @return
	 */
	public String EfetuarLogin() {
		/**
		 * Verificações para possíveis erros na hora do preenchimento dos dados de login.
		 */
		if (StringUtils.isEmpty(usuarioModel.getUsuario()) || StringUtils.isBlank(usuarioModel.getUsuario())) {
			Uteis.Mensagem("Favor informar o login!");
			return null;
		} else {
			if (StringUtils.isEmpty(usuarioModel.getSenha()) || StringUtils.isBlank(usuarioModel.getSenha())) {
				Uteis.Mensagem("Favor informara senha!");
				return null;
			} else {
				usuarioEntity = usuarioRepository.ValidaUsuario(usuarioModel);
				if (usuarioEntity != null) {
					usuarioModel.setSenha(null);
					usuarioModel.setCodigo(usuarioEntity.getCodigo());

					FacesContext facesContext = FacesContext.getCurrentInstance();
					facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioModel);

					return "sistema/home?faces-redirect=true";
				} else {
					Uteis.Mensagem("Não foi possível efetuar o login com esse usuário e senha!");
					return null;
				}

			}
		}

	}

}
