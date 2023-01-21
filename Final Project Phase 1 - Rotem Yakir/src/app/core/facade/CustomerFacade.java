package app.core.facade;

import java.time.LocalDate;
import java.util.List;

import app.core.beans.Category;
import app.core.beans.Coupon;
import app.core.beans.Customer;
import app.core.exceptions.CouponSystemException;

/**
 * implements a customer facade to handle business logic operations
 * 
 * @author RotemYakir
 *
 */
public class CustomerFacade extends ClientFacade {

	private int customerId;

	/**
	 * constructs a customer facade
	 */
	public CustomerFacade() {
	}

	/**
	 * compares email and password given by the customer to the email and password
	 * stored in the database
	 */
	@Override
	public boolean login(String email, String password) {
		if (customersDao.isCustomerExists(email, password)) {
			Customer customer = customersDao.getCustomerByEmailAndPassword(email, password);
			this.customerId = customer.getId();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * purchase a coupon by a customer and record it in the database
	 * 
	 * @param coupon the coupon that's being purchased
	 * @throws CouponSystemException if coupon doesn't exist / coupon is out of
	 *                               stock (amount==0) / coupon is expired
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		if (coupon != null && couponsDao.isCouponExists(coupon.getId())) {
			Coupon couponFromDb = couponsDao.getCouponById(coupon.getId());
			if (couponsDao.isCouponPurchaseExists(customerId, coupon.getId())) {
				throw new CouponSystemException("Failed to purchase the coupon - you already have this coupon.");
			}
			if (couponFromDb.getAmount() <= 0) {
				throw new CouponSystemException("Failed to purchase the coupon - coupon is out of stock");
			}
			if (couponFromDb.getEndDate().isBefore(LocalDate.now())) {
				throw new CouponSystemException("Failed to purchase the coupon - coupon is expierd");
			}
			couponsDao.addCouponPurchase(customerId, coupon.getId());
			couponFromDb.setAmount(couponFromDb.getAmount() - 1);
			couponsDao.updateCoupon(couponFromDb);
		} else {
			throw new CouponSystemException("failed to purchase coupon - the coupon doesn't exist.");
		}
	}

	/**
	 * @return a list of coupons owned by the customer
	 */
	public List<Coupon> getAllCoupons() {
		return customersDao.getAllCustomerCoupons(this.customerId);
	}

	/**
	 * @param category
	 * @return a list of coupons owned by the customer - of a specific category.
	 */
	public List<Coupon> getCouponsOfCategory(Category category) {
		return customersDao.getCouponsByCategory(this.customerId, category);
	}

	/**
	 * 
	 * @param price
	 * @return a list of coupons owned by the customer - up to the inserted price.
	 */
	public List<Coupon> getCouponsUpToMaxPrice(double price) {
		return customersDao.getCouponsUpToPrice(customerId, price);
	}

	/**
	 * @return customer details
	 */
	public Customer getCustomerDetails() {
		return customersDao.getCustomerById(this.customerId);
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getCustomerId() {
		return customerId;
	}

}
