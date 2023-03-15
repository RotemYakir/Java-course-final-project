package app.core.database;

import java.util.List;

import app.core.beans.Coupon;
import app.core.exceptions.CouponSystemException;

/**
 * an interface of a Data Access Object
 * 
 * @author RotemYakir
 */
public interface CouponsDAO {

	/**
	 * adds a coupon to the database.
	 * 
	 * @param coupon coupon to add to the database.
	 * @return the coupon that has been added with its generated id.
	 */
	public Coupon addCoupon(Coupon coupon);

	/**
	 * updates the coupon using (using coupon.id to get it from the database)
	 * 
	 * @param coupon the coupon that needs to be updated
	 */
	public void updateCoupon(Coupon coupon);

	/**
	 * deletes the coupon with the matching id from the database
	 * 
	 * @param couponId the id of the coupon
	 */
	public void deleteCouponById(int couponId);

	/**
	 * @return a list of all the coupons from the database
	 */
	public List<Coupon> getAllCoupons();

	/**
	 * @param couponId the id of the coupon
	 * @return a coupon with matching id from the database
	 */
	public Coupon getCouponById(int couponId);

	/**
	 * adds a purchase record of a coupon by a customer to the database
	 * customers_vs_coupons.
	 * 
	 * @param customerId the id of the customer who has purchased the coupon
	 * @param couponId   the id of the coupon that has been purchased by the
	 *                   customer
	 */
	public void addCouponPurchase(int customerId, int couponId);

	/**
	 * deletes a purchase record of a coupon by a customer from the database.
	 * 
	 * @param customerId the id of the customer who has purchased the coupon
	 * @param couponId   the id of the coupon that has been purchased by the
	 *                   customer
	 */
	public void deleteCouponPurchase(int customerId, int couponId);

	/**
	 * deletes all the expired coupons from the database.
	 */
	void deleteAllExpiredCoupons();

	/**
	 * deletes all the expired coupons purchase history from the database.
	 */
	void deleteAllExpiredCouponsHistory();

	/**
	 * checks if there are any expired coupons in the database
	 * 
	 * @return the result of the check
	 */
	boolean isExpiredCouponExists();

	/**
	 * deletes from the database all purchases of the coupon with the matching id.
	 * 
	 * @param couponId the id of the coupon
	 */
	void deleteAllPurchasesOfCoupon(int couponId);

	/**
	 * checks if a purchase of a specific coupon by a specific customer exists in
	 * the database
	 * 
	 * @param customerId the id of the customer
	 * @param couponId   the id of the coupon
	 * @return the result of the check
	 */
	boolean isCouponPurchaseExists(int customerId, int couponId);

	/**
	 * deletes from the database all coupons purchases of a specific company.
	 * 
	 * @param companyId the id of the company
	 */
	void deleteCouponPurchasesOfCompany(int companyId);

	/**
	 * checks if a specific coupon exists in the company by its title.
	 * 
	 * @param companyId   the id of the company
	 * @param couponTitle the title of the coupon
	 * @return the result of the check
	 * @throws CouponSystemException
	 */
	boolean isCouponExistsInCompanyByTitle(int companyId, String couponTitle) throws CouponSystemException;

	/**
	 * checks if a coupon with matching id exists in the database
	 * 
	 * @param couponId the coupon id
	 * @return the result of the check
	 */
	boolean isCouponExists(int couponId);
}
