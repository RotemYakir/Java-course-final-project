package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.services.CompanyService;

@RequestMapping("/coupon-system/company")
@RestController
public class CompanyController {

	@Autowired
	CompanyService service;

	// TODO create a login method that returns a token.

	@PutMapping("/login")
	public boolean login(String email, String password) {
		try {
			return service.login(email, password);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PostMapping("/add-coupon") // DOESNT WORK!!!
	public Coupon addNewCoupon(@RequestBody Coupon coupon) {
		try {
			return service.addNewCoupon(coupon);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/update-coupon")
	public Coupon updateCoupon(@RequestBody Coupon coupon) {
		try {
			return service.updateCoupon(coupon);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("delete-coupon")
	public void deleteCoupon(int couponId) {
		try {
			service.deleteCoupon(couponId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("get-all-coupons")
	public List<Coupon> getAllCoupons() {
		return service.getAllCoupons();
	}

	@GetMapping("get-coupons-by-category")
	public List<Coupon> getCouponsByCategory(Category category) {
		return service.getCouponsByCategory(category);
	}

}
