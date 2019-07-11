package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateCustomersVsCouponsTable {

	public static void main(String[] args) {

		String url = "jdbc:derby://localhost:1527/db1";
	  try (
			Connection con = DriverManager.getConnection(url + ";create = true");
			Statement stmt = con.createStatement();
			){
			String sqlCreateTable= "create table customers_vs_coupons(" +
			"customer_id int not null REFERENCES customers(id), \r\n" +
			"coupon_id int not null REFERENCES coupons(id), \r\n " +
			"PRIMARY KEY(customer_id, coupon_id))"; 
				
			stmt.execute(sqlCreateTable);
			
	} catch (SQLException e) {
		e.printStackTrace();
	}

	}
	}
