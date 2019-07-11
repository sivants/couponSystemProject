package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateCustomersTable {

	public static void main(String[] args) {

	String url = "jdbc:derby://localhost:1527/db1";{
	try (
	Connection con = DriverManager.getConnection(url + ";create = true");
	Statement stmt = con.createStatement();
		){
		String sqlCreateTable= "create table customers("
				+ "id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\r\n" +
				"first_name VARCHAR(10),\r\n" + 
				"last_name VARCHAR(20),\r\n" + 
				"email VARCHAR(20),\r\n" +
				"password VARCHAR (10) UNIQUE NOT NULL," +
				"PRIMARY KEY(id))";
		
		stmt.execute(sqlCreateTable);
		
				
				
		}
	catch (SQLException e) {
		e.printStackTrace();
	}
	}
}
}