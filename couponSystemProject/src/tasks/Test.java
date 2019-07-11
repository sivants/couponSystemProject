package tasks;

import DAOclasses.CouponsDbDAO;
import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import db.ConnectionPool;
import exceptions.CouponSystemException;
import facade.AdminFacade;
import facade.ClientType;
import facade.CompanyFacade;
import facade.CustomerFacade;

public class Test {
	//@SuppressWarnings("deprecation")
	//public static void main(String[] args) throws CouponSystemException {
//connectionPool
	
	CouponExpirationDailyJob job;
	LoginManager loginManager;
	
	public void testAll() throws CouponSystemException, InterruptedException{
		ClientType loggedAs;
		
		ConnectionPool.getInstance();
		job = new CouponExpirationDailyJob();
		Thread t = new Thread(job, "jobThread");
		loginManager = LoginManager.getInstance(); 
		loggedAs = ClientType.ADMINISTRATOR;
		Company company = new Company("comp6", "email6", "666");
		Customer customer = new Customer("Dina", "Lev", "dina@", "dinaL");
		try {
			t.start();
			AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", loggedAs);
			int companyId = adminFacade.addCompany(company);
			System.out.println("adding company succeded");
			int customerId = adminFacade.addCustomer(customer);
			System.out.println("adding customer succeded");
			System.out.println("List of all companies: " + adminFacade.getAllCompanies());
			System.out.println("List of all customers: " + adminFacade.getAllCustomers());
			System.out.println("Testing company update:");
			System.out.println("before: " + adminFacade.getOneCompany(companyId));
			company.setEmail("comp1@.com");
			adminFacade.updateCompany(company);
			System.out.println("after: " + adminFacade.getOneCompany(companyId));
			System.out.println("Testing customer update:");
			System.out.println("before: " + adminFacade.getOneCustomer(customerId));
			customer.setLastName("Levi");
			adminFacade.updateCustomer(customer);
			System.out.println("after: " + adminFacade.getOneCustomer(customerId));
			adminFacade.deleteCompany(companyId);
			adminFacade.deleteCustomer(customerId);
			System.out.println("Testing admin succeded");
			
			loggedAs = ClientType.COMPANY;
			CompanyFacade comFacade = (CompanyFacade) loginManager.login("email2", "222", loggedAs);
			
			Coupon coupon = new Coupon();
			coupon.setCompanyId(3);
			coupon.setAmount(200);
			coupon.setCategory(Category.FOOD);
			coupon.setCategoryId(1);
			coupon.setDescription("aaa");
			coupon.setEndDate(null);
			coupon.setStartDate(null);
			coupon.setImage("aaaa");
			coupon.setTittle("couponTest");
			coupon.setPrice(15);
			
			CouponsDbDAO couponsDAO = new CouponsDbDAO();
			System.out.println(comFacade.getCompanyCoupons(3));
			comFacade.addCoupon(coupon);
			System.out.println(couponsDAO.getOneCoupon(coupon.getId()));
			System.out.println(comFacade.getCompanyCoupons(Category.FOOD));
			System.out.println(comFacade.getCompanyCoupons(50.9));
			System.out.println(comFacade.getCompanyCoupons(3));
			System.out.println(comFacade.getCompanyDetails(3));
			
			System.out.println("Testing coupon update:");
			coupon.setAmount(35);
			coupon.setPrice(12);
			System.out.println("before: " + couponsDAO.getOneCoupon(coupon.getId()));
			comFacade.updateCoupon(coupon);
			System.out.println("after: " + couponsDAO.getOneCoupon(coupon.getId()));
			comFacade.deleteCoupon(coupon.getId());
			System.out.println("Testing companyFacade succeded");
			
			loggedAs = ClientType.CUSTOMER;
			CustomerFacade cusFacade = (CustomerFacade) loginManager.login("mali@", "1122", loggedAs);
			
			System.out.println(cusFacade.getCustomerDetails());
			System.out.println("" + cusFacade.getCustomerCoupons());
			cusFacade.purchaseCoupon(203);
			System.out.println(cusFacade.getCustomerCoupons());
			System.out.println(cusFacade.getCustomerCoupons(Category.VACATION));
			System.out.println(cusFacade.getCustomerCoupons(100));
			System.out.println("Testing customerFacade succeded");

			//the next line is needed for running this code repeetedly 
			couponsDAO.deleteCouponPurchase(206, 203);
			
			job.quit();
			
		} catch (CouponSystemException e) {
			e.printStackTrace();
			//System.out.println(e);
//		}finally {
//			job.quit();
//			t.interrupt();
//			t.join();
//			ConnectionPool.getInstance().closeAllConnections();
		}
	}
}

//}