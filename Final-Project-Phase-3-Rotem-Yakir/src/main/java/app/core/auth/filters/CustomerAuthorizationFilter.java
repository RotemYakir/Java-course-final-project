package app.core.auth.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import app.core.auth.client.ClientType;
import app.core.auth.client.User;

public class CustomerAuthorizationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		User user = (User) httpRequest.getAttribute("user");
		if(user.getClientType().equals(ClientType.CUSTOMER)) {
			chain.doFilter(httpRequest, httpResponse);
		}else {
			httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"Access denied - only customers are authorized");
		}
		
	}

}
