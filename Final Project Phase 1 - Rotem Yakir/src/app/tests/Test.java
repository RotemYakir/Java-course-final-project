package app.tests;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import app.core.Threads.CouponExpirationDailyJob;
import app.core.beans.Category;
import app.core.beans.Company;
import app.core.beans.Coupon;
import app.core.beans.Customer;
import app.core.database.ConnectionPool;
import app.core.exceptions.CouponSystemException;
import app.core.facade.AdminFacade;
import app.core.facade.CompanyFacade;
import app.core.facade.CustomerFacade;
import app.core.facade.login.ClientType;
import app.core.facade.login.LoginManager;

/**
 * contains a test method - to demonstrate the capabilities of the system.
 * 
 * @author RotemYakir
 *
 */
public class Test {

	static LoginManager loginManager = LoginManager.getInstance();

	/**
	 * creates Facades and activates all their methods. runs without exceptions -
	 * debug is recommended to check it step by step.
	 */
	public static void testAll() {
		ConnectionPool conPool = ConnectionPool.getInstance();
		Thread thread = new Thread(new CouponExpirationDailyJob());
		try {
			thread.start();
			AdminFacade admin = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMIN);

			admin.addCompany(new Company("Super Farm", "SuperFarm@email.com", "Farm123Super"));
			admin.addCompany(new Company("McDonald's", "McDonalds@email.com", "NotBurgerKing"));
			admin.addCompany(new Company("Dan Hotel", "DanHotel@email.com", "9485721"));
			admin.addCompany(new Company("DutyFree", "DutyFree@email.com", "GurionBen48"));
			Company companyToDelete = admin.addCompany(new Company("Delete Example", "delete@meLater", "42398")); // Admin.addCompany

			admin.deleteCompany(companyToDelete.getId());

			Company company3 = admin.getCompany(3);
			company3.setEmail("DHotel@email.com");
			company3.setPassword("drowssap");
			admin.updateCompany(company3);

			System.out.println("All companies: ");
			for (Company company : admin.getAllCompanies()) {
				System.out.println(company);
			}

			admin.addCustomer(new Customer("Billy", "Joel", "BillyJoel@email.com", "1122334455"));
			admin.addCustomer(new Customer("Agatha", "Christie", "AgathaChristie@email.com", "detective952"));
			admin.addCustomer(new Customer("Linoy", "Ashram", "LinoyAshram@email.com", "goldie294"));
			admin.addCustomer(new Customer("Eyal", "Shani", "EyalShani@email.com", "tomatoes"));
			Customer customerToDel = admin.addCustomer(new Customer("Delete", "Later", "DeleteMe@email.com", "329864"));

			admin.deleteCustomer(customerToDel.getId());

			Customer customer1 = admin.getCustomer(1);
			customer1.setPassword("102938");
			customer1.setEmail("BillyJ@email.com");
			admin.updateCustomer(customer1);

			System.out.println("\nAll customers: ");
			for (Customer customer : admin.getAllcustomers()) {
				System.out.println(customer);
			}

			CompanyFacade dutyFree = (CompanyFacade) loginManager.login("DutyFree@email.com", "GurionBen48",
					ClientType.COMPANY);
			Coupon shampoo = dutyFree
					.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.FARMACY, "Shampoo Discount",
							"10% discount", LocalDate.of(2022, 9, 20), LocalDate.of(2023, 12, 01), 3, 5.99));
			Coupon perfume = dutyFree
					.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.FARMACY, "Perfume Discount",
							"70 ILS discount", LocalDate.of(2022, 3, 30), LocalDate.of(2023, 04, 01), 6, 50));
			Coupon tShirt = dutyFree
					.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.CLOTHING, "T-shirt Discount",
							"20 ILS discount", LocalDate.of(2021, 12, 14), LocalDate.of(2022, 12, 31), 10, 19.90));
			Coupon glidaPitsoots = dutyFree
					.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.RESTAURANT, "Icecream Discount",
							"15% discount", LocalDate.of(2022, 7, 30), LocalDate.of(2023, 02, 28), 4, 7.50));
			Coupon giftShop = dutyFree.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.VACATION,
					"Gift Shop Coupon", "10% discount", LocalDate.of(2022, 10, 10), LocalDate.of(2023, 02, 10), 7, 20));
			Coupon shekemElectric = dutyFree.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.ELECTRICITY,
					"Shekem Electric", "5% discount for mobile phones", LocalDate.of(2020, 11, 05),
					LocalDate.of(2023, 06, 14), 30, 29.90));

			System.out.println("\nDutyFree details: \n" + dutyFree.getCompanyDetails());
			System.out.println("\nDutyFree coupons: ");
			for (Coupon coupon : dutyFree.getCoupons()) {
				System.out.println(coupon);
			}
			System.out.println("\nDutyFree RESTAURANT coupons: ");
			for (Coupon coupon : dutyFree.getCouponsByCategory(Category.RESTAURANT)) {
				System.out.println(coupon);
			}

			giftShop.setPrice(30.90);
			giftShop.setAmount(15);
			dutyFree.updateCoupon(giftShop);

			System.out.println("\nDutyFree coupons up to 30 ILS: ");
			for (Coupon coupon : dutyFree.getCouponsUpToMaxPrice(30)) {
				System.out.println(coupon);
			}

			CustomerFacade billy = (CustomerFacade) loginManager.login("BillyJ@email.com", "102938",
					ClientType.CUSTOMER);
			CustomerFacade agatha = (CustomerFacade) loginManager.login("AgathaChristie@email.com", "detective952",
					ClientType.CUSTOMER);
			CustomerFacade linoy = (CustomerFacade) loginManager.login("LinoyAshram@email.com", "goldie294",
					ClientType.CUSTOMER);
			CustomerFacade eyal = (CustomerFacade) loginManager.login("EyalShani@email.com", "tomatoes",
					ClientType.CUSTOMER);

			billy.purchaseCoupon(tShirt);
			billy.purchaseCoupon(shampoo);
			agatha.purchaseCoupon(giftShop);
			agatha.purchaseCoupon(perfume);
			linoy.purchaseCoupon(perfume);
			eyal.purchaseCoupon(glidaPitsoots);
			eyal.purchaseCoupon(giftShop);
			linoy.purchaseCoupon(shampoo);
			billy.purchaseCoupon(shekemElectric);

			dutyFree.deleteCoupon(giftShop.getId()); // to check quickly in data base - giftShop id = 5

			System.out.println("\nBilly's details: ");
			System.out.println(billy.getCustomerDetails());

			System.out.println("\nBilly's all coupons: ");
			for (Coupon coupon : billy.getAllCoupons()) {
				System.out.println(coupon);
			}
			System.out.println("\nBilly's FARMACY coupons: ");
			for (Coupon coupon : billy.getCouponsOfCategory(Category.FARMACY)) {
				System.out.println(coupon);
			}

			System.out.println("\nBilly's coupons up to 20 ILS: ");
			for (Coupon coupon : billy.getCouponsUpToMaxPrice(20)) {
				System.out.println(coupon);
			}

			// admin.deleteCompany(4); // dutyFree id
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			throw new CouponSystemException(e.getMessage());
		} finally {
			thread.interrupt();
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
			conPool.closeAllConnections();
		}
	}
}