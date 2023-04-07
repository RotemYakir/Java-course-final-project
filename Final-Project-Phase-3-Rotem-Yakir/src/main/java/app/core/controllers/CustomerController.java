package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	public void purchaseCoupon(int couponId) {
		try {
			service.purchaseCoupon(couponId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	
	
}

