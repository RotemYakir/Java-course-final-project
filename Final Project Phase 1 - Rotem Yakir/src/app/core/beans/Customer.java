package app.core.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * a customer object.
 * <p/> includes constructors, getters & setters and toString
 * @author RotemYakir
 *
 */
public class Customer {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private List<Coupon> coupons = new ArrayList<>();

	public Customer() {
		super();
	}

	/**
	 * constructs a customer with no id. Used in facade methods where the id is
	 * generated in the database.
	 */
	public Customer( String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	
	public Customer(int id, String firstName, String lastName, String email, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * @return string of id, first name, last name, email and password
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password +"]";
	}
	
	

}
