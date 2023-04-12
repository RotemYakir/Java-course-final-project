package app.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.login.auth.JwtUtilCompany;
import app.core.login.auth.UserCredentials;

/**
 * a client service of company, to handle business logic operations/
 * 
 * @author RotemYakir
 *
 */

@Service
@Transactional
public class CompanyService extends ClientService {

	@Autowired
	JwtUtilCompany jwtUtil;
	
	/**
	 * compares email and password given by the company to the email and password
	 * stored in the database
	 */
	@Override
	public String login(UserCredentials credentials) {
		Optional<Company> opt = companyRepo.findByEmailAndPassword(credentials.getEmail(), credentials.getPassword());
		if (opt.isPresent()) {
			String token = jwtUtil.generateToken(opt.get());
			return token;
		}
		else {
			throw new CouponSystemException("Login failed: email or password are incorrect.");
		}
	}

	/**
	 * creates a new coupon and adds it to the database.
	 * 
	 * @param coupon the coupon details
	 * @return object of the coupon that was added, with a generated id from the
	 *         database.
	 * @throws CouponSystemException if couponId already exists in the system / the
	 *                               company already have a coupon of the same title
	 *                               / coupon is expired / coupon amount is
	 *                               negative.
	 */
	public Coupon addNewCoupon(Coupon coupon, int companyId) {
		Company companyFromDb = companyRepo.findById(companyId).orElseThrow(() -> new CouponSystemException(
				"Failed to add the coupon. a company with id: " + companyId + " doesn't exist"));
		if (couponRepo.existsById(coupon.getId())) {
			throw new CouponSystemException(
					"Failed to add the coupon - coupon with id: " + coupon.getId() + " already exists in the system.");
		}
		if (couponRepo.existsByTitleAndCompany_id(coupon.getTitle(), companyId)) {
			throw new CouponSystemException(
					"FAILED to add new coupon. coupon title: " + coupon.getTitle() + " already exists in the company.");
		}
		if (coupon.getEndDate().isBefore(LocalDate.now())) {
			throw new CouponSystemException("FAILED to add new coupon. The expiration date of the coupon has passed.");
		}
		if (coupon.getAmount() < 0) {
			throw new CouponSystemException("FAILED to add the coupon - amount cannot be negative.");
		}
		companyFromDb.addCoupon(coupon);
		return couponRepo.save(coupon);
	}

	/**
	 * updates a coupon
	 * 
	 * @param coupon the coupon with the updated parameters.
	 * @throws CouponSystemException if coupon does not exist / the company doesn't
	 *                               own the coupon / coupon's company_id was
	 *                               changed / coupon is expired / coupon amount is
	 *                               negative.
	 */
	public Coupon updateCoupon(Coupon coupon, int companyId) {
		if (!companyRepo.existsById(companyId)) {
			throw new CouponSystemException(
					"Failed to update the coupon. a company with id: " + companyId + " doesn't exist");
		}
		Optional<Coupon> opt = couponRepo.findById(coupon.getId());
		if (opt.isPresent()) {
			Coupon couponFromDb = opt.get();
			if (couponFromDb.getCompanyId() != companyId) {
				throw new CouponSystemException("Failed to update the coupon. the company doesn't own the coupon.");
			}
			if (couponRepo.existsByTitleAndCompany_id(coupon.getTitle(), companyId)
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
	 * @throws CouponSystemException if the company doesn't own the coupon / coupon
	 *                               is not found.
	 */
	public void deleteCoupon(int couponId, int companyId) {
		Optional<Coupon> opt = couponRepo.findById(couponId);
		if (opt.isPresent()) {
			Coupon couponToDelete = opt.get();
			if (couponToDelete.getCompanyId() != companyId) {
				throw new CouponSystemException(
						"Failed to delete coupon id: " + couponId + " the company doesn't own the coupon.");
			} else {
				couponRepo.deleteById(couponId);
			}
		} else {
			throw new CouponSystemException("Failed to delete coupon id: " + couponId + " coupon not found.");
		}
	}

	/**
	 * @return a list of coupons owned by the company
	 * @throws CouponSystemException if the company doesn't exist by the inserted id
	 */
	public List<Coupon> getAllCoupons(int companyId) {
		if (companyRepo.existsById(companyId)) {
			return couponRepo.findAllByCompany_id(companyId);
		} else {
			throw new CouponSystemException(
					"Failed to get the coupons. a company with id: " + companyId + " doesn't exist");
		}

	}

	/**
	 * @return a list of coupons owned by the company - of a specific category.
	 * @throws CouponSystemException if the company doesn't exist by the inserted id
	 */
	public List<Coupon> getCouponsByCategory(Category category, int companyId) {
		if (companyRepo.existsById(companyId)) {
			return couponRepo.findAllByCompany_idAndCategory(companyId, category);
		} else {
			throw new CouponSystemException(
					"Failed to get the coupons. a company with id: " + companyId + " doesn't exist");
		}
	}

	/**
	 * @return a list of coupons owned by the company - up to the inserted price.
	 * @throws CouponSystemException if the company doesn't exist by the inserted id
	 */
	public List<Coupon> getCouponsUpToMaxPrice(double price, int companyId) {
		if (companyRepo.existsById(companyId)) {
			return couponRepo.findAllByCompany_idAndPriceLessThan(companyId, price);
		} else {
			throw new CouponSystemException(
					"Failed to get the coupons. a company with id: " + companyId + " doesn't exist");
		}
	}

	/**
	 * @return company details
	 * @throws CouponSystemException if the company doesn't exist by the inserted id
	 */
	public Company getCompanyDetails(int companyId) {
		return companyRepo.findById(companyId).orElseThrow(() -> new CouponSystemException(
				"Failed to get the company's details. a company with id: " + companyId + " doesn't exist"));

	}

}