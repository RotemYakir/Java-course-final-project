package app.core.auth;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController("/login")
@CrossOrigin
public class AuthController {

//	@Autowired
//	AdminService adminService;
//	@Autowired
//	CompanyService companyService;
//	@Autowired
//	CustomerService customerService;
//
//	@PostMapping("/admin")
//	public String adminLogin(@RequestBody UserCredentials credentials) {
//		try {
//			return adminService.login(credentials);
//		} catch (CouponSystemException e) {
//			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//		}
//	}
//	@PostMapping("/company")
//	public String companyLogin(@RequestBody UserCredentials credentials) {
//		try {
//			return companyService.login(credentials);
//		} catch (CouponSystemException e) {
//			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//		}
//	}
//	@PostMapping("/customer")
//	public String customerLogin(@RequestBody UserCredentials credentials) {
//		try {
//			return customerService.login(credentials);
//		} catch (CouponSystemException e) {
//			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//		}
//	}

}
