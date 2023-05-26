package app.core.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.auth.client.User;
import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.CustomerService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerController {

	@Autowired
	CustomerService service;



	@PutMapping(path = "/purchase-coupon", headers = HttpHeaders.AUTHORIZATION)
	public void purchaseCoupon(int couponId, HttpServletRequest req) {
		try {
			int userId= (int) ((User) req.getAttribute("user")).getId();
			service.purchaseCoupon(couponId, userId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "/get-coupons", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getCoupons(HttpServletRequest req) {
		try {
			int userId= (int) ((User) req.getAttribute("user")).getId();
			return service.getCoupons(userId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "/get-coupons-by-category", headers = HttpHeaders.AUTHORIZATION)
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
			return service.getCouponsUpToMaxPrice(price, userId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
}

	@GetMapping(path = "/get-all-coupons", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getAllCoupons() {
		try {
			return service.getAllCoupons();
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/get-details", headers = HttpHeaders.AUTHORIZATION)
	public Customer getCustomerDetails(HttpServletRequest req) {
		try {
			int userId= (int) ((User) req.getAttribute("user")).getId();
			return service.getCustomerDetails(userId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}
