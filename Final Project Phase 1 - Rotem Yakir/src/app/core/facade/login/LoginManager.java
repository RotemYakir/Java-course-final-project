package app.core.facade.login;

import app.core.database.CompaniesDBDAO;
import app.core.database.CustomersDBDAO;
import app.core.facade.AdminFacade;
import app.core.facade.ClientFacade;
import app.core.facade.CompanyFacade;
import app.core.facade.CustomerFacade;

/**
 * enables clients to enter the system 
 * @author RotemYakir
 *
 */
public class LoginManager {

	private static LoginManager instance;

	/**
	 * constructs a login manager
	 */
	private LoginManager() {
	}

	/**
	 * a static method to initialize the LoginManager (if needed).
	 * @return the (single) instance of the LoginManager
	 */
	public static LoginManager getInstance() {
		if (instance == null) {
			instance = new LoginManager();
		}
		return instance;
	}

	/**
	 * compares email and password given by the client to the email and password
	 * stored in the system, and returning a fitting client facade if login succeeded.
	 * @param email client email
	 * @param password client password
	 * @param clientType client type
	 * @return ClientFacade object / null
	 */
	public ClientFacade login(String email, String password, ClientType clientType) {
		switch (clientType) {
		case ADMIN: {
			AdminFacade client = new AdminFacade();
			if (client.login(email, password)) {
				return client;
			} else {
				return null;
			}
		}
		case COMPANY: {
			CompaniesDBDAO companiesDao = new CompaniesDBDAO();
			CompanyFacade client = new CompanyFacade();
			if (client.login(email, password)) {
				int companyId = companiesDao.getCompanyByEmailAndPassword(email, password).getId();
				client.setCompanyId(companyId);
				return client;
			} else {
				return null;
			}
		}
		case CUSTOMER:{
			CustomersDBDAO customersDao = new CustomersDBDAO();
			CustomerFacade client = new CustomerFacade();
			if(client.login(email, password)) {
				int customerId = customersDao.getCustomerByEmailAndPassword(email, password).getId();
				client.setCustomerId(customerId);
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
