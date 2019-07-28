package DAOclasses;

import beans.Category;
import beans.Coupon;
import exceptions.CouponSystemException;

public class Test2 {

	public static void main(String[] args) {
		
		Coupon coupon = new Coupon();
		coupon.setCategoryId(0);
		coupon.setEndDate(null);
		coupon.setStartDate(null);
		coupon.setImage("Image");
		coupon.setAmount(150);
		coupon.setDescription("");
		coupon.setPrice(70.00);
		coupon.setTittle("testA");
		coupon.setCompanyId(2);
		coupon.setCategory(Category.FOOD);
	
	CouponsDAO dao = new CouponsDbDAO();
	try {
		System.out.println(dao.getAllCoupons());
		int couponId = dao.addCoupon(coupon);
		System.out.println(dao.getAllCoupons());
		System.out.println(dao.getOneCoupon(couponId));
		System.out.println(dao.getCompanyCoupons(2));
		dao.subtructCouponAmount(coupon);
		System.out.println(dao.getOneCoupon(couponId));
		dao.deleteCoupon(couponId);
	} catch (CouponSystemException e) {
		e.printStackTrace();
	}
	
}
}
