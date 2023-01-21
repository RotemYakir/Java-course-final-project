package app.core.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * a company object
* <p/> includes constructors, getters & setters and toString
 * @author RotemYakir
 * 
 */
public class Company {

	private int id;
	private String name;
	private String email;
	private String password;
	private List<Coupon> coupons = new ArrayList<Coupon>();

	/**
	 * constructs a company with no id. used in Facade method where the id is
	 * generated in the database.
	
	 */
	public Company(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public Company() {
		super();
	}


	public Company(int id, String name, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	/**
	 * @return string of id, name, email and password
	 */
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}



}
