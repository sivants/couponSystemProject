package facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import DAOclasses.CompaniesDAO;
import DAOclasses.CouponsDAO;
import DAOclasses.CouponsDbDAO;
import DAOclasses.CustomersDAO;
import DAOclasses.CustomersDbDAO;
import beans.Category;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemException;

public class CustomerFacade extends ClientFacade{

	private int customerId;
	protected CustomersDAO customersDAO = new CustomersDbDAO();
	protected CouponsDAO couponsDAO = new CouponsDbDAO(); 
	
	public CustomerFacade(CompaniesDAO companiesDAO, CustomersDAO customersDAO, CouponsDAO couponsDAO, int customerId) {
		super(companiesDAO, customersDAO, couponsDAO);
		this.customerId = customerId;
	}

	public CustomerFacade() {
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		if (customersDAO.isCustomerExists(email, password)) {
			setCustomerId(customersDAO.getCustomerIdByMailPassword(email, password));
			return true;
		}
	return false;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		Date now = new Date();
		boolean isPurchased = false;
		Collection<Coupon> customerCoupons = couponsDAO.getCustomerCoupons(customerId);
		if (coupon.getAmount() != 0 && now.before(coupon.getEndDate())) {
			for (Coupon coupon2 : customerCoupons) {
				if (coupon2.equals(coupon)) {
					isPurchased = true;
					System.out.println("coupon allready purchased");
					break;
			    }
		    }
			if (!isPurchased) {
				couponsDAO.addCouponPurchase(customerId, coupon.getId());
				couponsDAO.subtructCouponAmount(couponsDAO.getOneCoupon(coupon.getId()));
			}
		}else {
			System.out.println("coupon ia out of stock or not valid");
		}
    }
	
	public void purchaseCoupon(int couponId) throws CouponSystemException {
		Date now = new Date();
		boolean isPurchased = false;
		Coupon coupon = couponsDAO.getOneCoupon(couponId);
		Collection<Coupon> customerCoupons = couponsDAO.getCustomerCoupons(customerId);
			if (coupon.getAmount() != 0 && now.before(coupon.getEndDate())) { 
				for (Coupon coupon2 : customerCoupons) {
					if (coupon2.equals(coupon)) {
						isPurchased = true;
						System.out.println("coupon allready purchased");
						break;		
					}
				}
				if(!isPurchased) {
					couponsDAO.addCouponPurchase(customerId, couponId);
					couponsDAO.subtructCouponAmount(couponsDAO.getOneCoupon(couponId));
				}
			}else {
				System.out.println("coupon ia out of stock or not valid");
			}
	}		
		
	public Collection<Coupon> getCustomerCoupons() throws CouponSystemException {
		return couponsDAO.getCustomerCoupons(customerId);
	}

	public Collection<Coupon> getCustomerCoupons(Category category) throws CouponSystemException {
		Collection<Coupon> customerCoupons = couponsDAO.getCustomerCoupons(customerId);
		Collection<Coupon> categoryCoupons = new ArrayList<Coupon>();
		for (Coupon coupon : customerCoupons) {
			if (coupon.getCategoryId() == category.ordinal()) {
				categoryCoupons.add(coupon);
			}
		}
		return categoryCoupons;
	}
	
	public Collection<Coupon> getCustomerCoupons(double maxPrice) throws CouponSystemException{
		Collection<Coupon> customerCoupons = couponsDAO.getCustomerCoupons(customerId);
		Collection<Coupon> maxPriceCoupons = new ArrayList<Coupon>();
		for (Coupon coupon : customerCoupons) {
			if (coupon.getPrice() < maxPrice || coupon.getPrice() == maxPrice) {
				maxPriceCoupons.add(coupon);
			}
		}
		return maxPriceCoupons;
	}
	public Customer getCustomerDetails() throws CouponSystemException {
		return this.customersDAO.getOneCustomer(customerId);
	}
}
