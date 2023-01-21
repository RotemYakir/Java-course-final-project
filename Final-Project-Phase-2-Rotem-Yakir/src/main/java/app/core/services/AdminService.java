package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class AdminService extends ClientService {

	private final String email = "admin@admin.com";
	private final String password = "admin";

	/**
	 * compares email and password given by the administrator to the permanent email
	 * and password which are set in this class
	 */
	@Override
	public boolean login(String email, String password) {
		return this.email.equals(email) && this.password.equals(password);
	}

	/**
	 * creates a new company and adds it to the database
	 * 
	 * @param company the company details
	 * @return object of the company that was added, with a generated id from the
	 *         database
	 * @throws CouponSystemException if company name is taken / company email is
	 *                               taken / the given company is not found
	 */
	public Company addCompany(Company company) throws CouponSystemException {
		if (company != null) {
			boolean isNameTaken = companyRepo.existsByName(company.getName());
			boolean isEmailTaken = companyRepo.existsByEmail(company.getEmail());
			if (isNameTaken) {
				throw new CouponSystemException("Failed to add the company - the name: " + company.getName()
						+ " already exists in the system. try to change the company name before you try again");
			}
			if (isEmailTaken) {
				throw new CouponSystemException("Failed to add the company - the email: " + company.getEmail()
						+ " already exists in the system. try to change the company email before you try again");
			}
			return companyRepo.save(company);
		} else {
			throw new CouponSystemException("Failed to add the company - company not found");
		}
	}

	/**
	 * updates a company
	 * 
	 * @param company the company with updated parameters
	 * @throws CouponSystemException if the user is trying to change company name/
	 *                               if the updated email is taken / if the given
	 *                               company is not found
	 */
	public Company updateCompany(Company company) throws CouponSystemException {
		if (company != null) {
			Optional<Company> opt = companyRepo.findById(company.getId());
			if (opt.isPresent()) {
				Company companyFromDb = opt.get();
				if (!(company.getName().equals(companyFromDb.getName()))) {
					throw new CouponSystemException("Failed to update the company - CANNOT UPDATE COMPANY NAME.");
				}
				if (companyRepo.existsByEmail(company.getEmail())
						&& !company.getEmail().equals(companyFromDb.getEmail())) {
					throw new CouponSystemException("Failed to update the company - the email: " + company.getEmail()
							+ " already exists in the system. try to change the email before you try again");
				}
				return companyRepo.save(company);
			} else {
				throw new CouponSystemException(
						"Failed to update company - company not found by id: " + company.getId());
			}
		} else {
			throw new CouponSystemException("Failed to update the company - the given company id null");
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
		if (companyRepo.existsById(companyId)) {
			companyRepo.deleteById(companyId); // TODO check if the method deletes all the purchase history and the
												// coupons
		} else {
			throw new CouponSystemException(
					"Failed to delete the company - the given id (" + companyId + ") was not found in the database.");
		}
	}

	/**
	 * @return a company with matching id from the database.
	 * @throws CouponSystemException - if company is not found by the given id.
	 */
	public Company getCompany(int companyId) {
		Optional<Company> opt = companyRepo.findById(companyId);
		if (opt.isEmpty()) {
			throw new CouponSystemException("company not found by id: " + companyId);
		} else {
			return opt.get();
		}
	}

	/**
	 * @return a list of all the companies from the database.
	 */
	public List<Company> getAllCompanies() {
		return companyRepo.findAll();
	}

	/**
	 * adds a new customer to the database
	 * 
	 * @param customer the customer details
	 * @return object of the customer that was added, with a generated id from the
	 *         database
	 * @throws CouponSystemException if customers email is taken / the given
	 *                               customer is null
	 */
	public Customer addCustomer(Customer customer) {
		if (customer != null) {
			if (customerRepo.existsByEmail(customer.getEmail())) {
				throw new CouponSystemException("Falied to add the customer - the email: " + customer.getEmail()
						+ " already exists in the system. try to change the customers email before you try again.");
			} else {
				return customerRepo.save(customer);
			}
		} else {
			throw new CouponSystemException("Failed to add the customer - the given customer is null");
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
	public Customer updateCustomer(Customer customer) {
		if(customer!=null) {
		Optional<Customer> opt = customerRepo.findById(customer.getId());
		if (opt.isPresent()) {
			Customer customerFromDb = opt.get();
			if (customerRepo.existsByEmail(customer.getEmail())
					&& !customerFromDb.getEmail().equals(customer.getEmail())) {
				throw new CouponSystemException("Falied to update the customer - the email: " + customer.getEmail()
						+ " is already taken. try to change the email before you try again.");
			} else {
				return customerRepo.save(customer);
			}
		} else {
			throw new CouponSystemException("Failed to update the customer - not found by id: " + customer.getId());
	}
		}else {
			throw new CouponSystemException("Failed to update the customer - the given customer id null");		}
		}

	/**
	 * deletes a customer along with the history of the purchases of coupons
	 * 
	 * @param customerId
	 * @throws CouponSystemException if customer is not found by the given id.
	 */
	public void deleteCustomer(int customerId) { // TODO check if the method deletes all the purchase history
		if (customerRepo.existsById(customerId)) {
			customerRepo.deleteById(customerId);
		} else {
			throw new CouponSystemException(
					"Failed to delete the customer - the given id (" + customerId + ") was not found in the database.");
		}
	}

	/**
	 * @return a customer with matching id from the database.
	 * @throws CouponSystemException - if customer is not found by the given id.
	 */
	public Customer getCustomer(int customerId) {
		Optional<Customer> opt = customerRepo.findById(customerId);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new CouponSystemException("customer not found by the given id: " + customerId);
		}
	}

	/**
	 * @return a list of all the customers from the database.
	 */
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

}
