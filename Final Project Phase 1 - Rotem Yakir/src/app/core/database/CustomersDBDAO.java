package app.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.core.beans.Category;
import app.core.beans.Coupon;
import app.core.beans.Customer;
import app.core.exceptions.CouponSystemException;

/**
 * implements an access object to to the database and handles DB customer operations.
 * @author RotemYakir
 *
 */
public class CustomersDBDAO implements CustomersDAO {

	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String IS_EXISTS_BY_EMAIL_AND_PASSWORD = "select * from customers where email=? and password=?";
	private static final String IS_EXISTS_BY_EMAIL = "select * from customers where email=?";
	private static final String INSERT = "insert into customers values (0,?,?,?,?)";
	private static final String DELETE = "delete from customers where id =?";
	private static final String UPDATE = "update customers set first_name=?, last_name=?, email=?, password=? where id=?";
	private static final String SELECT_ONE = " select * from customers where id =?";
	private static final String SELECT_ALL = " select * from customers";
	private static final String DELETE_COUPON_PURCHASE_OF_CUSTOMER = "delete from customers_vs_coupons where customer_id=?";
	private static final String SELECT_ONE_BY_EMAIL_AND_PASSWORD = " select * from customers where email =? and password=?";
	private static final String GET_ALL_COUPONS = "select * from coupons where id in (select coupon_id from customers_vs_coupons where customer_id=?)";
	private static final String GET_ALL_COUPONS_BY_CATEGORY = "select * from coupons where id in (select coupon_id from customers_vs_coupons where customer_id=?) and category=?";
	private static final String GET_ALL_COUPONS_UP_TO_PRICE = "select * from coupons where id in (select coupon_id from customers_vs_coupons where customer_id=?) and price<=?";
	private static final String IS_CUSTOMER_EXISTS = "select * from customers where id=?";
	
	@Override
	public boolean isCustomerExists(String email, String password) {
		boolean isExists = false;
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(IS_EXISTS_BY_EMAIL_AND_PASSWORD);) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			isExists = rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to check if customer exists", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return isExists;
	}

	@Override
	public boolean isCustomerExistsByEmail(String email) {
		boolean isExists = false;
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(IS_EXISTS_BY_EMAIL);) {
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			isExists = rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to check if customer exists", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return isExists;

	}

	@Override
	public Customer addCustomer(Customer customer) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				customer.setId(rs.getInt(1)); 
			}
			return customer;
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to add the customer", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void updateCustomer(Customer customer) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(UPDATE);) {
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setInt(5, customer.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException(
					"Failed to update customer's details. id: " + customer.getId() + " not found .", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}

	}

	@Override
	public void deleteCustomerById(int customerId) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(DELETE);) {
			pstmt.setInt(1, customerId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to delete the customer id: " + customerId, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		Connection con = conPool.getConnection();
		try (Statement stmt = con.createStatement();) {
			ResultSet rs = stmt.executeQuery(SELECT_ALL);
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
				customers.add(customer);
			}
			return customers;
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to get all customers", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public Customer getCustomerById(int customerId) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(SELECT_ONE);) {
			pstmt.setInt(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			Customer customer = new Customer();
			customer.setFirstName(rs.getString("first_name"));
			customer.setLastName(rs.getString("last_name"));
			customer.setEmail(rs.getString("email"));
			customer.setPassword(rs.getString("password"));
			customer.setId(customerId);
			return customer;
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to get customer with id value: " + customerId, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public Customer getCustomerByEmailAndPassword(String email, String password) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(SELECT_ONE_BY_EMAIL_AND_PASSWORD);) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			Customer customer = new Customer();
			customer.setId(rs.getInt("id"));
			customer.setFirstName(rs.getString("first_name"));
			customer.setLastName(rs.getString("last_name"));
			customer.setEmail(rs.getString("email"));
			customer.setPassword(rs.getString("password"));
			return customer;
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to get customer.", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public List<Coupon> getAllCustomerCoupons(int customerId) {
		List<Coupon> coupons = new ArrayList<>();
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(GET_ALL_COUPONS);) {
			pstmt.setInt(1, customerId);
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
			throw new CouponSystemException("Failed to get all coupons of customer id: " + customerId, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public List<Coupon> getCouponsByCategory(int customerId, Category category) {
		List<Coupon> coupons = new ArrayList<>();
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(GET_ALL_COUPONS_BY_CATEGORY);) {
			pstmt.setInt(1, customerId);
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
			throw new CouponSystemException("Failed to get all coupons of customer id: " + customerId, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}
	@Override
	public List<Coupon> getCouponsUpToPrice(int customerId, double price) {
		List<Coupon> coupons = new ArrayList<>();
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(GET_ALL_COUPONS_UP_TO_PRICE);) {
			pstmt.setInt(1, customerId);
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
			throw new CouponSystemException("Failed to get all coupons of customer id: " + customerId, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}
	@Override
	public void deleteCouponPurchasesOfCustomer(int customerId) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(DELETE_COUPON_PURCHASE_OF_CUSTOMER);) {
			pstmt.setInt(1, customerId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to delete coupon purchase by company id : " + customerId, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}
	
	@Override
	public boolean isCustomerExists(int customerId) {
		Connection con = conPool.getConnection();
		boolean isExists = false;
		try (PreparedStatement pstmt = con.prepareStatement(IS_CUSTOMER_EXISTS);) {
			pstmt.setInt(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			isExists = rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to check if customer (id:" + customerId + ")  exists", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return isExists;
	}

}
