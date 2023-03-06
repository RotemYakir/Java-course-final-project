package app.core.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * a customer entity.
 * <p/> includes constructors, getters & setters and toString
 * @author RotemYakir
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	@ToString.Exclude
	@ManyToMany
	@JoinTable(name = "customers_vs_coupons", joinColumns = {
			@JoinColumn(name = "customer_id") }, inverseJoinColumns = { @JoinColumn(name = "coupon_id") })
	private List<Coupon> coupons;

	public void addCoupon(Coupon coupon) {
		if (coupon != null) {
			this.coupons.add(coupon);
		}
	}

	/**
	 * constructs a customer with no id. Used in service where the id is
	 * generated in the database.
	 */
	public Customer(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

}
