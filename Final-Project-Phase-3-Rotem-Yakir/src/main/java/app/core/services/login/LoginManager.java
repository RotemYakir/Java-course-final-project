package app.core.services.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import app.core.services.AdminService;
import app.core.services.ClientService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

/**
 * enables clients to enter the system 
 * @author RotemYakir
 *
 */
@Service
public class LoginManager {
	
	@Autowired
	private ApplicationContext ctx;
	
	/**
	 * compares email and password given by the client to the email and password
	 * stored in the system, and returning a fitting client service if login succeeded.
	 */
	public ClientService login(String email, String password, ClientType clientType) {
		switch (clientType) {
		case ADMIN: {
			AdminService client =  ctx.getBean(AdminService.class);
			if (client.login(email, password)) {
				return client;
			} else {
				return null;
			}
		}
		case COMPANY: {
			CompanyService client = ctx.getBean(CompanyService.class);
			if (client.login(email, password)) {
				return client;
			} else {
				return null;
			}
		}
		case CUSTOMER:{
			CustomerService client = ctx.getBean(CustomerService.class);
			if(client.login(email, password)) {
				return client;
			}else {
				return null;
			}
		}
		default:
			throw new IllegalArgumentException("Unexpected value");
		}
	}
	
	
}