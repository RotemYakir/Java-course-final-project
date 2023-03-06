package app.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Company;

/**
 * repository interface for company entity.
 * 
 * @author RotemYakir
 *
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	/**
	 * checks if there is a company with the matching email in the database.
	 * 
	 * @param email the email of the company
	 * 
	 */
	boolean existsByEmail(String email);

	/**
	 * checks if there is a company with the matching name in the database.
	 * 
	 * @param name the name of the company
	 */
	boolean existsByName(String name);

	/**
	 * @param email    the email of the company
	 * @param password the password of the company
	 * @return optional object of company - with matching email and password
	 */
	Optional<Company> findByEmailAndPassword(String email, String password);

}