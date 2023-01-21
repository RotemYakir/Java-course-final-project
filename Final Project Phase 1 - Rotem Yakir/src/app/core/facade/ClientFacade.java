package app.core.facade;

import app.core.database.CompaniesDBDAO;
import app.core.database.CouponsDBDAO;
import app.core.database.CustomersDBDAO;

/**
 * an abstract class to the facade clients
 * 
 * @author RotemYakir
 *
 */
public abstract class ClientFacade {

	/**
	 * login to the system by email and password //compare email and password given
	 * by the user to the email and password stored in the database
	 * 
	 * @param email    the email of the client
	 * @param password the password of the client
	 * @return the result of the comparison
	 */
	public abstract boolean login(String email, String password);

	/**
	 * a reference to company DAO
	 */
	protected CompaniesDBDAO companiesDao = new CompaniesDBDAO();
	/**
	 * a reference to coupon DAO
	 */
	protected CouponsDBDAO couponsDao = new CouponsDBDAO();
	/**
	 * a reference to customer DAO
	 */
	protected CustomersDBDAO customersDao = new CustomersDBDAO();

}
