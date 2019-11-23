package lab7out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	
	private Connection conn;
	private Statement stmt;
	  //Add any other data fields you like – at least a Connection object is mandatory
	  public Database()
	  {
	    //Add your code here
		  try {
			conn = DriverManager.getConnection(  
			          "jdbc:mysql://localhost:3306/student_space","student","hello");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  

	  }
	  
	  public ArrayList<String> query(String query)
	  {
		  Statement stmt;
		  ResultSet rs;
		  ResultSetMetaData rsmd;
		  int colcount;
		  ArrayList<String> s = new ArrayList<String>();
	    //Add your code here
		  try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rsmd = rs.getMetaData();
			colcount=rsmd.getColumnCount();
			while(rs.next()) 
			{
				int i = 1;
				while(i <= colcount) {
					s.add(rs.getString(i++));
				}


			}
			return s;
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		  
	  }
	  
	  public void executeDML(String dml) throws SQLException
	  {
	    //Add your code here
		  Statement stmt = conn.createStatement();
		  stmt.execute(dml);
	  }

}
