package app.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.core.beans.Category;
import app.core.beans.Company;
import app.core.beans.Coupon;
import app.core.exceptions.CouponSystemException;

/**
 * implements an access object to to the database and handles DB company operations.
 * @author RotemYakir
 *
 */
public class CompaniesDBDAO implements CompaniesDAO {

	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String INSERT = "insert into companies values (0,?,?,?)";
	private static final String DELETE = "delete from companies where id =?";
	private static final String UPDATE = "update companies set name=?, email=?, password=? where id=?";
	private static final String SELECT_ONE = " select * from companies where id =?";
	private static final String SELECT_ONE_BY_EMAIL_AND_PASSWORD = " select * from companies where email =? and password=?";
	private static final String SELECT_ALL = " select * from companies";
	private static final String GET_ALL_COUPONS_BY_COMPANY_ID = " select * from coupons where company_id=?";
	private static final String GET_ALL_COUPONS_BY_COMPANY_ID_AND_CATEGORY = " select * from coupons where company_id=? and category=?";
	private static final String GET_COUPONS_UP_TO_PRICE = " select * from coupons where company_id=? and price<=?";
	private static final String DELETE_COUPONS_OF_COMPANY = "delete from coupons where id =?";
	private static final String IS_EXISTS_BY_NAME = "select * from companies where name=?";
	private static final String IS_EXISTS_BY_EMAIL = "select * from companies where email=?";
	private static final String IS_EXISTS_BY_ID = "select * from companies where id=?";

	@Override
	public boolean isCompanyExists(String email, String password) {
		boolean isExists = false;
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(SELECT_ONE_BY_EMAIL_AND_PASSWORD);) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			isExists = rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to check if company exists.", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return isExists;

	}

	@Override
	public Company addCompany(Company company) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				company.setId(rs.getInt(1));
			}
			return company;
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to add the company.", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void updateCompany(Company company) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(UPDATE);) {
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.setInt(4, company.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to update company details.", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void deleteCompanyById(int companyId) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(DELETE);) {
			pstmt.setInt(1, companyId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to delete the company.", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public List<Company> getAllCompanies() {
		List<Company> companies = new ArrayList<>();
		Connection con = conPool.getConnection();
		try (Statement stmt = con.createStatement();) {
			ResultSet rs = stmt.executeQuery(SELECT_ALL);
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				companies.add(company);
			}
			return companies;
		} catch (Exception e) {
			throw new CouponSystemException("FAILED to get all companies.", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public Company getCompanyById(int companyId) {
		Company company = new Company();
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(SELECT_ONE);) {
			pstmt.setInt(1, companyId);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			company.setId(rs.getInt("id"));
			company.setName(rs.getString("name"));
			company.setEmail(rs.getString("email"));
			company.setPassword(rs.getString("password"));
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to get company with id value: " + companyId );
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return company;
	}

	@Override
	public Company getCompanyByEmailAndPassword(String email, String password) {
		Connection con = conPool.getConnection();
		Company company = new Company();
		try (PreparedStatement pstmt = con.prepareStatement(SELECT_ONE_BY_EMAIL_AND_PASSWORD);) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			company.setId(rs.getInt("id"));
			company.setName(rs.getString("name"));
			company.setEmail(rs.getString("email"));
			company.setPassword(rs.getString("password"));
			return company;
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to get company.", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public boolean isCompanyExistsByName(String name) {
		boolean isExists = false;
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(IS_EXISTS_BY_NAME);) {
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			isExists = rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to check if company exists by name: "+name, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return isExists;
	}

	@Override
	public boolean isCompanyExistsByEmail(String email) {
		boolean isExists = false;
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(IS_EXISTS_BY_EMAIL);) {
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			isExists = rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to check if company exists by email: "+email, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return isExists;
	}

	@Override
	public List<Coupon> getAllCouponsOfCompany(int companyId) {
		List<Coupon> coupons = new ArrayList<>();
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(GET_ALL_COUPONS_BY_COMPANY_ID);) {
			pstmt.setInt(1, companyId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(rs.getDate("start_date").toLocalDate());
				coupon.setEndDate(rs.getDate("end_date").toLocalDate());
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to get all coupons of company id: " + companyId, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public List<Coupon> getAllCouponsOfCompanyByCategory(int companyId, Category category) {
		List<Coupon> coupons = new ArrayList<>();
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(GET_ALL_COUPONS_BY_COMPANY_ID_AND_CATEGORY);) {
			pstmt.setInt(1, companyId);
			pstmt.setString(2, category.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(rs.getDate("start_date").toLocalDate());
				coupon.setEndDate(rs.getDate("end_date").toLocalDate());
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException(
					"FAILED to get all coupons of company id: " + companyId + " by category: " + category, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public List<Coupon> getCouponsUpToPrice(int companyId, double price) {
		List<Coupon> coupons = new ArrayList<>();
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(GET_COUPONS_UP_TO_PRICE);) {
			pstmt.setInt(1, companyId);
			pstmt.setDouble(2, price);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(rs.getDate("start_date").toLocalDate());
				coupon.setEndDate(rs.getDate("end_date").toLocalDate());
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException(
					"FAILED to get all coupons of company id: " + companyId + " up to price: " + price, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void deleteCouponsByCompanyId(int companyId) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(DELETE_COUPONS_OF_COMPANY);) {
			pstmt.setInt(1, companyId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to delete the coupons of company id: " + companyId, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}
	
	@Override
	public boolean isCompanyExists(int companyId) {
		Connection con = conPool.getConnection();
		boolean isExists = false;
		try (PreparedStatement pstmt = con.prepareStatement(IS_EXISTS_BY_ID);) {
			pstmt.setInt(1, companyId);
			ResultSet rs = pstmt.executeQuery();
			isExists = rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to check if company (id:" + companyId + ")  exists", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return isExists;
	}
}

