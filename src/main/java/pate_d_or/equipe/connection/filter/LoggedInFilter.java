package pate_d_or.equipe.connection.filter;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pate_d_or.equipe.bll.BLLException;
import pate_d_or.equipe.bll.UserBLL;

@Component
public class LoggedInFilter implements Filter {

	@Autowired private UserBLL service;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		
		
		//=========================================================================
		//routes non filtrée
		
		/*
		 * Si on essaie d'accéder au endpoint de login, on autorise l'accès
		 * sans vérifier d'autre condition
		 */
		
		if ("/pate_d_or/user/login".equals(httpReq.getServletPath()) || "/pate_d_or/user".equals(httpReq.getServletPath()) || "OPTIONS".equals(httpReq.getMethod())) {
				chain.doFilter(request, response);
				return;
			}
		
	
		
		//=========================================================================
		//

		
		/*
		 * Si le token n'est pas renseigné, on interdit l'accès
		 * Si le token est renseigné mais ne correspond à aucun user
		 * on interdit l'accès
		 * Sinon, on autorise l'accès
		 */
		try
		{
			
			service.getByToken(httpReq.getHeader("token"), LocalDateTime.now());
			chain.doFilter(request, response);
			
		}
		catch(BLLException error)
		{
			httpResp.sendError(HttpStatus.UNAUTHORIZED.value());
		}
		
		

		

	}
}
