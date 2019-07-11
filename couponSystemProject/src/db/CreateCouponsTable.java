package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateCouponsTable {

	public static void main(String[] args) {

	String url = "jdbc:derby://localhost:1527/db1";
	try (
	Connection con = DriverManager.getConnection(url + ";create = true");
	Statement stmt = con.createStatement();
		){
		String sqlCreateTable= "create table coupons("
				+ "id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\r\n" +
				"company_id INTEGER," +
				"category_id INTEGER,\r\n" +
				"tittle VARCHAR(10),\r\n" + 
				"description VARCHAR(80),\r\n" + 
				"start_date DATE,\r\n" +
				"end_date DATE,\r\n" +
				"amount INTEGER," + 
				"price DOUBLE," + 
				"image VARCHAR(40)," +
				"PRIMARY KEY(id)," +
				"FOREIGN KEY(company_id) REFERENCES companies(id)," +
				"FOREIGN KEY(category_id) REFERENCES categories(id))";
		
		stmt.execute(sqlCreateTable);
		}
	catch (SQLException e) {
		e.printStackTrace();
	}
	}
}
