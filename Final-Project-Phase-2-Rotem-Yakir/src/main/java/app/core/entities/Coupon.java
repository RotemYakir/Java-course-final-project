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
	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name = "company_id")
	private Company company;
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
	@ToString.Exclude
	private List<Customer> customers;

	
	/**
	 * @return the id of the company that owns the coupon
	 */
	public int getCompanyId() {
		return this.getCompany().getId();
	}

	/**
	 * constructs a coupon with no id. Used in service where the id is
	 * generated in the database.
	 */
	public Coupon(int companyId, Category category, String title, String description, LocalDate startDate,
			LocalDate endDate, int amount, double price) {
		super();
		this.company = new Company();
		this.company.setId(companyId);
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
	}

}
