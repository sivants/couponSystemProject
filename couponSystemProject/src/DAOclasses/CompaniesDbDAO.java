package DAOclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Company;
import db.ConnectionPool;
import exceptions.CouponSystemException;

public class CompaniesDbDAO implements CompaniesDAO {

	@Override
	public boolean isCompanyExists(String email, String password) throws CouponSystemException {
		String sql = "SELECT * from companies WHERE email='" + email + "'" + " and password = '" + password + "'";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			return (rs.next());
		} catch (SQLException e) {
			throw new CouponSystemException("finding the company failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	@Override
	public int addCompany(Company company) throws CouponSystemException {
		String sql = "INSERT into companies (name, email, password) values (?, ?, ?)";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			company.setId(rs.getInt(1));
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new CouponSystemException("addCompany failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		String sql = "UPDATE companies set name=?, email=?, password=? WHERE id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.setLong(4, company.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("UpDateCompany failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void deleteCompany(int companyID) throws CouponSystemException {
		String sql = "DELETE from companies WHERE id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, companyID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("deleteCompany failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		String sql = "SELECT * from companies";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
			ArrayList<Company> allCompanies = new ArrayList<Company>();
			while (rs.next()) {
				Company company = new Company();
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				allCompanies.add(company);
			}
			return allCompanies;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCompanies failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public Company getOneCompany(int companyID) throws CouponSystemException {
		String sql = "SELECT * from companies WHERE id=?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			Company company = new Company();
			rs.next();
			company.setId(companyID);
			company.setName(rs.getString("name"));
			company.setEmail(rs.getString("email"));
			company.setPassword(rs.getString("password"));
			return company;
		} catch (SQLException e) {
			throw new CouponSystemException("getCompany failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public int getCompanyIdByMailPassword(String email, String password) throws CouponSystemException {
		String sql = "SELECT * from companies WHERE email='" + email + "'" + " and password = '" + password + "'";
		Connection con = ConnectionPool.getInstance().getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			if(rs.next());
			return rs.getInt("id");
		} catch (SQLException e) {
			throw new CouponSystemException("getCompanyId failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
}
