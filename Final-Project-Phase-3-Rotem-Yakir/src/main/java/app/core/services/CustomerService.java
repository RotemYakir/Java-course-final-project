package app.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

/**
 * a client service of customer, to handle business logic operations/
 * 
 * @author RotemYakir
 *
 */
@Service
@Transactional
public class CustomerService extends ClientService {

	/**
	 * compares email and password given by the customer to the email and password
	 * stored in the database
	 */
	@Override
	public boolean login(String email, String password) {
		Optional<Customer> opt = customerRepo.findByEmailAndPassword(email, password);
		if (opt.isPresent()) {
//			this.customerId = opt.get().getId();
		}
		return opt.isPresent();
	}

	/**
	 * purchase a coupon by a customer and record it in the database
	 * 
	 * @param coupon the coupon that's being purchased
	 * @throws CouponSystemException if coupon doesn't exist / coupon is out of
	 *                               stock (amount==0) / coupon is expired
	 */
	public void purchaseCoupon(int couponId, int customerId) throws CouponSystemException {
		if (!customerRepo.existsById(customerId)) {
			throw new CouponSystemException(
					"Failed to purchase the coupon. a customer with id: " + customerId + " doesn't exist.");
		}
		Optional<Coupon> opt = couponRepo.findById(couponId);
		if (opt.isPresent()) {
			Coupon coupon = opt.get();
			if (couponRepo.existsByIdAndCustomersId(couponId, customerId)) {
				throw new CouponSystemException("Failed to purchase the coupon - you already have this coupon.");
			}
			if (coupon.getAmount() <= 0) {
				throw new CouponSystemException("Failed to purchase the coupon - coupon is out of stock");
			}
			if (coupon.getEndDate().isBefore(LocalDate.now())) {
				throw new CouponSystemException("Failed to purchase the coupon - coupon is expierd");
			}
			Customer customer = customerRepo.findById(customerId).get(); // the customer must exist by this id
			customer.addCoupon(coupon);
			coupon.setAmount(coupon.getAmount() - 1);
		} else {
			throw new CouponSystemException("failed to purchase coupon - the coupon doesn't exist by id: " + couponId);
		}
	}

	/**
	 * @return a list of coupons owned by the customer
	 * @throws CouponSystemException if the customer doesn't exist by the inserted
	 *                               id
	 */
	public List<Coupon> getAllCoupons(int customerId) {
		if (customerRepo.existsById(customerId)) {
			return couponRepo.findAllByCustomersId(customerId);
		} else {
			throw new CouponSystemException(
					"Failed to get the coupons. a customer with id: " + customerId + " doesn't exist");
		}
	}

	/**
	 * @param category
	 * @return a list of coupons owned by the customer - of a specific category.
	 * @throws CouponSystemException if the customer doesn't exist by the inserted
	 *                               id
	 */
	public List<Coupon> getCouponsByCategory(Category category, int customerId) {
		if (customerRepo.existsById(customerId)) {
			return couponRepo.findAllByCustomersIdAndCategory(customerId, category);
		} else {
			throw new CouponSystemException(
					"Failed to get the coupons. a customer with id: " + customerId + " doesn't exist");
		}
	}

	/**
	 * @param price
	 * @return a list of coupons owned by the customer - up to the inserted price.
	 * @throws CouponSystemException if the customer doesn't exist by the inserted
	 *                               id
	 */
	public List<Coupon> getCouponsUpToMaxPrice(double price, int customerId) {
		if (customerRepo.existsById(customerId)) {
			return couponRepo.findAllByCustomersIdAndPriceLessThan(customerId, price);
		} else {
			throw new CouponSystemException(
					"Failed to get the coupons. a customer with id: " + customerId + " doesn't exist");
		}
	}

	/**
	 * @return customer details
	 * @throws CouponSystemException if the customer doesn't exist by the inserted
	 *                               id
	 */
	public Customer getCustomerDetails(int customerId) {
		return customerRepo.findById(customerId).orElseThrow(() -> new CouponSystemException(
				"Failed to get the customer's details. a customer with id: " + customerId + " doesn't exist"));
	}

}
