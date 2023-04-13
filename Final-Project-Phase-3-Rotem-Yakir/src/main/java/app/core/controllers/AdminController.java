package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.login.UserCredentials;
import app.core.services.AdminService;

@RequestMapping("/coupon-system/admin")
@RestController
public class AdminController {

	@Autowired
	AdminService service;

	@PostMapping("/login")
	public String login(@RequestBody UserCredentials credentials ) {
		try {
			return service.login(credentials);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PostMapping("/add-company")
	public Company addCompany(@RequestBody Company company) {
		try {
			return service.addCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/add-customer")
	public Customer addCustomer(@RequestBody Customer customer) {
		try {
			return service.addCustomer(customer);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/update-company")
	public Company updateCompany(@RequestBody Company company) {
		try {
			return service.updateCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/update-customer")
	public Customer updateCustomer(@RequestBody Customer customer) {
		try {
			return service.updateCustomer(customer);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/get-company/{companyId}")
	public Company getCompany(@PathVariable int companyId) {
		try {
			return service.getCompany(companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/get-customer/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		try {
			return service.getCustomer(customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("/delete-company")
	public void deleteCompany(int companyId) {
		try {
			service.deleteCompany(companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("/delete-customer")
	public void deleteCustomer(int customerId) {
		try {
			service.deleteCustomer(customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/get-all-companies")
	public List<Company> getAllCompanies(){    
			return service.getAllCompanies();
	}
	@GetMapping("/get-all-customers")
	public List<Customer> getAllCustomers(){ 
		return service.getAllCustomers();
	}

}
