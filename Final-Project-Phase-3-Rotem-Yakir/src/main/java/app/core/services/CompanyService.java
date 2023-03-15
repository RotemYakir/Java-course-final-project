package app.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
@Scope("prototype")
@Transactional
public class CompanyService extends ClientService {

	private int companyId;

	/**
	 * compares email and password given by the company to the email and password
	 * stored in the database
	 */
	@Override
	public boolean login(String email, String password) {
		Optional<Company> opt = companyRepo.findByEmailAndPassword(email, password);
		if (opt.isPresent()) {
			this.companyId = opt.get().getId();
		}
		return opt.isPresent();
	}

	/**
	 * creates a new coupon and adds it to the database.
	 * 
	 * @param coupon the coupon details
	 * @return object of the coupon that was added, with a generated id from the
	 *         database.
	 * @throws CouponSystemException if couponId already exists in the system / the company already
	 *                               have a coupon of the same title / coupon is
	 *                               expired / coupon amount is negative.
	 */
	public Coupon addNewCoupon(Coupon coupon) {
		if (couponRepo.existsById(coupon.getId())) {
			throw new CouponSystemException(
					"Failed to add the coupon - coupon with id: " + coupon.getId() + " already exists in the system.");
		}
		if (couponRepo.existsByTitleAndCompany_id(coupon.getTitle(), this.companyId)) { //this.companyId)) {
			throw new CouponSystemException(
					"FAILED to add new coupon. coupon title: " + coupon.getTitle() + " already exists in the company.");
		}
		if (coupon.getEndDate().isBefore(LocalDate.now())) {
			throw new CouponSystemException("FAILED to add new coupon. The expiration date of the coupon has passed.");
		}
		if (coupon.getAmount() < 0) {
			throw new CouponSystemException("FAILED to add the coupon - amount cannot be negative.");
		}
		Company company = companyRepo.findById(this.companyId).get();
		company.addCoupon(coupon);
		return couponRepo.save(coupon);
	}

	public Coupon updateCoupon(Coupon coupon) {
		Optional<Coupon> opt = couponRepo.findById(coupon.getId());
		if (opt.isPresent()) {
			Coupon couponFromDb = opt.get();
			if (couponFromDb.getCompanyId() != this.companyId) {
				throw new CouponSystemException("Failed to update the coupon. the company doesn't own the coupon.");
			}
			if (couponRepo.existsByTitleAndCompany_id(coupon.getTitle(), this.companyId)
					&& !coupon.getTitle().equals(couponFromDb.getTitle())) {
				throw new CouponSystemException("Failed to update the coupon (id: " + coupon.getId()
						+ ") coupon title: " + coupon.getTitle() + " already exists in the company.");
			}
			if (coupon.getCompanyId() != couponFromDb.getCompanyId()) {
				throw new CouponSystemException("Failed to update the coupon - cannot change company id.");
			}
			if (coupon.getEndDate().isBefore(LocalDate.now())) {
				throw new CouponSystemException(
						"Failed to update the coupon. The expiration date of the coupon has passed. ("
								+ coupon.getEndDate() + ")");
			}
			if (coupon.getAmount() < 0) {
				throw new CouponSystemException("Failed to update the coupon - amount cannot be negative.");
			}
			return couponRepo.save(coupon);
		} else {
			throw new CouponSystemException(
					"failed to update the coupon - coupon doesn't exist by id: " + coupon.getId());
		}
	}

	

	/**
	 * deletes a coupon and its purchases history
	 * 
	 * @param CouponId
	 * @throws CouponSystemException if the company doesn't own the coupon / coupon
	 *                               is not found.
	 */
	public void deleteCoupon(int couponId) { // TODO check if cascade works
		Optional<Coupon> opt = couponRepo.findById(couponId);
		if (opt.isPresent()) {
			Coupon couponToDelete = opt.get();
			if (couponToDelete.getCompanyId() != this.companyId) {
				throw new CouponSystemException(
						"Failed to delete coupon id: " + couponId + " the company doesn't own the coupon.");
			}else {				
			couponRepo.deleteById(couponId);
			}
		} else {
			throw new CouponSystemException("Failed to delete coupon id: " + couponId + " coupon not found.");
		}
	}

	/**
	 * @return a list of coupons owned by the company
	 */
	public List<Coupon> getAllCoupons() {
		return couponRepo.findAllByCompany_id(this.companyId);
	}

	/**
	 * @param category
	 * @return a list of coupons owned by the company - of a specific category.
	 */
	public List<Coupon> getCouponsByCategory(Category category) {
		return couponRepo.findAllByCompany_idAndCategory(this.companyId, category);
	}

	/**
	 * @param price
	 * @return a list of coupons owned by the company - up to the inserted price.
	 */
	public List<Coupon> getCouponsUpToMaxPrice(double price) {
		return couponRepo.findAllByCompany_idAndPriceLessThan(this.companyId, price);
	}

	/**
	 * @return company details
	 */
	public Company getCompanyDetails() {
		return companyRepo.findById(this.companyId).get(); // the company must exist by this Id.
	}

}
