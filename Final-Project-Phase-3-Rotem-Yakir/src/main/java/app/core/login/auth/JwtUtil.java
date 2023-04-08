package app.core.login.auth;

import java.security.Key;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author ryaki
 *
 * @param <T> the type of the object from which we generate the token - JWT payload
 * @param <ID> the type if the identifier - JWT subject
 */
public abstract class JwtUtil<T,ID> { 
	
	private String alg = SignatureAlgorithm.HS256.getJcaName();
	@Value("${jwt.util.secret.key}")
	private String secret;
	private Key key;
	@Value("${jwt.util.chrono.unit}")
	private String chronoUnit;	
	@Value("${jwt.util.chrono.unit.number}")
	private int unitsNumber;
	
	@PostConstruct
	public void init() {
		this.key=new SecretKeySpec(Base64.getDecoder().decode(secret), alg);
	}
	
	
}
