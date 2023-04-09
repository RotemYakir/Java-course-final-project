package app.core.login.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import app.core.entities.Company;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Component
public class JwtUtilCompany extends JwtUtilAbstract<Company, Integer> {

	@Override
	public String generateToken(Company user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("name", user.getName());
		claims.put("email", user.getEmail());
		// TODO check if i should add the coupons here
		
		return this.createToken(claims, user.getId());
	}

	@Override
	public Company extractUser(String token) throws JwtException {
		Claims claims = this.extractAllClaims(token);
		int id = Integer.parseInt(claims.getSubject());
		String name = (String) claims.get("name");
		String email = (String) claims.get("email");
		Company company = new Company(id, name, email, null, null);
		return company;

	}

}
