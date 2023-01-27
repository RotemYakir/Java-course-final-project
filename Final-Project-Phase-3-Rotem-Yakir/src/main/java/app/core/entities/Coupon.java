package app.core.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * a coupon entity
 * <p/>
 * includes constructors, getters & setters and toString
 * 
 * @author RotemYakir
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "company_id")
	@ToString.Exclude
	private Company company; 
	@Transient
	private int companyId;
	private String title;
	private String description;
	@Enumerated(EnumType.STRING)
	private Category category;
	@Column(name = "start_date")
	private LocalDate startDate;
	@Column(name = "end_date")
	private LocalDate endDate;
	private int amount;
	private double price;
	private String image;
	@ManyToMany
	@JoinTable(name = "customers_vs_coupons", joinColumns = { @JoinColumn(name = "coupon_id") }, inverseJoinColumns = {
			@JoinColumn(name = "customer_id") })
	@JsonIgnore
	@ToString.Exclude
	private List<Customer> customers;

	public enum Category {
		ELECTRICITY, RESTAURANT, VACATION, FARMACY, CLOTHING
	}


	public Coupon( int companyId, String title, String description, Category category, LocalDate startDate,
			LocalDate endDate, int amount, double price) {
		super();
		this.companyId = companyId;
		this.company=new Company();
		this.company.setId(this.companyId);
		this.title = title;
		this.description = description;
		this.category = category;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
	}

}
