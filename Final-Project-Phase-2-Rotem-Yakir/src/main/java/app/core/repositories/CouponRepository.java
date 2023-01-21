package app.core.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	boolean existsByTitleAndCompany_id(String title,int companyId);

	boolean existsByIdAndCustomersId(int couponId, int customerId);

	List<Coupon> findAllByCompany_id(int companyId);

	List<Coupon> findAllByCompany_idAndCategory(int companyId, Category category);

	List<Coupon> findAllByCompany_idAndPriceLessThan(int companyId, double price);

	List<Coupon> findAllByCustomersId(int customerId);

	List<Coupon> findAllByCustomersIdAndCategory(int customerId, Category category);
	
	List<Coupon> findAllByCustomersIdAndPriceLessThan(int customerId, double price);

	List<Coupon> findAllByEndDateBefore(LocalDate now);



}