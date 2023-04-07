package app.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import lombok.Getter;
import lombok.Setter;

/**
 * a client service of customer, to handle business logic operations/
 * 
 * @author RotemYakir
 *
 */
@Getter@Setter
@Service
@Scope("prototype")
@Transactional
public class CustomerService extends ClientService {

	private int customerId;

	/**
	 * compares email and password given by the customer to the email and password
	 * stored in the database
	 */
	@Override
	public boolean login(String email, String password) {
		Optional<Customer> opt = customerRepo.findByEmailAndPassword(email, password);
		if (opt.isPresent()) {
			this.customerId = opt.get().getId();
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
	public void purchaseCoupon(int couponId) throws CouponSystemException {
		Optional<Coupon> opt = couponRepo.findById(couponId);
		if (opt.isPresent()) {
			Coupon coupon = opt.get();
			if (couponRepo.existsByIdAndCustomersId(couponId, this.customerId)) {
				throw new CouponSystemException("Failed to purchase the coupon - you already have this coupon.");
			}
			if (coupon.getAmount() <= 0) {
				throw new CouponSystemException("Failed to purchase the coupon - coupon is out of stock");
			}
			if (coupon.getEndDate().isBefore(LocalDate.now())) {
				throw new CouponSystemException("Failed to purchase the coupon - coupon is expierd");
			}
			Customer customer = customerRepo.findById(this.customerId).get(); // the customer must exist by this id
			customer.addCoupon(coupon);
			coupon.setAmount(coupon.getAmount()-1);
		} else {
			throw new CouponSystemException("failed to purchase coupon - the coupon doesn't exist by id: "+couponId);
		}
	}

	/**
	 * @return a list of coupons owned by the customer
	 */
	public List<Coupon> getAllCoupons() {
		return couponRepo.findAllByCustomersId(this.customerId);
	}

	/**
	 * @param category
	 * @return a list of coupons owned by the customer - of a specific category.
	 */
	public List<Coupon> getCouponsByCategory(Category category) {
		return couponRepo.findAllByCustomersIdAndCategory(this.customerId, category);
	}

	/**
	 * @param price
	 * @return a list of coupons owned by the customer - up to the inserted price.
	 */
	public List<Coupon> getCouponsUpToMaxPrice(double price) {
		return couponRepo.findAllByCustomersIdAndPriceLessThan(this.customerId, price);
	}

	/**
	 * @return customer details
	 */
	public Customer getCustomerDetails() {
		return customerRepo.findById(this.customerId).get(); // the customer must exist by this Id.
	}

}
