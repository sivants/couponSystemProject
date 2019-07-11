package DAOclasses;

import beans.Category;
import beans.Coupon;
import exceptions.CouponSystemException;

public class Test2 {

	public static void main(String[] args) {
		
		Coupon coupon = new Coupon();
		coupon.setCategoryId(2);
		coupon.setEndDate(null);
		coupon.setStartDate(null);
		coupon.setImage("cImage");
		coupon.setAmount(150);
		coupon.setDescription("");
		coupon.setPrice(70.00);
		coupon.setTittle("aaa");
		coupon.setCompanyId(2);
		coupon.setCategory(Category.RESTAURANT);
	
	CouponsDAO dao = new CouponsDbDAO();
	try {
		int couponId = dao.addCoupon(coupon);
		System.out.println(dao.getOneCoupon(couponId));
		System.out.println(dao.getAllCoupons());
		//dao.deleteCoupon(104);
		System.out.println(dao.getAllCoupons());
		
	} catch (CouponSystemException e) {
		e.printStackTrace();
	}
	
}
}
