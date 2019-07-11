package facade;

import DAOclasses.CouponsDAO;
import DAOclasses.CouponsDbDAO;
import DAOclasses.CustomersDAO;
import DAOclasses.CustomersDbDAO;
import beans.Category;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemException;
import tasks.LoginManager;

public class FacadeTest3 {
	public static void main(String[] args) {
	
			LoginManager logM = LoginManager.getInstance();
			CouponsDAO couponDAO = new CouponsDbDAO();
			CustomersDAO customerDAO = new CustomersDbDAO();
			
			try {
				Coupon coupon = new Coupon();
				coupon.setAmount(700);
				coupon.setCategory(Category.FOOD);
				coupon.setCompanyId(2);
				coupon.setDescription(null);
				coupon.setEndDate(java.sql.Date.valueOf("2019-04-16"));
				coupon.setStartDate(null);
				coupon.setImage(null);
				coupon.setPrice(8.00);
				coupon.setTittle("1+1 free");
				int couponId = couponDAO.addCoupon(coupon);
				coupon.setId(couponId);
				
				Customer customer = new Customer("Mali", "Cohen", "mali@", "1122");
				int customerId = customerDAO.addCustomer(customer);
				customer.setId(customerId);
				
				//couponDAO.addCouponPurchase(207, 202);
				CustomerFacade cusFacade = (CustomerFacade) logM.login("mali@", "1122", ClientType.CUSTOMER);
				System.out.println(cusFacade.getCustomerDetails());
				System.out.println(cusFacade.getCustomerCoupons());
				//cusFacade.purchaseCoupon(203);
				System.out.println(cusFacade.getCustomerCoupons());
				System.out.println(cusFacade.getCustomerCoupons(Category.VACATION));
				System.out.println(cusFacade.getCustomerCoupons(100));
				
				couponDAO.deleteCouponPurchase(206, 203);
				couponDAO.deleteCoupon(209);
				customerDAO.deleteCustomer(206);
			} catch (CouponSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
