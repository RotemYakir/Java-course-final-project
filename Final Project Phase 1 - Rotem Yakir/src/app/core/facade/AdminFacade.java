package app.core.facade;

import java.util.List;

import app.core.beans.Company;
import app.core.beans.Customer;
import app.core.exceptions.CouponSystemException;

/**
 * implements an administrator facade to handle business logic operations
 * 
 * @author RotemYakir
 *
 */
public class AdminFacade extends ClientFacade {

	private final String email = "admin@admin.com";
	private final String password = "admin";

	/**
	 * compares email and password given by the administrator to the permanent email
	 * and password which are set in the AdminFacade class
	 */
	@Override
	public boolean login(String email, String password) {
		return this.email.equals(email) && this.password.equals(password);
	}

	/**
	 * creates a new company and adds it to the database
	 * 
	 * @param company new company details
	 * @return object of the company that was added, with a generated id from the
	 *         database
	 * @throws CouponSystemException if company name is taken / company email is
	 *                               taken / the given company is not found
	 */
	public Company addCompany(Company company) throws CouponSystemException {
		if (company != null) {
			boolean isNameTaken = companiesDao.isCompanyExistsByName(company.getName());
			boolean isEmailTaken = companiesDao.isCompanyExistsByEmail(company.getEmail());
			if (isNameTaken) {
				throw new CouponSystemException("Failed to add the company - the name: " + company.getName()
						+ " already exists in the system. try to change the company name before you try again");
			}
			if (isEmailTaken) {
				throw new CouponSystemException("Failed to add the company - the email: " + company.getEmail()
						+ " already exists in the system. try to change the company email before you try again");
			}
			return companiesDao.addCompany(company);
		} else {
			throw new CouponSystemException("Failed to add the company - company not found");
		}
	}

	/**
	 * updates a company
	 * 
	 * @param company the company with updated parameters
	 * @throws CouponSystemException if the updated name is taken / user is trying
	 *                               to change company email to one that is already
	 *                               taken / the given company is not found
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		if (company != null && companiesDao.isCompanyExists(company.getId())) {
			Company companyFromDb = companiesDao.getCompanyById(company.getId());
			if (!(company.getName().equals(companyFromDb.getName()))) {
				throw new CouponSystemException("Failed to update the company - CANNOT UPDATE COMPANY NAME.");
			}
			if (companiesDao.isCompanyExistsByEmail(company.getEmail())
					&& !company.getEmail().equals(companyFromDb.getEmail())) {
				throw new CouponSystemException("Failed to update the company - the email: " + company.getEmail()
						+ " already exists in the system. try to change the email before you try again");
			}
			companiesDao.updateCompany(company);
		} else {
			throw new CouponSystemException("Failed to update company - company not found.");
		}
	}

	/**
	 * deletes the company, the coupons of the company, and the coupons purchases
	 * history by customers.
	 * 
	 * @param companyId
	 * @throws CouponSystemException if company is not found by the given id.
	 */
	public void deleteCompany(int companyId) throws CouponSystemException {
		if (companiesDao.isCompanyExists(companyId)) {
			couponsDao.deleteCouponPurchasesOfCompany(companyId);
			companiesDao.deleteCouponsByCompanyId(companyId);
			companiesDao.deleteCompanyById(companyId);
		} else {
			throw new CouponSystemException("Failed to delete company id: " + companyId + " company not found.");
		}
	}

	/**
	 * @return a company with matching id from the database.
	 * @throws CouponSystemException - if company is not found by the given id.
	 */
	public Company getCompany(int companyId) {
		if (companiesDao.isCompanyExists(companyId)) {
			return companiesDao.getCompanyById(companyId);
		} else {
			throw new CouponSystemException("company not found - id: " + companyId);
		}
	}

	/**
	 * @return a list of all the companies from the database.
	 */
	public List<Company> getAllCompanies() {
		return companiesDao.getAllCompanies();
	}

	/**
	 * adds a new customer to the database
	 * 
	 * @param customer the customer details
	 * @return object of the customer that was added, with a generated id from the
	 *         database
	 * @throws CouponSystemException if customers email is taken / the given
	 *                               customer is not found
	 */
	public Customer addCustomer(Customer customer) throws CouponSystemException {
		if (customer != null) {
			if (customersDao.isCustomerExistsByEmail(customer.getEmail())) {
				throw new CouponSystemException("Falied to add the customer - the email: " + customer.getEmail()
						+ " already exists in the system. try to change the customers email before you try again.");
			} else {
				return customersDao.addCustomer(customer);
			}
		} else {
			throw new CouponSystemException("Failed to add the customer - not found");
		}
	}

	/**
	 * updates a customer
	 * 
	 * @param customer a customer with updated parameters
	 * @throws CouponSystemException if user is trying to change customer email to
	 *                               one that is already taken / the given customer
	 *                               is not found.
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		if (customer != null && customersDao.isCustomerExists(customer.getId())) {
			Customer customerFromDb = customersDao.getCustomerById(customer.getId());
			if (customersDao.isCustomerExistsByEmail(customer.getEmail())
					&& !customer.getEmail().equals(customerFromDb.getEmail())) {
				throw new CouponSystemException("Falied to update the customer - the email: " + customer.getEmail()
						+ " already exists in the system. try to change the customers email before you try again.");
			} else {
				customersDao.updateCustomer(customer);
			}
		} else {
			throw new CouponSystemException("Failed to update the customer - not found");
		}
	}

	/**
	 * deletes a customer along with the history of the purchases of coupons
	 * 
	 * @param customerId
	 * @throws CouponSystemException if customer is not found by the given id.
	 */
	public void deleteCustomer(int customerId) {
		if (customersDao.isCustomerExists(customerId)) {
			customersDao.deleteCouponPurchasesOfCustomer(customerId);
			customersDao.deleteCustomerById(customerId);
		} else {
			throw new CouponSystemException("Failed to delete customer id: " + customerId + " - not found.");
		}
	}

	/**
	 * @return a customer with matching id from the database.
	 * @throws CouponSystemException - if customer is not found by the given id.
	 */
	public Customer getCustomer(int customerId) {
		if (customersDao.isCustomerExists(customerId)) {
			return customersDao.getCustomerById(customerId);
		} else {
			throw new CouponSystemException("customer not found - id: " + customerId);
		}
	}

	/**
	 * @return a list of all the customers from the database.
	 */
	public List<Customer> getAllcustomers() {
		return customersDao.getAllCustomers();
	}

}