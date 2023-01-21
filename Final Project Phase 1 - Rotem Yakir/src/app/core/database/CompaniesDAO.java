package app.core.database;

import java.util.List;

import app.core.beans.Category;
import app.core.beans.Company;
import app.core.beans.Coupon;

/**
 * an interface of a Data Access Object
 * 
 * @author RotemYakir
 */
public interface CompaniesDAO {

	/**
	 * adds a company to the database.
	 * 
	 * @param company company to add to the database
	 * @return the company that has been added with its generated id.
	 */
	public Company addCompany(Company company);

	/**
	 * updates the company (using company id to get it from the database).
	 * 
	 * @param company the company that needs to be updated
	 */
	public void updateCompany(Company company);

	/**
	 * deletes the company with the matching id from the database.
	 * 
	 * @param companyId the id of the company
	 */
	public void deleteCompanyById(int companyId);

	/**
	 * @return a list of all the companies 
	 */
	public List<Company> getAllCompanies();

	/**
	 * 
	 * @param companyId the id of the company
	 * @return a company with matching id from the database
	 */
	public Company getCompanyById(int companyId);

	/**
	 * @param email the email of the company
	 * @param password the password of the company
	 * @return a company with matching email and password
	 */
	public Company getCompanyByEmailAndPassword(String email, String password);

	/**
	 * checks if there is a company with matching email and password in the
	 * database.
	 * 
	 * @return the result of the check
	 */
	public boolean isCompanyExists(String email, String password);

	/**
	 * checks if there is a company with the matching name in the database.
	 * 
	 * @param name the name of the company
	 * @return
	 */
	boolean isCompanyExistsByName(String name);

	/**
	 * checks if there is a company with the matching email in the database.
	 * 
	 * @param email the email of the company
	 * @return the result of the check
	 */
	boolean isCompanyExistsByEmail(String email);

	/**
	 * @param companyId the id of the company
	 * @return a list of all the coupons owned by the company.
	 */
	List<Coupon> getAllCouponsOfCompany(int companyId);

	/**
	 * @param companyId the id of the company
	 * @param category  the wanted category
	 * @return a list of coupons owned by the company - of a specific category.
	 */
	List<Coupon> getAllCouponsOfCompanyByCategory(int companyId, Category category);

	/**
	 * @param companyId the id of the company
	 * @param price the wanted (maximum) price
	 * @return a list of coupons owned by the company - up to the inserted price.
	 */
	List<Coupon> getCouponsUpToPrice(int companyId, double price);

	/**
	 * deletes all the coupons owned by the company from the database.
	 * 
	 * @param companyId the id of the company
	 */
	void deleteCouponsByCompanyId(int companyId);
	/**
	 * checks if a company with matching id exists in the database
	 * @param companyId the company id
	 * @return the result of the check
	 */
	boolean isCompanyExists(int companyId);

}
