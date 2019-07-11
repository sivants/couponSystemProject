package facade;

import java.util.Collection;

import DAOclasses.CompaniesDAO;
import DAOclasses.CompaniesDbDAO;
import DAOclasses.CouponsDAO;
import DAOclasses.CouponsDbDAO;
import DAOclasses.CustomersDAO;
import DAOclasses.CustomersDbDAO;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemException;

public class AdminFacade extends ClientFacade {

	protected CompaniesDAO companiesDAO = new CompaniesDbDAO();
	protected CouponsDAO couponsDAO = new CouponsDbDAO();
	protected CustomersDAO customersDAO = new CustomersDbDAO();
	
	protected AdminFacade(CompaniesDAO companiesDAO, CustomersDAO customersDAO, CouponsDAO couponsDAO) {
		super(companiesDAO, customersDAO, couponsDAO);
	}
	
	public AdminFacade() {
		
	}
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		if (email.equals("amin@admin.com") && password.equals("admin")) {
			return true;
		}
		return false;
	}
	
	public int addCompany(Company company) throws CouponSystemException {
		if(!companiesDAO.isCompanyExists(company.getEmail(), company.getPassword())) {
		return companiesDAO.addCompany(company);
			}else {
				throw new CouponSystemException("addCompany failed- allready exists");
				}
	}

	public void updateCompany(Company company) throws CouponSystemException {
		Company company2 = companiesDAO.getOneCompany(company.getId());
		if (company2.getId() == company.getId() && company2.getName().equals(company.getName())) {
			companiesDAO.updateCompany(company);
		}else {
			throw new CouponSystemException("updateCompany failed, updating companyId and companyName is not possible");
		}
	}
	
	public void deleteCompany(int companyId) throws CouponSystemException {
		Collection<Coupon> allCompanyCoupons = couponsDAO.getCompanyCoupons(companyId);
		for (Coupon coupon : allCompanyCoupons) {
			couponsDAO.deleteCouponPurchaseById(coupon.getId());
		}
		couponsDAO.deleteAllCompanyCoupons(companyId);
		companiesDAO.deleteCompany(companyId);
	}
	
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		return companiesDAO.getAllCompanies();
	}
	
	public Company getOneCompany(int companyId) throws CouponSystemException {
		return companiesDAO.getOneCompany(companyId);
	}
	
	public int addCustomer(Customer customer) throws CouponSystemException{
		if (!customersDAO.isCustomerExists(customer.getEmail(), customer.getPassword())){
			return customersDAO.addCustomer(customer);
		}else {
			throw new CouponSystemException("addCustomer failed- allready exists");
		}
	}
	
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Customer customer2 = customersDAO.getOneCustomer(customer.getId());
		if (customer2.getId() == customer.getId()) {
			customersDAO.updateCustomer(customer);
		}else {
			throw new CouponSystemException("updateCustomer failed- cannot update customerId");
		}
	}
	public void deleteCustomer(int customerId) throws CouponSystemException {
		couponsDAO.deleteAllCustomerCoupons(customerId);
		customersDAO.deleteCustomer(customerId);
	}
	
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		return customersDAO.getAllCustomers();
	}
	
	public Customer getOneCustomer(int customerId) throws CouponSystemException {
		return customersDAO.getOneCustomer(customerId);
	}
}
