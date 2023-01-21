package app.core.services;

import org.springframework.beans.factory.annotation.Autowired;

import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;



public abstract class ClientService  {

	@Autowired
	protected CompanyRepository companyRepo;
	@Autowired
	protected CustomerRepository customerRepo;
	@Autowired
	protected CouponRepository couponRepo;

	public abstract boolean login(String email, String password);

}
