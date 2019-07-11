package facade;

import DAOclasses.CompaniesDAO;
import DAOclasses.CompaniesDbDAO;
import beans.Category;
import beans.Coupon;
import exceptions.CouponSystemException;
import tasks.LoginManager;

public class FacadeTest2 {
	public static void main(String[] args) {
	
		LoginManager log = LoginManager.getInstance();
		try {
			
			CompanyFacade comFacade = (CompanyFacade) log.login("email33", "333", ClientType.COMPANY);
			
//			Coupon coupon = new Coupon();
//			coupon.setCompanyId(2);
//			coupon.setAmount(20);
//			coupon.setCategory(Category.FOOD);
//			coupon.setCategoryId(1);
//			coupon.setDescription("");
//			coupon.setEndDate(null);
//			coupon.setStartDate(null);
//			coupon.setImage("");
//			coupon.setTittle("couTest2");
//			coupon.setPrice(10);
//			
			CompaniesDAO companiesDAO = new CompaniesDbDAO();
			System.out.println(companiesDAO.getCompanyIdByMailPassword("email33", "333"));
//			CouponsDbDAO couponsDAO = new CouponsDbDAO();
//			System.out.println(comFacade.getCompanyCoupons(2));
		//	comFacade.addCoupon(coupon);
			//System.out.println(couponsDAO.getOneCoupon(coupon.getId()));
			System.out.println(comFacade.getCompanyCoupons(Category.FOOD));
			System.out.println(comFacade.getCompanyCoupons(50.9));
			System.out.println(comFacade.getCompanyCoupons(2));
			//System.out.println(comFacade.getCompanyDetails(2));
			
			//coupon.setAmount(35);
			//coupon.setPrice(30);
//			System.out.println(couponsDAO.getOneCoupon(coupon.getId()));
//			comFacade.updateCoupon(coupon);
//			System.out.println(couponsDAO.getOneCoupon(coupon.getId()));
		//	System.out.println(coupon);
			comFacade.deleteCoupon(202);
			//comFacade.deleteCoupon(coupon.getId());
			//System.out.println(couponsDAO.getOneCoupon(couponId));
			
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}
}
