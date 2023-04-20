package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.login.UserCredentials;
import app.core.services.CompanyService;

@RequestMapping("/coupon-system/company")
@RestController
public class CompanyController {

	@Autowired
	CompanyService service;


	@PostMapping("/login")
	public String login(@RequestBody UserCredentials credentials ) {
		try {
			return service.login(credentials);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PostMapping(path = "/add-coupon",headers = HttpHeaders.AUTHORIZATION)
	public Coupon addNewCoupon(@RequestBody Coupon coupon,@RequestParam int companyId) {
		try {
			return service.addNewCoupon(coupon,companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(path = "/update-coupon",headers = HttpHeaders.AUTHORIZATION)
	public Coupon updateCoupon(@RequestBody Coupon coupon,@RequestParam int companyId) {
		try {
			return service.updateCoupon(coupon,companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(path="delete-coupon", headers = HttpHeaders.AUTHORIZATION)
	public void deleteCoupon(int couponId,int companyId) {
		try {
			service.deleteCoupon(couponId,companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path =  "get-all-coupons",headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getAllCoupons(int companyId) {
		try {
			return service.getAllCoupons(companyId); 			
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path =  "get-coupons-by-category",headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getCouponsByCategory(Category category, int companyId) {
		try {
			return service.getCouponsByCategory(category,companyId); 			
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping(path ="/get-coupons-max-price",headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getCouponsUpToMaxPrice(double price, int companyId) {
		try {
			return service.getCouponsUpToMaxPrice(price, companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping(path = "/get-details",headers = HttpHeaders.AUTHORIZATION)
	public Company getCustomerDetails(int companyId) {
		try {
			return service.getCompanyDetails(companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} 
	}

}
