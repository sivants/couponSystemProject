package facade;

import DAOclasses.CouponsDAO;
import DAOclasses.CouponsDbDAO;
import beans.Category;
import beans.Coupon;
import exceptions.CouponSystemException;
import tasks.LoginManager;

public class FacadeTest3 {
	public static void main(String[] args) {
	
			LoginManager logM = LoginManager.getInstance();
			CouponsDAO couponDAO = new CouponsDbDAO();
			
			try {
				Coupon coupon = new Coupon();
				coupon.setAmount(300);
				coupon.setCategory(Category.FOOD);
				coupon.setCompanyId(2);
				coupon.setDescription(null);
				coupon.setEndDate(java.sql.Date.valueOf("2019-12-20"));
				coupon.setStartDate(null);
				coupon.setImage("image2");
				coupon.setPrice(13.00);
				coupon.setTittle("test2");
				int couponId = couponDAO.addCoupon(coupon);
				coupon.setId(couponId);
				
//				Customer customer = new Customer("Mali", "Cohen", "mali@", "1122");
//				int customerId = customerDAO.addCustomer(customer);
//				customer.setId(customerId);
				
				//couponDAO.addCouponPurchase(207, 202);
				CustomerFacade cusFacade = (CustomerFacade) logM.login("mali@", "1122", ClientType.CUSTOMER);
				System.out.println(cusFacade.getCustomerDetails());
				System.out.println(cusFacade.getCustomerCoupons());
				cusFacade.purchaseCoupon(couponId);
				System.out.println(cusFacade.getCustomerCoupons());
				System.out.println(cusFacade.getCustomerCoupons(Category.FOOD));
				System.out.println(cusFacade.getCustomerCoupons(100));
				
			//	couponDAO.deleteCouponPurchase(206, couponId);
				//couponDAO.deleteCoupon(couponId);
			} catch (CouponSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
