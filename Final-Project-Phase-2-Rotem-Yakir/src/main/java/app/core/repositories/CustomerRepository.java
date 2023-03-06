package app.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Customer;


/**
 * repository interface for customer entity.
 * 
 * @author RotemYakir
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	/**
	 * @param email    the email of the customer
	 * @param password the password of the customer
	 * @return an optional object of customer - with matching email and password
	 */
	Optional<Customer> findByEmailAndPassword(String email, String password);

	/**
	 * checks if there is a customer with matching email in the database.
	 * 
	 * @param email
	 * @return the result of the check
	 */
	boolean existsByEmail(String email);

}