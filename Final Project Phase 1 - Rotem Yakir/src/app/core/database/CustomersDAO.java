package app.core.database;

import java.util.List;

import app.core.beans.Category;
import app.core.beans.Coupon;
import app.core.beans.Customer;

/**
 * an interface of a Data Access Object
 * 
 * @author RotemYakir
 */
public interface CustomersDAO {

	/**
	 * checks if there is a customer with the matching email and password in the
	 * database.
	 * 
	 * @param email
	 * @param password
	 * @return the result of the check
	 */
	public boolean isCustomerExists(String email, String password);

	/**
	 * adds a customer to the database.
	 * 
	 * @param customer customer to add to the database
	 * @return the customer that has been added with their generated id.
	 */
	public Customer addCustomer(Customer customer);

	/**
	 * updates the customer (using customer id to get it from the database).
	 * 
	 * @param customer the customer that needs to be updated
	 */
	public void updateCustomer(Customer customer);

	/**
	 * deletes the customer with the matching id from the database.
	 * 
	 * @param customerId the id of the customer
	 */
	public void deleteCustomerById(int customerId);

	/**
	 * @return a list of all the customers
	 */
	public List<Customer> getAllCustomers();

	/**
	 * @param customerId the id of the customer
	 * @return a customer with matching id from the database
	 */
	public Customer getCustomerById(int customerId);

	/**
	 * checks if there is a customer with matching email in the database.
	 * 
	 * @param email
	 * @return the result of the check
	 */
	boolean isCustomerExistsByEmail(String email);

	/**
	 * @param email    the email of the customer
	 * @param password the password of the customer
	 * @return a customer with matching email and password
	 */
	Customer getCustomerByEmailAndPassword(String email, String password);

	/**
	 * @param customerId the id of the customer
	 * @return a list of all the coupons owned by the customer
	 */
	List<Coupon> getAllCustomerCoupons(int customerId);

	/**
	 * @param customerId the id of the customer
	 * @param category   the wanted category
	 * @return a list of coupons owned by the customer - of a specific category
	 */
	List<Coupon> getCouponsByCategory(int customerId, Category category);

	/**
	 * @param customerId the id of the customer
	 * @param price      the wanted (maximum) price
	 * @return a list of coupons owned by the customer - up to the inserted price
	 */
	List<Coupon> getCouponsUpToPrice(int customerId, double price);

	/**
	 * deletes all the coupons owned by the customer from the database.
	 * 
	 * @param cusrtomerId the id of the customer
	 */
	void deleteCouponPurchasesOfCustomer(int customerId);

	/**
	 * checks if a customer with matching id exists in the database
	 * @param customerId the customer id
	 * @return the result of the check
	 */
	boolean isCustomerExists(int customerId);

}
