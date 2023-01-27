package app.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<Coupon> coupons;
	

	public Company(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = new ArrayList<>();
	}




}
