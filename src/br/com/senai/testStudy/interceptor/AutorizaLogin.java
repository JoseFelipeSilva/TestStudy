package br.com.senai.testStudy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AutorizaLogin extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object controller) throws Exception {

		String uri = request.getRequestURI();
		if (uri.endsWith("logar") || uri.contains("resources")) {
			return true;
		}
		
		if (request.getSession().getAttribute("alunoLogon") != null
				|| request.getSession().getAttribute("examLogon") != null
				|| request.getSession().getAttribute("coordLogon") != null
				|| request.getSession().getAttribute("profLogon") != null
				|| request.getSession().getAttribute("admLogon") != null) {
			return true;
		}

		response.sendRedirect("index.jsp");
		return false;
	}
}
