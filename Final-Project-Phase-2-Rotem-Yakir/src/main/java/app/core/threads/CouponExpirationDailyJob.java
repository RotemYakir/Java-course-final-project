package app.core.threads;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.repositories.CouponRepository;

/**
 * this thread deletes expired coupons (if needed)
 * 
 * @author RotemYakir
 */
@Service
public class CouponExpirationDailyJob{

	@Autowired
	CouponRepository couponRepository;


	/**
	 * checks if expired coupons exist in the system.
	 * if they do - deletes them
	 * scheduled to run once a day.
	 */
	@Scheduled(timeUnit = TimeUnit.DAYS, fixedRate = 1)
	public void task() {
			System.out.println("\t >>>>> ExpirationDailyJob is searching for expired coupons.");
			List<Coupon> expiredCoupons = couponRepository.findAllByEndDateBefore(LocalDate.now());
			if (!expiredCoupons.isEmpty()) {
				System.out.println("\t >>>>> ExpirationDailyJob found expired coupons and deleting them.");
				for (Coupon coupon : expiredCoupons) {
					couponRepository.delete(coupon);
					System.out.println("\t >>>>> Expired coupon (id " + coupon.getId() + ") was deleted");
				}
				System.out.println("\t >>>>> deletion is done.");
			} else {
				System.out.println("\t >>>>> expired coupons up to " + LocalDate.now().toString()
						+ " - not found. The next check is scheduled to occur tomorrow.");
			}
		}

}
