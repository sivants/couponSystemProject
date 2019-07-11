package DAOclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Customer;
import db.ConnectionPool;
import exceptions.CouponSystemException;

public class CustomersDbDAO implements CustomersDAO {

	@Override
	public boolean isCustomerExists(String email, String password) throws CouponSystemException {
		String sql = "SELECT * from customers WHERE email='" + email + "'" + "and password= '" + password + "'";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();){
				return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException ("finding the customer failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public int addCustomer(Customer customer) throws CouponSystemException {
		String sql = "INSERT into customers (first_name, last_name, email, password) values (?, ?, ?, ?)";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			customer.setId(rs.getInt(1));
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new CouponSystemException("adding customer failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		String sql = "UPDATE customers set first_name=?, last_name=?, email=?, password=? WHERE id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setInt(5, customer.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Updating customer failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void deleteCustomer(int customerId) throws CouponSystemException {
		String sql = "DELETE from customers WHERE id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Delete customer failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		String sql = "SELECT * from customers";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			ArrayList<Customer> allCustomers = new ArrayList<Customer>();
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
				allCustomers.add(customer);
			}
			return allCustomers;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCustomers failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public Customer getOneCustomer(int customerId) throws CouponSystemException {
		String sql = "SELECT * from customers WHERE id =?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try(PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			Customer customer = new Customer();
			rs.next();
				customer.setId(customerId);
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
			return customer;
		} catch (SQLException e) {
			throw new CouponSystemException("getOneCustomer failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public int getCustomerIdByMailPassword(String email, String password) throws CouponSystemException {
		String sql = "SELECT * from customers WHERE email='" + email + "'" + "and password= '" + password + "'";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();){
				if (rs.next()); 
					return rs.getInt("id");
		} catch (SQLException e) {
			throw new CouponSystemException ("finding the customer failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
}
