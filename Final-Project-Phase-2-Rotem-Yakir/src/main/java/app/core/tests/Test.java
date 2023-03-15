package app.core.tests;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;
import app.core.services.login.LoginManager;
import app.core.services.login.LoginManager.ClientType;
import app.core.threads.CouponExpirationDailyJob;

@Component
public class Test implements CommandLineRunner {

	@Autowired
	LoginManager loginManager;
	@Autowired
	CouponExpirationDailyJob couponExpirationDailyJob;

	@Override
	public void run(String... args) throws Exception {

		try {

			AdminService admin = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMIN);
			admin.addCompany(new Company("Super Farm", "SuperFarm@email.com", "Farm123Super"));
			admin.addCompany(new Company("McDonald's", "McDonalds@email.com", "NotBurgerKing"));
			admin.addCompany(new Company("Dan Hotel", "DanHotel@email.com", "9485721"));
			admin.addCompany(new Company("DutyFree", "DutyFree@email.com", "GurionBen48"));
			Company companyToDelete = admin.addCompany(new Company("Delete Example", "delete@meLater", "42398"));

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
			for (Customer customer : admin.getAllCustomers()) {
				System.out.println(customer);
			}

			CompanyService dutyFree = (CompanyService) loginManager.login("DutyFree@email.com", "GurionBen48",
					ClientType.COMPANY);
			Coupon shampoo = dutyFree
					.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.FARMACY, "Shampoo Discount",
							"10% discount", LocalDate.of(2022, 9, 20), LocalDate.of(2023, 12, 01), 3, 5.99));
			Coupon perfume = dutyFree
					.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.FARMACY, "Perfume Discount",
							"70 ILS discount", LocalDate.of(2022, 3, 30), LocalDate.of(2023, 9, 01), 6, 50));
			Coupon tShirt = dutyFree
					.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.CLOTHING, "T-shirt Discount",
							"20 ILS discount", LocalDate.of(2021, 12, 14), LocalDate.of(2023, 05, 30), 10, 19.90));
			Coupon glidaPitsoots = dutyFree
					.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.RESTAURANT, "Icecream Discount",
							"15% discount", LocalDate.of(2022, 7, 30), LocalDate.of(2023, 07, 28), 4, 7.50));
			Coupon giftShop = dutyFree.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.VACATION,
					"Gift Shop Coupon", "10% discount", LocalDate.of(2022, 10, 10), LocalDate.of(2023, 10, 10), 7, 20));
			Coupon shekemElectric = dutyFree.addNewCoupon(new Coupon(dutyFree.getCompanyId(), Category.ELECTRICITY,
					"Shekem Electric", "5% discount for mobile phones", LocalDate.of(2020, 11, 05),
					LocalDate.of(2023, 06, 14), 30, 29.90));

			System.out.println("\nDutyFree details: \n" + dutyFree.getCompanyDetails());
			System.out.println("\nDutyFree coupons: ");
			for (Coupon coupon : dutyFree.getAllCoupons()) {
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

			CustomerService billy = (CustomerService) loginManager.login("BillyJ@email.com", "102938",
					ClientType.CUSTOMER);
			CustomerService agatha = (CustomerService) loginManager.login("AgathaChristie@email.com", "detective952",
					ClientType.CUSTOMER);
			CustomerService linoy = (CustomerService) loginManager.login("LinoyAshram@email.com", "goldie294",
					ClientType.CUSTOMER);
			CustomerService eyal = (CustomerService) loginManager.login("EyalShani@email.com", "tomatoes",
					ClientType.CUSTOMER);

			billy.purchaseCoupon(tShirt.getId());
			billy.purchaseCoupon(shampoo.getId());
			agatha.purchaseCoupon(giftShop.getId());
			agatha.purchaseCoupon(perfume.getId());
			linoy.purchaseCoupon(perfume.getId());
			eyal.purchaseCoupon(glidaPitsoots.getId());
			eyal.purchaseCoupon(giftShop.getId());
			linoy.purchaseCoupon(shampoo.getId());
			billy.purchaseCoupon(shekemElectric.getId());

			dutyFree.deleteCoupon(giftShop.getId()); // to check quickly in data base - giftShop id = 5

			System.out.println("\nBilly's details: ");
			System.out.println(billy.getCustomerDetails());

			System.out.println("\nBilly's all coupons: ");
			for (Coupon coupon : billy.getAllCoupons()) {
				System.out.println(coupon);
			}
			System.out.println("\nBilly's FARMACY coupons: ");
			for (Coupon coupon : billy.getCouponsByCategory(Category.FARMACY)) {
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
		}
	}
}
