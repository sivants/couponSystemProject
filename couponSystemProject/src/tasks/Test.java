package tasks;

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
//	public static void main(String[] args) throws CouponSystemException {
	
	public void testAll() throws CouponSystemException, InterruptedException{
		ClientType loggedAs;
		
		ConnectionPool.getInstance();
		CouponExpirationDailyJob job = new CouponExpirationDailyJob();
		Thread t = new Thread(job, "jobThread");
		job.setTaskThread(t);
		LoginManager loginManager = LoginManager.getInstance(); 
		Company company = new Company("comp9", "email9", "999");
		Customer customer = new Customer("Moran", "Shalom", "moran@", "moran111");
		try {
			t.start();
			loggedAs = ClientType.ADMINISTRATOR;
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
			System.out.println("Testing admin succeded");
			
			loggedAs = ClientType.COMPANY;
			CompanyFacade comFacade = (CompanyFacade) loginManager.login(company.getEmail(), company.getPassword(), loggedAs);
			
			Coupon coupon1 = new Coupon();
			coupon1.setCompanyId(companyId);
			coupon1.setAmount(200);
			coupon1.setCategory(Category.FOOD);
			coupon1.setCategoryId(1);
			coupon1.setDescription("aaa");
			coupon1.setEndDate(java.sql.Date.valueOf("2020-04-16"));
			coupon1.setStartDate(null);
			coupon1.setImage("aImage");
			coupon1.setTittle("couponTest");
			coupon1.setPrice(15);
			
			Coupon coupon2 = new Coupon();
			coupon2.setCompanyId(companyId);
			coupon2.setAmount(800);
			coupon2.setCategory(Category.FOOD);
			coupon2.setCategoryId(1);
			coupon2.setDescription("bbb");
			coupon2.setEndDate(java.sql.Date.valueOf("2019-12-20"));
			coupon2.setStartDate(null);
			coupon2.setImage("bImage");
			coupon2.setTittle("coupon2");
			coupon2.setPrice(7.5);

//			Coupon coupon3 = new Coupon();
//			coupon3.setCompanyId(companyId);
//			coupon3.setAmount(150);
//			coupon3.setCategory(Category.FOOD);
//			coupon3.setCategoryId(1);
//			coupon3.setDescription("ccc");
//			coupon3.setEndDate(null);
//			coupon3.setStartDate(null);
//			coupon3.setImage("cImage");
//			coupon3.setTittle("coupon3");
//			coupon3.setPrice(40);
			
			System.out.println(comFacade.getCompanyCoupons());
			int coupon1Id = comFacade.addCoupon(coupon1);
			int coupon2Id = comFacade.addCoupon(coupon2);
			System.out.println(comFacade.getCompanyCoupons(Category.FOOD));
			System.out.println(comFacade.getCompanyCoupons(50.9));
			System.out.println(comFacade.getCompanyCoupons(companyId));
			System.out.println(comFacade.getCompanyDetails(companyId));
			
			System.out.println("Testing coupon update:");
			coupon1.setAmount(35);
			coupon1.setPrice(12);
			System.out.println("before: " + comFacade.getOneCoupon(coupon1Id));
			comFacade.updateCoupon(coupon1);
			System.out.println("after: " + comFacade.getOneCoupon(coupon1Id));
			System.out.println("Testing companyFacade succeded");
			
			loggedAs = ClientType.CUSTOMER;
			CustomerFacade cusFacade = (CustomerFacade) loginManager.login(customer.getEmail(), customer.getPassword(), loggedAs);
			
			System.out.println("Customer details: " + cusFacade.getCustomerDetails());
			System.out.println("This Customer Coupons: " + cusFacade.getCustomerCoupons());
			cusFacade.purchaseCoupon(coupon1Id);
			System.out.println(cusFacade.getCustomerCoupons());
			System.out.println(cusFacade.getCustomerCoupons(Category.FOOD));
			System.out.println(cusFacade.getCustomerCoupons(20));
			System.out.println("Testing customerFacade succeded");

			comFacade.deleteCoupon(coupon1Id);
			comFacade.deleteCoupon(coupon2Id);
			adminFacade.deleteCustomer(customerId);
			adminFacade.deleteCompany(companyId);
			
		} catch (CouponSystemException e) {
			e.printStackTrace();
			System.out.println(e);
		}finally {
			job.quit();
			t.interrupt();
			t.join();
			ConnectionPool.getInstance().closeAllConnections();
		}
	}
}

