package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateCategoriesTable {

	public static void main(String[] args) {

		String url = "jdbc:derby://localhost:1527/db1";
	  try (
			Connection con = DriverManager.getConnection(url + ";create = true");
			Statement stmt = con.createStatement();
			){
			String sqlCreateTable= "create table categories(" +
			"id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1), \r\n" +
			"name VARCHAR (20), \r\n" +
			 "PRIMARY KEY (id))"; 
				
			stmt.execute(sqlCreateTable);
			
	} catch (SQLException e) {
		e.printStackTrace();
	}

	}
	}
