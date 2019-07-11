package facade;

import java.util.ArrayList;
import java.util.Collection;

import DAOclasses.CompaniesDAO;
import DAOclasses.CompaniesDbDAO;
import DAOclasses.CouponsDAO;
import DAOclasses.CouponsDbDAO;
import DAOclasses.CustomersDAO;
import beans.Category;
import beans.Company;
import beans.Coupon;
import exceptions.CouponSystemException;

public class CompanyFacade extends ClientFacade {

	private int companyId;
	protected CompaniesDAO companiesDAO = new CompaniesDbDAO();
	protected CouponsDAO couponsDAO = new CouponsDbDAO();
	
	protected CompanyFacade(CompaniesDAO companiesDAO, CustomersDAO customersDAO, CouponsDAO couponsDAO, int companyId) {
		super(companiesDAO, customersDAO, couponsDAO);
		this.companyId = companyId;
	}

	public CompanyFacade() {
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException{
		if (companiesDAO.isCompanyExists(email, password)) {
			setCompanyId(companiesDAO.getCompanyIdByMailPassword(email, password));
			return true;
		}
		return false;
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public void addCoupon(Coupon coupon) throws CouponSystemException {
		Collection<Coupon> allCompanyCoupons = new ArrayList<Coupon>(couponsDAO.getCompanyCoupons(coupon.getCompanyId()));
		boolean isCouponExists = false;
		for (Coupon coupon2 : allCompanyCoupons) {
			if (coupon2.getTittle().equals(coupon.getTittle())){
				isCouponExists = true;
				System.out.println("CouponTittle allready exists");
			}if (!isCouponExists) {
				couponsDAO.addCoupon(coupon);
			}
		}
	}
	
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Coupon coupon1 = couponsDAO.getOneCoupon(coupon.getId());
		if (coupon1.getId() == coupon.getId() && coupon1.getCompanyId() == coupon.getCompanyId()) {
		couponsDAO.updateCoupon(coupon);
		}else {
			throw new CouponSystemException("updateCoupon failed, updating couponId and companyId is not possible");
		}
	}
	
	public void deleteCoupon(int couponId) throws CouponSystemException {
		couponsDAO.deleteCouponPurchaseById(couponId);
		couponsDAO.deleteCoupon(couponId);
	}
	
	public Collection<Coupon> getCompanyCoupons(int companyId) throws CouponSystemException{
		return couponsDAO.getCompanyCoupons(companyId);
	}
	
	public Collection<Coupon> getCompanyCoupons(Category category) throws CouponSystemException{
		Collection<Coupon> compCoupons = couponsDAO.getCompanyCoupons(companyId);
		Collection<Coupon> categoryCoupons = new ArrayList<Coupon>();
		for (Coupon coupon : compCoupons) {
			if (coupon.getCategoryId() == category.ordinal()) {
				categoryCoupons.add(coupon);
			}
		}
		return categoryCoupons;
	}
	public Collection<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemException{
		Collection<Coupon> compCoupons = couponsDAO.getCompanyCoupons(companyId);
		Collection<Coupon> maxPriceCoupons = new ArrayList<Coupon>();
		for (Coupon coupon : compCoupons) {
			if (coupon.getPrice() < maxPrice || coupon.getPrice() == maxPrice) {
				maxPriceCoupons.add(coupon);
			}
		}
		return maxPriceCoupons;
	}
	
	public Company getCompanyDetails(int companyId) throws CouponSystemException {
		return this.companiesDAO.getOneCompany(companyId);
	}
	
	
}
