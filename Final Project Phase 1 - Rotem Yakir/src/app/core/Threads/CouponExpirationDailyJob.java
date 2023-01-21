package app.core.Threads;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import app.core.database.CouponsDBDAO;

/**
 * this thread deletes expired coupons (if needed)
 * 
 * @author RotemYakir
 */
public class CouponExpirationDailyJob implements Runnable {

	private CouponsDBDAO couponsDAO = new CouponsDBDAO();
	private boolean quit;

	public CouponExpirationDailyJob() {
		super();

	}

	/**
	 * ONCE A DAY checks if expired coupons exist in the system. if there are
	 * expired coupons - deletes them and their history of purchase.
	 */
	@Override
	public void run() {
		while (!quit) {
			System.out.println("\t >>>>> ExpirationDailyJob is searching for expired coupons.");
			if (couponsDAO.isExpiredCouponExists()) {
				System.out.println("\t >>>>> ExpirationDailyJob found expired coupons and deleting them.");
				couponsDAO.deleteAllExpiredCouponsHistory();
				couponsDAO.deleteAllExpiredCoupons();
				System.out.println("\t >>>>> deletion is done.");
			} else {
				System.out.println("\t >>>>> expired coupons up to " + LocalDate.now().toString() + " - not found. The next check is scheduled to occur tomorrow.");
			}
			try {
				TimeUnit.DAYS.sleep(1);
			} catch (InterruptedException e) {
				quit = true;
			}
		}
	}
	
}
