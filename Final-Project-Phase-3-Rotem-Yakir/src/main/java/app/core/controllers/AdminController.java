package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import app.core.services.AdminService;

@RequestMapping("/api/admin")
@RestController
@CrossOrigin
public class AdminController {

	@Autowired
	AdminService service;



	@PostMapping(path = "/add-company", headers = HttpHeaders.AUTHORIZATION)
	public Company addCompany(@RequestBody Company company) {
		try {
			return service.addCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping(path = "/add-customer", headers = HttpHeaders.AUTHORIZATION)
	public Customer addCustomer(@RequestBody Customer customer) {
		try {
			return service.addCustomer(customer);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(path = "/update-company", headers = HttpHeaders.AUTHORIZATION)
	public Company updateCompany(@RequestBody Company company) {
		try {
			return service.updateCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
	   	}
	}

	@PutMapping(path = "/update-customer", headers = HttpHeaders.AUTHORIZATION)
	public Customer updateCustomer(@RequestBody Customer customer) {
		try {
			return service.updateCustomer(customer);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "/get-company/{companyId}", headers = HttpHeaders.AUTHORIZATION)
	public Company getCompany(@PathVariable int companyId) {
		try {
			return service.getCompany(companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/get-customer/{customerId}", headers = HttpHeaders.AUTHORIZATION)
	public Customer getCustomer(@PathVariable int customerId) {
		try {
			return service.getCustomer(customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping(path = "/delete-company", headers = HttpHeaders.AUTHORIZATION)
	public void deleteCompany(int companyId) {
		try {
			service.deleteCompany(companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping(path = "/delete-customer", headers = HttpHeaders.AUTHORIZATION)
	public void deleteCustomer(int customerId) {
		try {
			service.deleteCustomer(customerId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/get-all-companies", headers = HttpHeaders.AUTHORIZATION)
	public List<Company> getAllCompanies() {
		return service.getAllCompanies();
	}

	@GetMapping(path = "/get-all-customers", headers = HttpHeaders.AUTHORIZATION)
	public List<Customer> getAllCustomers() {
		return service.getAllCustomers();
	}

	
}
