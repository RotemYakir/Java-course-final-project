package app.core.login.auth;

import app.core.login.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {

	private ClientType clientType;
	private String email;
	private String password;

}
