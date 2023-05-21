package app.core.auth.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import app.core.auth.client.ClientType;
import app.core.auth.client.User;

@Order(2)
public class AdminAuthorizationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		if(httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		
		User user = (User) httpRequest.getAttribute("user");
		if(user.getClientType().equals(ClientType.ADMIN)) {
			chain.doFilter(httpRequest, httpResponse);
		}else {
			httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"Access denied - only admins are authorized");
		}
		
	}

}
