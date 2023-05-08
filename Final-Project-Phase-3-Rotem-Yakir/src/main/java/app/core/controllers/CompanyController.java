package app.core.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.auth.client.User;
import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.services.CompanyService;

@RequestMapping("/api/company")
@RestController
@CrossOrigin
public class CompanyController {

	@Autowired
	CompanyService service;

	@PostMapping(path = "/add-coupon", headers = HttpHeaders.AUTHORIZATION)
	public Coupon addNewCoupon(@RequestBody Coupon coupon, HttpServletRequest req) {
		try {
			int userId= (int) ((User) req.getAttribute("user")).getId();
			return service.addNewCoupon(coupon, userId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(path = "/update-coupon", headers = HttpHeaders.AUTHORIZATION)
	public Coupon updateCoupon(@RequestBody Coupon coupon, HttpServletRequest req) {
		try {
			int userId= (int) ((User) req.getAttribute("user")).getId();
			return service.updateCoupon(coupon, userId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(path = "delete-coupon", headers = HttpHeaders.AUTHORIZATION)
	public void deleteCoupon(int couponId, HttpServletRequest req) {
		try {
			int userId= (int) ((User) req.getAttribute("user")).getId();
			service.deleteCoupon(couponId, userId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "get-all-coupons", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getAllCoupons(HttpServletRequest req) {
		try {
			int userId= (int) ((User) req.getAttribute("user")).getId();
			return service.getAllCoupons(userId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "get-coupons-by-category", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getCouponsByCategory(Category category, HttpServletRequest req) {
		try {
			int userId= (int) ((User) req.getAttribute("user")).getId();
			return service.getCouponsByCategory(category, userId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "/get-coupons-max-price", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getCouponsUpToMaxPrice(double price, HttpServletRequest req) {
		try {
			int userId= (int) ((User) req.getAttribute("user")).getId();
			return service.getCouponsUpToMaxPrice(price,userId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "/get-details", headers = HttpHeaders.AUTHORIZATION)
	public Company getCompanyDetails(HttpServletRequest req) {
		try {
			int userId= (int) ((User) req.getAttribute("user")).getId();
			return service.getCompanyDetails(userId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}
