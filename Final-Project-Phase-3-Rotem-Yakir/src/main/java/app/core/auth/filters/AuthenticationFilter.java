package app.core.auth.filters;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import app.core.auth.client.User;
import app.core.auth.jwt.JwtUtilUser;

public class AuthenticationFilter implements Filter {

	JwtUtilUser jwtUtil;

	public AuthenticationFilter(JwtUtilUser jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		if(httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		
		String auth = httpRequest.getHeader("Authorization");
		if (auth == null) {
			httpResponse.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000");
			httpResponse.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer \"general api\"");
			httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(),
					"You are not logged in. Please log in and try again.");
		} else {
			StringTokenizer tokenizer = new StringTokenizer(auth);
			tokenizer.nextToken();
			String jwt = tokenizer.nextToken();
			try {
				User user = jwtUtil.extractUser(jwt);
				httpRequest.setAttribute("user", user);
				chain.doFilter(httpRequest, response);
			} catch (Exception e) {
				httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "You are not logged in: " + e.getMessage());
				httpResponse.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer \"general api\"");
			}
		}
	}

}
