package app.core.auth.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import app.core.auth.ClientType;
import app.core.auth.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Component
public class JwtUtilUser extends JwtUtilAbstract<User, Integer> {

	@Override
	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", user.getEmail());
		claims.put("clientType", user.getClientType());
		return this.createToken(claims, user.getId());
	}

	@Override
	public User extractUser(String token) throws JwtException {
		Claims claims = this.extractAllClaims(token);
		int id = Integer.parseInt(claims.getSubject());
		ClientType clientType = (ClientType) claims.get("clientType");
		String email = (String) claims.get("email");
		User user = new User(id,email,clientType);
		return user;

	}

}
