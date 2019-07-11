package facade;

import DAOclasses.CompaniesDAO;
import DAOclasses.CouponsDAO;
import DAOclasses.CustomersDAO;
import exceptions.CouponSystemException;

public abstract class ClientFacade {
	
	protected CompaniesDAO companiesDAO ;
	protected CustomersDAO customersDAO;
	protected CouponsDAO couponsDAO;
	
	protected ClientFacade(CustomersDAO customersDAO) {
		super();
		this.customersDAO = customersDAO;
	}

	protected ClientFacade(CompaniesDAO companiesDAO) {
		super();
		this.companiesDAO = companiesDAO;
	}

	public ClientFacade(CouponsDAO couponsDAO) {
		super();
		this.couponsDAO = couponsDAO;
	}
	protected ClientFacade(CompaniesDAO companiesDAO, CustomersDAO customersDAO, CouponsDAO couponsDAO) {
		super();
		this.companiesDAO = companiesDAO;
		this.customersDAO = customersDAO;
		this.couponsDAO = couponsDAO;
	}

	protected ClientFacade() {
		
	}
	
	public abstract boolean login(String email, String password) throws CouponSystemException;
	
}
