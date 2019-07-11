package DAOclasses;

import java.util.ArrayList;

import beans.Company;
import exceptions.CouponSystemException;

public interface CompaniesDAO {

	public boolean isCompanyExists(String email, String password) throws CouponSystemException;
	
	public int addCompany(Company company) throws CouponSystemException;
	
	public void updateCompany(Company company) throws CouponSystemException;

	public void deleteCompany(int companyID) throws CouponSystemException;
	
	public ArrayList<Company> getAllCompanies() throws CouponSystemException;
	
	public Company  getOneCompany(int CompanyID) throws CouponSystemException;
	
	public int getCompanyIdByMailPassword(String companyMail, String companyPassword) throws CouponSystemException;  
	
	
	
	
}
