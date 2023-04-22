package app.core.auth.jwt;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author ryaki
 *
 * @param <T>  the type of the object from which we generate the token - JWT
 *             payload
 * @param <ID> the type if the identifier - JWT subject
 */
public abstract class JwtUtilAbstract<T, ID> {

	private String alg = SignatureAlgorithm.HS256.getJcaName();
	@Value("${jwt.util.secret.key}")
	private String secret;
	private Key key;
	@Value("${jwt.util.chrono.unit}")
	private ChronoUnit chronoUnit;
	@Value("${jwt.util.chrono.unit.number}")
	private int unitsNumber;

	@PostConstruct
	public void init() {
		this.key = new SecretKeySpec(Base64.getDecoder().decode(secret), alg);
	}

	public abstract String generateToken(T user);
	
	

	/**
	 * this method creates the token
	 * @param claims
	 * @param subject
	 * @return
	 */
	protected String createToken(Map<String, Object> claims, ID subject) {
		JwtBuilder jwtBuilder = Jwts.builder();
		Instant now = Instant.now(); 
		Instant exp = now.plus(this.unitsNumber, this.chronoUnit);

		String token = jwtBuilder.setClaims(claims).setSubject(subject.toString()).setIssuedAt(Date.from(now))
				.setExpiration(Date.from(exp)).signWith(key).compact();
		return token;

	}

	public abstract T extractUser(String token) throws JwtException;

	
	/**
	 * this method opens the token
	 * @return
	 * @throws JwtException
	 */
	protected Claims extractAllClaims(String token) throws JwtException {
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
		Jws<Claims> jwt = jwtParser.parseClaimsJws(token);
		return jwt.getBody();
	}

}
