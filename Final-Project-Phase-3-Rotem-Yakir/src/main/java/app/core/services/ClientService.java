package app.core.services;

import org.springframework.beans.factory.annotation.Autowired;

import app.core.auth.UserCredentials;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;


/**
 * an abstract class to the service clients
 * 
 * @author RotemYakir
 *
 */
public abstract class ClientService  {

	@Autowired
	protected CompanyRepository companyRepo;
	@Autowired
	protected CustomerRepository customerRepo;
	@Autowired
	protected CouponRepository couponRepo;


	/**
	 * compares email and password given by the company to the email and password
	 * stored in the database
	 */
	public abstract String login(UserCredentials credentials);
	

}

