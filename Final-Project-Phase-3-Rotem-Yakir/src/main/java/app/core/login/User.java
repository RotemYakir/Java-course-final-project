package app.core.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class User {
	
	
	private int id;
	private String email;
	ClientType clientType;
	

}
