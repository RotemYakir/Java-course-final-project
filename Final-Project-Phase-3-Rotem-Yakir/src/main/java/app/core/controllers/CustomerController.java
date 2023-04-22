package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.auth.client.UserCredentials;
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

	@PostMapping("/login")
	public String login(@RequestBody UserCredentials credentials) {
		System.out.println(credentials);
		try {
			return service.login(credentials);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PutMapping(path = "/purchase-coupon", headers = HttpHeaders.AUTHORIZATION)
	public void purchaseCoupon(int couponId, int customerId) {
		try {
			service.purchaseCoupon(couponId, customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "/get-coupons", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getAllCoupons(int customerId) {
		try {
			return service.getAllCoupons(customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "/get-coupons-by-category", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getCouponsByCategory(Category category, int customerId) {
		try {
			return service.getCouponsByCategory(category, customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "/get-coupons-max-price", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getCouponsUpToMaxPrice(double price, int customerId) {
		try {
			return service.getCouponsUpToMaxPrice(price, customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "/get-details", headers = HttpHeaders.AUTHORIZATION)
	public Customer getCustomerDetails(int customerId) {
		try {
			return service.getCustomerDetails(customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}
