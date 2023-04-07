package app.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * a company entity
* <p/> includes constructors, getters & setters and toString
 * @author RotemYakir
 * 
 */
@Data
@AllArgsConstructor @NoArgsConstructor
@ToString(exclude = "coupons")
@EqualsAndHashCode(of = "id")
@Entity
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String password;
	@JsonIgnore
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<Coupon> coupons;
	
	/**
	 * constructs a company with no id. used in service where the id is
	 * generated in the database.
	 */
	public Company(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = new ArrayList<>();
	}
	
	/**
	 * adds a coupon to the list of the company coupons
	 */
	public void addCoupon(Coupon coupon) {
		if (coupons == null) {
			this.coupons = new ArrayList<>();
		}
		coupon.setCompany(this);
		this.coupons.add(coupon);
	}

}