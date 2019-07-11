package tasks;

import DAOclasses.CompaniesDbDAO;
import DAOclasses.CouponsDbDAO;
import DAOclasses.CustomersDbDAO;
import exceptions.CouponSystemException;
import facade.AdminFacade;
import facade.ClientFacade;
import facade.ClientType;
import facade.CompanyFacade;
import facade.CustomerFacade;

public class LoginManager {

	private static LoginManager instance;
	CustomersDbDAO customersDAO = new CustomersDbDAO();
	CouponsDbDAO couponsDAO = new CouponsDbDAO();
	CompaniesDbDAO companiesDAOb = new CompaniesDbDAO();
	
	private LoginManager() {
		super();
	}

	public static LoginManager getInstance() {
		if (instance == null) {
			instance = new LoginManager();
		}
		return instance;
	}

	public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {
		AdminFacade adminFacade;
		CustomerFacade customerFacade;
		CompanyFacade companyFacade; 
		switch (clientType) {
		case ADMINISTRATOR:
			adminFacade = new AdminFacade(); 
			adminFacade.login(email, password);
			return adminFacade;
		case COMPANY:
			companyFacade = new CompanyFacade();
			companyFacade.login(email, password);	
			return companyFacade;
		case CUSTOMER:
			customerFacade = new CustomerFacade(); 
			customerFacade.login(email, password);	
			return customerFacade;
		}
		return null;
	}
}
