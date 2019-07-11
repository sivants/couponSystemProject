package DAOclasses;

import beans.Company;
import exceptions.CouponSystemException;

public class Test1 {
	public static void main(String[] args) {
		Company co1 = new Company("co1", "email1", "111");
		Company co2 = new Company();
		Company co3 = new Company();
		Company co4 = new Company();
		
		co1.getName();
		
		co2.setEmail("email2");
		co2.setName("co2");
		co2.setPassword("222");
		
		co3.setEmail("email3");
		co3.setName("co3");
		co3.setPassword("333");
		
		co4.setEmail("email4");
		co4.setName("co4");
		co4.setPassword("444");
		
		CompaniesDAO companyDao = new CompaniesDbDAO();
		try {
			int id = companyDao.addCompany(co1);
			companyDao.addCompany(co2);
			companyDao.addCompany(co4);
//			System.out.println(id);
//			System.out.println(companyDao.isCompanyExists("email2", "222"));
//			co1.setPassword("45678");
//			co3.setEmail("email33");
//			co1.setId(10);
//			co3.setId(id);
//			companyDao.updateCompany(co1);
//			companyDao.updateCompany(co3);
//			//System.out.println(companyDao.getAllCompanies());
			//companyDao.deleteCompany(12);
			System.out.println(companyDao.getAllCompanies());
			System.out.println(companyDao.getOneCompany(id));
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		
	}

}
