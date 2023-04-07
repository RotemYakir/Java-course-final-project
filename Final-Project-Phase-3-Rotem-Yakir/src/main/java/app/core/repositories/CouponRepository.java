package app.core.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Category;
import app.core.entities.Coupon;


/**
 * repository interface for coupon entity.
 * 
 * @author RotemYakir
 *
 */
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	/**
	 * checks if a specific coupon exists in the company by its title.
	 * 
	 * @param companyId the id of the company
	 * @param title     the title of the coupon
	 */
	boolean existsByTitleAndCompany_id(String title, int companyId);

	/**
	 * checks if a purchase of a specific coupon by a specific customer exists in
	 * the database
	 * 
	 * @param customerId the id of the customer
	 * @param couponId   the id of the coupon
	 */
	boolean existsByIdAndCustomersId(int couponId, int customerId);

	/**
	 * @param companyId the id of the company
	 * @return a list of all the coupons owned by the company.
	 */
	List<Coupon> findAllByCompany_id(int companyId);

	/**
	 * @param companyId the id of the company
	 * @param category  the wanted category
	 * @return a list of coupons owned by the company - of a specific category.
	 */
	List<Coupon> findAllByCompany_idAndCategory(int companyId, Category category);

	/**
	 * @param companyId the id of the company
	 * @param price     the wanted (maximum) price
	 * @return a list of coupons owned by the company - up to the inserted price.
	 */
	List<Coupon> findAllByCompany_idAndPriceLessThan(int companyId, double price);

	/**
	 * @param customerId the id of the customer
	 * @return a list of all the coupons owned by the customer
	 */
	List<Coupon> findAllByCustomersId(int customerId);

	/**
	 * @param customerId the id of the customer
	 * @param category   the wanted category
	 * @return a list of coupons owned by the customer - of a specific category
	 */
	List<Coupon> findAllByCustomersIdAndCategory(int customerId, Category category);

	/**
	 * @param customerId the id of the customer
	 * @param price      the wanted (maximum) price
	 * @return a list of coupons owned by the customer - up to the inserted price
	 */
	List<Coupon> findAllByCustomersIdAndPriceLessThan(int customerId, double price);

	/**
	 * checks if there are any expired coupons in the database and returns them if
	 * they exist.
	 * 
	 * @return a list of expired coupons
	 */
	List<Coupon> findAllByEndDateBefore(LocalDate now);

}