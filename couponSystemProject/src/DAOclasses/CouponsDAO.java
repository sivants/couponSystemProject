package DAOclasses;

import java.util.Collection;

import beans.Coupon;
import exceptions.CouponSystemException;

public interface CouponsDAO {

	public int addCoupon(Coupon coupon) throws CouponSystemException;
	
	public void updateCoupon(Coupon coupon) throws CouponSystemException;
	
	public void subtructCouponAmount(Coupon coupon) throws CouponSystemException;

	public void deleteCoupon(int couponId) throws CouponSystemException;

	public void deleteAllCompanyCoupons(int companyId) throws CouponSystemException;
	
	public void deleteAllCustomerCoupons(int customerId) throws CouponSystemException;
	
	public void deleteCompanyCouponPurchase(int companyId) throws CouponSystemException;
	
	public Collection<Coupon> getAllCoupons() throws CouponSystemException;
	
	public Collection<Coupon> getCompanyCoupons(int companyId) throws CouponSystemException;
	
	public Collection<Coupon> getCustomerCoupons(int customerId) throws CouponSystemException;
	
	public Coupon getOneCoupon(int CouponId) throws CouponSystemException;
	
	public void addCouponPurchase (int customerId, int couponId) throws CouponSystemException;
	
	public void deleteCouponPurchase(int customerId, int couponId) throws CouponSystemException;
	
	public void deleteCouponPurchaseByCustomer(int customerId) throws CouponSystemException;

	public void deleteCouponPurchaseById(int couponId) throws CouponSystemException;

	public Collection<Coupon> getExpiredCoupons() throws CouponSystemException ;


	
	
	
	
}
