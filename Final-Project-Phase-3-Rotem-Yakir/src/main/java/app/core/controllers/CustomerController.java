package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.CustomerService;

@RestController
@RequestMapping("/coupon-system/customer")
public class CustomerController {

	@Autowired
	CustomerService service;

	@PutMapping("/login")
	public boolean login(String email, String password) {
		try {
			return service.login(email, password);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PutMapping("/purchase-coupon")
	public void purchaseCoupon(int couponId, int customerId) {
		try {
			service.purchaseCoupon(couponId, customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/get-coupons")
	public List<Coupon> getAllCoupons(int customerId) {
		try {
			return service.getAllCoupons(customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/get-coupons-by-category")
	public List<Coupon> getCouponsByCategory(Category category, int customerId) {
		try {
			return service.getCouponsByCategory(category, customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/get-coupons-max-price")
	public List<Coupon> getCouponsUpToMaxPrice(double price, int customerId) {
		try {
			return service.getCouponsUpToMaxPrice(price, customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/get-details")
	public Customer getCustomerDetails(int customerId) {
		try {
			return service.getCustomerDetails(customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} 
	}
	
}
