package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.auth.client.UserCredentials;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.ClientService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class AuthController {

	@Autowired
	ClientService clientService;

	@PostMapping("/admin")
	public String adminLogin(@RequestBody UserCredentials credentials) {
		try {
			AdminService service = (AdminService) clientService;
			return service.login(credentials);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PostMapping("/company")
	public String companyLogin(@RequestBody UserCredentials credentials) {
		try {
			CompanyService service = (CompanyService) clientService;
			return service.login(credentials);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PostMapping("/customer")
	public String customerLogin(@RequestBody UserCredentials credentials) {
		try {
			CustomerService service = (CustomerService) clientService;
			return service.login(credentials);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

}
