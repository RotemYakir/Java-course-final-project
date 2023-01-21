package app.core.facade;

import java.time.LocalDate;
import java.util.List;

import app.core.beans.Category;
import app.core.beans.Company;
import app.core.beans.Coupon;
import app.core.exceptions.CouponSystemException;

/**
 * implements a company facade to handle business logic operations
 * 
 * @author RotemYakir
 *
 */
public class CompanyFacade extends ClientFacade {

	private int companyId;

	/**
	 * constructs a companyFacade
	 */
	public CompanyFacade() {
	}

	/**
	 * compares email and password given by the company to the email and password
	 * stored in the database
	 */
	@Override
	public boolean login(String email, String password) {
		if (companiesDao.isCompanyExists(email, password)) {
			Company company = companiesDao.getCompanyByEmailAndPassword(email, password);
			this.companyId = company.getId();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * creates a new coupon and adds it to the database.
	 * 
	 * @param coupon the coupon details
	 * @return object of the coupon that was added, with a generated id from the
	 *         database.
	 * @throws CouponSystemException if coupons does not exist / the company already
	 *                               have a coupon of the same title / coupon is
	 *                               expired / coupon amount is negative.
	 */
	public Coupon addNewCoupon(Coupon coupon) throws CouponSystemException {
		if (coupon != null) {
			if (couponsDao.isCouponExistsInCompanyByTitle(this.companyId, coupon.getTitle())) {
				throw new CouponSystemException("FAILED to add new coupon. coupon title: " + coupon.getTitle()
						+ " already exists in the company.");
			}
			if (coupon.getEndDate().isBefore(LocalDate.now())) {
				throw new CouponSystemException(
						"FAILED to add new coupon. The expiration date of the coupon has passed.");
			}
			if (coupon.getAmount() < 0) {
				throw new CouponSystemException("FAILED to add the coupon - amount cannot be negative.");
			}
			return couponsDao.addCoupon(coupon);
		} else {
			throw new CouponSystemException("Failed to add the coupon - coupon not found");
		}
	}

	/**
	 * updates a coupon
	 * 
	 * @param coupon the coupon with updated parameters.
	 * @throws CouponSystemException if coupon does not exist / the company doesn't
	 *                               own the coupon / coupon's company_id was
	 *                               changed / coupon is expired / coupon amount is
	 *                               negative.
	 */
	public void updateCoupon(Coupon coupon) {
		if (coupon != null && couponsDao.isCouponExists(coupon.getId())) {
			Coupon couponFromDB = couponsDao.getCouponById(coupon.getId());
			if (couponFromDB.getCompanyId() != this.companyId) {
				throw new CouponSystemException("FAILED to update the coupon. the company doesn't own the coupon.");
			}
			if (couponsDao.isCouponExistsInCompanyByTitle(this.companyId, coupon.getTitle())
					&& !coupon.getTitle().equals(couponFromDB.getTitle())) {
				throw new CouponSystemException("FAILED to update the coupon (id: " + coupon.getId()
						+ ") coupon title: " + coupon.getTitle() + " already exists in the company.");
			}
			if (coupon.getCompanyId() != couponFromDB.getCompanyId()) {
				throw new CouponSystemException("FAILED to update the coupon - cannot change company id.");
			}
			if (coupon.getEndDate().isBefore(LocalDate.now())) {
				throw new CouponSystemException(
						"FAILED to update the coupon. The expiration date of the coupon has passed. ("
								+ coupon.getEndDate() + ")");
			}
			if (coupon.getAmount() < 0) {
				throw new CouponSystemException("FAILED to update the coupon - amount cannot be negative.");
			}
			couponsDao.updateCoupon(coupon);
		} else {
			throw new CouponSystemException("Failed to update coupon - not found");
		}
	}

	/**
	 * deletes a coupon and its purchases history
	 * 
	 * @param CouponId
	 * @throws CouponSystemException if the company doesn't own the coupon / coupon
	 *                               is not found.
	 */
	public void deleteCoupon(int CouponId) {
		if (couponsDao.isCouponExists(CouponId)) {
			Coupon couponToDelete = couponsDao.getCouponById(CouponId);
			if (couponToDelete.getCompanyId() != this.companyId) {
				throw new CouponSystemException(
						"Failed to delete coupon id: " + CouponId + " the company doesn't own the coupon.");
			}
			couponsDao.deleteAllPurchasesOfCoupon(CouponId);
			couponsDao.deleteCouponById(CouponId);
		} else {
			throw new CouponSystemException("Failed to delete coupon id: " + CouponId + " coupon not found.");
		}
	}

	/**
	 * @return a list of coupons owned by the company
	 */
	public List<Coupon> getCoupons() {
		return companiesDao.getAllCouponsOfCompany(this.companyId);
	}

	/**
	 * @param category
	 * @return a list of coupons owned by the company - of a specific category.
	 */
	public List<Coupon> getCouponsByCategory(Category category) {
		return companiesDao.getAllCouponsOfCompanyByCategory(companyId, category);
	}

	/**
	 * @param price
	 * @return a list of coupons owned by the company - up to the inserted price.
	 */
	public List<Coupon> getCouponsUpToMaxPrice(double price) {
		return companiesDao.getCouponsUpToPrice(companyId, price);
	}

	/**
	 * @return company details
	 */
	public Company getCompanyDetails() {
		return companiesDao.getCompanyById(companyId);
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getCompanyId() {
		return companyId;
	}
}
