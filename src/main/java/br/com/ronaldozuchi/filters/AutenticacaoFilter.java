package br.com.ronaldozuchi.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.ronaldozuchi.model.UsuarioModel;

/**
 * Classe para validar se o login está logado e acessar as páginas.
 * Verifica se já existe um usuário na sessão, e redireciona para a página de atenticação caso não exista.
 *
 * @author Ronaldo Zuchi
 *
 */
@WebFilter("/sistema/*")
public class AutenticacaoFilter implements Filter {
	/**
	 * Construtor vazio.
	 */
	public AutenticacaoFilter() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {

		HttpSession httpSession = ((HttpServletRequest) arg0).getSession();
		HttpServletRequest httpServletRequest = (HttpServletRequest) arg0;
		HttpServletResponse httpServletResponse = (HttpServletResponse) arg1;

		if (httpServletRequest.getRequestURI().indexOf("index.xhtml") <= -1) {
			UsuarioModel usuarioModel = (UsuarioModel) httpSession.getAttribute("usuarioAutenticado");
			if (usuarioModel == null) {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/index.xhtml");
			} else {
				arg2.doFilter(arg0, arg1);
			}
		} else {
			arg2.doFilter(arg0, arg1);

		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
