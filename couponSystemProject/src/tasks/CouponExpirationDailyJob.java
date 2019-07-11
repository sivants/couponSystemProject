package tasks;

import java.util.Collection;

import DAOclasses.CouponsDAO;
import DAOclasses.CouponsDbDAO;
import beans.Coupon;
import exceptions.CouponSystemException;

public class CouponExpirationDailyJob implements Runnable {

	private boolean quit;
	private CouponsDAO couponsDAO = new CouponsDbDAO();
	private Thread taskThread;
	
	public CouponExpirationDailyJob() {
	}

	public CouponExpirationDailyJob(boolean quit, CouponsDbDAO couponsDAO) {
		super();
		this.quit = quit;
		this.couponsDAO = couponsDAO;
	}

	@Override
	public void run() {
		while (!quit) {
			try {
				Collection<Coupon> allExpiredCoupons = couponsDAO.getExpiredCoupons();
				for (Coupon currCoupon : allExpiredCoupons) {
					couponsDAO.deleteCoupon(currCoupon.getId());
				}
				Thread.sleep(1000*60*60*24);
			} catch (CouponSystemException e) {
				System.out.println("daily job not working");
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				break;
			}
		}
	}

	public void quit() {
		quit = true;
		taskThread.interrupt();
	}
}
