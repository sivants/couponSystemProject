package DAOclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import beans.Coupon;
import db.ConnectionPool;
import exceptions.CouponSystemException;

public class CouponsDbDAO implements CouponsDAO {

	@Override
	public int addCoupon(Coupon coupon) throws CouponSystemException {
		String sql = "INSERT into coupons (company_Id, category_id, tittle, description, start_date, end_date, amount, price, image) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			pstmt.setInt(1, coupon.getCompanyId());
			pstmt.setInt(2, coupon.getCategory().ordinal());
			pstmt.setString(3, coupon.getTittle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, coupon.getStartDate());
			pstmt.setDate(6, coupon.getEndDate());
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			coupon.setId(rs.getInt(1));
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new CouponSystemException("addCoupon failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		String sql = "UPDATE coupons set company_id=?, category_id=?, tittle=?, description=?,"
				+ " start_date=?, end_date=?, amount=?, price=?, image=?  WHERE id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, coupon.getCompanyId());
			pstmt.setInt(2, coupon.getCategoryId());
			pstmt.setString(3, coupon.getTittle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, coupon.getStartDate());
			pstmt.setDate(6, coupon.getEndDate());
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.setInt(10, coupon.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("updateCoupon failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void deleteCoupon(int couponId) throws CouponSystemException {
		String sql =  "DELETE from coupons WHERE id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try(PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("deleteCoupon failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		String sql = "SELECT * from coupons";
		Connection con = ConnectionPool.getInstance().getConnection();
		try(PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();){
			Collection<Coupon> allCoupons = new ArrayList<Coupon>();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategoryId(rs.getInt("category_id"));
				coupon.setTittle(rs.getString("tittle"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(rs.getDate("start_date"));
				coupon.setEndDate(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				allCoupons.add(coupon);
			}
			return allCoupons;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCoupons failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public Coupon getOneCoupon(int couponId) throws CouponSystemException {
		String sql = "SELECT * from coupons WHERE id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try(PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, couponId);
			ResultSet rs = pstmt.executeQuery();
			Coupon coupon = new Coupon();
			rs.next();
			coupon.setId(couponId);
			coupon.setCompanyId(rs.getInt("company_id"));
			coupon.setCategoryId(rs.getInt("category_id"));
			coupon.setTittle(rs.getString("tittle"));
			coupon.setDescription(rs.getString("description"));
			coupon.setStartDate(rs.getDate("start_date"));
			coupon.setEndDate(rs.getDate("end_date"));
			coupon.setAmount(rs.getInt("amount"));
			coupon.setPrice(rs.getDouble("price"));
			coupon.setImage(rs.getString("image"));
			return coupon;
		} catch (SQLException e) {
			throw new CouponSystemException("getOneCoupon failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void addCouponPurchase(int customerId, int couponId) throws CouponSystemException {
		String sql = "INSERT into customers_vs_coupons (customer_Id, coupon_Id) values (?, ?)";
		Connection con = ConnectionPool.getInstance().getConnection();
		try(PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("addCouponPurchase failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void deleteCouponPurchase(int customerId, int couponId) throws CouponSystemException {
		String sql = "DELETE from customers_vs_coupons WHERE customer_id=? and coupon_id= ?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try(PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("deleteCouponPurchase failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public Collection<Coupon> getCompanyCoupons(int companyId) throws CouponSystemException {
		String sql = "SELECT * from coupons WHERE company_id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			Collection<Coupon> allCompanyCoupons = new ArrayList<Coupon>();
			pstmt.setInt(1, companyId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategoryId(rs.getInt("category_id"));
				coupon.setTittle(rs.getString("tittle"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(rs.getDate("start_date"));
				coupon.setEndDate(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				allCompanyCoupons.add(coupon);
			}return allCompanyCoupons;
		} catch (SQLException e) {
			throw new CouponSystemException("getCompanyCoupons failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	@Override
	public Collection<Coupon> getExpiredCoupons() throws CouponSystemException {
		String sql = "SELECT * from coupons WHERE end_date < CURRENT_DATE";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			Collection<Coupon> expiredCoupons = new ArrayList<Coupon>();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategoryId(rs.getInt("category_id"));
				coupon.setTittle(rs.getString("tittle"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(rs.getDate("start_date"));
				coupon.setEndDate(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				expiredCoupons.add(coupon);
			}return expiredCoupons;
		} catch (SQLException e) {
			throw new CouponSystemException("getExpiredCoupons failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public Collection<Coupon> getCustomerCoupons(int customerId) throws CouponSystemException {
		String sql = "SELECT * from customers_vs_coupons WHERE customer_id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			Collection<Coupon> customerCoupons = new ArrayList<Coupon>();
			pstmt.setInt(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = getOneCoupon(rs.getInt("coupon_id"));
				customerCoupons .add(coupon);
			}return customerCoupons ;
		} catch (SQLException e) {
			throw new CouponSystemException("getCustomerCoupons failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	
	public void deleteAllCompanyCoupons(int companyId) throws CouponSystemException{
		String sql = "DELETE from coupons WHERE company_id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, companyId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("deleteCompanyCoupons failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	public void deleteAllCustomerCoupons(int customerId) throws CouponSystemException{
		String sql = "DELETE from customers_vs_coupons WHERE customer_id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, customerId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("deleteAllCustomerCoupons failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void deleteCompanyCouponPurchase(int companyId) throws CouponSystemException {
			Collection<Coupon> allCompanyCoupons = getCompanyCoupons(companyId);
			for (Coupon coupon : allCompanyCoupons) {
					deleteCouponPurchaseById(coupon.getId());
			}
	}
		
	@Override
		public void deleteCouponPurchaseByCustomer(int customerId) throws CouponSystemException {
			String sql = "DELETE from customers_vs_coupons WHERE customer_id=? ";
			Connection con = ConnectionPool.getInstance().getConnection();
			try(PreparedStatement pstmt = con.prepareStatement(sql);) {
				pstmt.setInt(1, customerId);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				throw new CouponSystemException("deleteCouponPurchaseByCustomer failed", e);
			}finally {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
			
		@Override
		public void deleteCouponPurchaseById(int couponId) throws CouponSystemException {
			String sql = "DELETE from customers_vs_coupons WHERE coupon_id= ?";
			Connection con = ConnectionPool.getInstance().getConnection();
			try(PreparedStatement pstmt = con.prepareStatement(sql);) {
				pstmt.setInt(1, couponId);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				throw new CouponSystemException("deleteCouponPurchaseById failed", e);
			}finally {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
			
}	
	


