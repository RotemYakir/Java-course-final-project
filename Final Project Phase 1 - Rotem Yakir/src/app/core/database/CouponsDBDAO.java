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
import app.core.exceptions.CouponSystemException;

/**
 * implements an access object to to the database and handles DB coupons and
 * purchases operations.
 * 
 * @author RotemYakir
 *
 */
public class CouponsDBDAO implements CouponsDAO {

	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String INSERT = "insert into coupons values (0,?,?,?,?,?,?,?,?,?)";
	private static final String DELETE_ONE_COUPON = "delete from coupons where id =?";
	private static final String UPDATE = "update coupons set company_id=?, category=?, title=?, description=?, start_date=?, end_date=?, amount=?, price=?,image=? where id=?";
	private static final String GET_ONE = " select * from coupons where id =?";
	private static final String GET_ALL_COUPONS = " select * from coupons";
	private static final String IS_EXISTS_BY_TITLE_AND_COMPANY_ID = "select * from coupons where company_id=? and title=?";
	private static final String INSERT_COUPON_PURCHASE = "insert into customers_vs_coupons values (?,?)";
	private static final String DELETE_COUPON_PURCHASE = "delete from customers_vs_coupons where coupon_id =? and customer_id=?";
	private static final String DELETE_ALL_COUPON_PURCHASES = "delete from customers_vs_coupons where coupon_id=?";
	private static final String DELETE_ALL_COUPON_PURCHASE_OF_COMPANY_ID = "delete from customers_vs_coupons where coupon_id in(select id from coupons where company_id=?)";
	private static final String IS_COUPON_PURCHSE_EXISTS = "select * from customers_vs_coupons where customer_id=? and coupon_id=?";
	private static final String IS_COUPON_EXISTS = "select * from coupons where id=?";
	private static final String DELETE_ALL_EXPIRED_COUPONS = "delete from coupons where end_date < now()";
	private static final String DELETE_ALL_EXPIRED_COUPONS_PURCHASE_HISTORY = "delete from customers_vs_coupons where coupon_id in (select id from coupons where end_date < now())";
	private static final String SELECT_EXPIRED_COUPONS = "select * from coupons where end_date < now()";

	@Override
	public boolean isCouponExistsInCompanyByTitle(int companyId, String couponTitle) throws CouponSystemException {
		boolean isExists = false;
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(IS_EXISTS_BY_TITLE_AND_COMPANY_ID);) {
			pstmt.setInt(1, companyId);
			pstmt.setString(2, couponTitle);
			ResultSet rs = pstmt.executeQuery();
			isExists = rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to check if coupon exists by title: " + couponTitle, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return isExists;
	}

	@Override
	public Coupon addCoupon(Coupon coupon) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
			pstmt.setInt(1, coupon.getCompanyId());
			pstmt.setString(2, coupon.getCategory().toString());
			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, java.sql.Date.valueOf(coupon.getStartDate()));
			pstmt.setDate(6, java.sql.Date.valueOf(coupon.getEndDate()));
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				coupon.setId(rs.getInt(1));
			}
			return coupon;
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to add the coupon", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(UPDATE);) {
			pstmt.setInt(1, coupon.getCompanyId());
			pstmt.setString(2, coupon.getCategory().toString());
			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, java.sql.Date.valueOf(coupon.getStartDate()));
			pstmt.setDate(6, java.sql.Date.valueOf(coupon.getEndDate()));
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.setInt(10, coupon.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to update coupon details", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void deleteCouponById(int couponId) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(DELETE_ONE_COUPON);) {
			pstmt.setInt(1, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to delete the coupon", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public List<Coupon> getAllCoupons() {
		List<Coupon> coupons = new ArrayList<>();
		Connection con = conPool.getConnection();
		try (Statement stmt = con.createStatement();) {
			ResultSet rs = stmt.executeQuery(GET_ALL_COUPONS);
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
		} catch (Exception e) {
			throw new CouponSystemException("FAILED to get all coupons", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public Coupon getCouponById(int couponId) {
		Coupon coupon = new Coupon();
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(GET_ONE);) {
			pstmt.setInt(1, couponId);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
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
			return coupon;
		} catch (Exception e) {
			throw new CouponSystemException("FAILED to get coupon with id value: " + couponId);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void addCouponPurchase(int customerId, int couponId) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(INSERT_COUPON_PURCHASE);) {
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to add coupon purchase", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void deleteCouponPurchase(int customerId, int couponId) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(DELETE_COUPON_PURCHASE);) {
			pstmt.setInt(1, couponId);
			pstmt.setInt(2, customerId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to delete coupon purchase", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void deleteCouponPurchasesOfCompany(int companyId) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(DELETE_ALL_COUPON_PURCHASE_OF_COMPANY_ID);) {
			pstmt.setInt(1, companyId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to delete coupon purchases of company - id : " + companyId, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public boolean isCouponPurchaseExists(int customerId, int couponId) {
		Connection con = conPool.getConnection();
		boolean isExists = false;
		try (PreparedStatement pstmt = con.prepareStatement(IS_COUPON_PURCHSE_EXISTS);) {
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, couponId);
			ResultSet rs = pstmt.executeQuery();
			isExists = rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException(
					"Failed to check if coupon purchase (id:" + couponId + ") by customer (id:" + couponId + ") exists",
					e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return isExists;
	}

	@Override
	public void deleteAllPurchasesOfCoupon(int couponId) {
		Connection con = conPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(DELETE_ALL_COUPON_PURCHASES);) {
			pstmt.setInt(1, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to delete purchases of coupon id: " + couponId, e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public boolean isExpiredCouponExists() {
		Connection con = conPool.getConnection();
		boolean isExists = false;
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(SELECT_EXPIRED_COUPONS);
			isExists = rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to check id there are expired coupons", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return isExists;
	}

	@Override
	public void deleteAllExpiredCouponsHistory() {
		Connection con = conPool.getConnection();
		try (Statement stmt = con.createStatement();) {
			stmt.execute(DELETE_ALL_EXPIRED_COUPONS_PURCHASE_HISTORY);
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to delete expired coupons purchase history: ", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void deleteAllExpiredCoupons() {
		Connection con = conPool.getConnection();
		try (Statement stmt = con.createStatement();) {
			stmt.execute(DELETE_ALL_EXPIRED_COUPONS);
		} catch (SQLException e) {
			throw new CouponSystemException("FAILED to delete expired coupons", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
	}

	@Override
	public boolean isCouponExists(int couponId) {
		Connection con = conPool.getConnection();
		boolean isExists = false;
		try (PreparedStatement pstmt = con.prepareStatement(IS_COUPON_EXISTS);) {
			pstmt.setInt(1, couponId);
			ResultSet rs = pstmt.executeQuery();
			isExists = rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to check if coupon (id:" + couponId + ")  exists", e);
		} finally {
			if (con != null) {
				conPool.restoreConnection(con);
			}
		}
		return isExists;
	}
}
