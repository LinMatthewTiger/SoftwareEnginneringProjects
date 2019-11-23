package lab8out;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Database
{
  private Connection conn;
  //Add any other data fields you like – at least a Connection object is mandatory
  public void setConnection(String fn) throws IOException 
	{
		//Add your code here
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(fn);
		prop.load(fis);
		String url = prop.getProperty("url");
		String user = prop.getProperty("user");
		String pass = prop.getProperty("password"); 
		//Perform the Connection
		try
		{
			conn = DriverManager.getConnection(url,user,pass);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

  public Connection getConnection()
  {
    return conn;
  }

  public ArrayList<String> query(String query) 
  {
    //Add your code here
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
