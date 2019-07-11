package facade;

import DAOclasses.CouponsDAO;
import DAOclasses.CouponsDbDAO;
import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemException;

public class FacadeTest1 {

	public static void main(String[] args) { //throws CouponSystemException {
		AdminFacade admin = new AdminFacade();
		try {
			admin.login("admin@admin.com", "admin");
			Company company = new Company();
			company.setName("comp1");
			company.setEmail("comp1@");
			company.setPassword("111");
			admin.addCompany(company);
			System.out.println("addCompany succeded");
			Customer customer = new Customer();
			customer.setFirstName("Shay");
			customer.setLastName("Lev");
			customer.setEmail("shay@");
			customer.setPassword("333");
			int customerId = admin.addCustomer(customer);
			System.out.println("addCustomer succeded");
			System.out.println(customerId);
			customer.setPassword("222");
			System.out.println(customer);
			admin.updateCustomer(customer);
			System.out.println(customer);
			System.out.println(admin.getAllCompanies());
			System.out.println(admin.getAllCustomers());
			System.out.println(admin.getOneCompany(14));
			System.out.println(admin.getOneCustomer(201));
			Company company1 = new Company();
			company.setId(210);
			company.setName("comp1");
			company.setEmail("comp5@");
			company.setPassword("555");
			admin.updateCompany(company1);
			System.out.println("updateCompany succeded");
			Coupon coupon = new Coupon();
			coupon.setCompanyId(14);
			coupon.setAmount(20);
			coupon.setCategory(Category.FOOD);
			coupon.setCategoryId(1);
			coupon.setDescription("blabla");
			coupon.setEndDate(null);
			coupon.setStartDate(null);
			coupon.setImage("blabla");
			coupon.setTittle("blabla");
			coupon.setPrice(12.5);
			CouponsDAO couponsDAO = new CouponsDbDAO();
			int x = couponsDAO.addCoupon(coupon);
			coupon.setId(x);
			System.out.println(couponsDAO.getCompanyCoupons(14));
			admin.deleteCompany(210);
			System.out.println("deleteCompany succeded");
			System.out.println(couponsDAO.getCustomerCoupons(201));
			admin.deleteCustomer(201);
			System.out.println("deleteCustomer succeded");
			
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}
	
}
