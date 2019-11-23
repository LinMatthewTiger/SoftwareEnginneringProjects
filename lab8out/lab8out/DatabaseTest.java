package lab8out;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;



public class DatabaseTest {

	String[] users = {"jsmith@uca.edu","msmith@uca.edu","tjones@yahoo.com","jjones@yahoo.com"};
	String[] passwords = {"hello123","pass123","123456","hello1234"};

	private Database db; 
		
	private int rando;
	
	
	@Before
	public void testsetConnection() throws Exception 
	{
	   db = new Database();
	   db.setConnection("lab8out/db.properties"); 
	}
	
	@Test
	public void testQuery() throws IOException 
	{
		 //Use Random # to extract username/ and expected password
	     rando = ((int)Math.random()*users.length); 
				
		 String username = users[rando]; 
		 String expected = passwords[rando];
		 
		 String queryString = "SELECT AES_DECRYPT(password,'key') FROM users where username=\"" + username + "\";";
		 //get actual result (invoke query with username
		 ArrayList<String> p = db.query(queryString);
	
		 
				
		//compare expected with actual using assertEquals 
		 // Password needs to be varbinary
		 assertEquals("Password does not match", p.get(0) ,expected);
			
			
	}
	
	
	@Test  //Test for bad user name
	public void testExecuteDML() throws IOException
	{
		String username = "mlin4@cub.uca.edu";
		String password = "password";
		String dml = "insert into users values('" + username + "',aes_encrypt('" + password + "','key'))";
		try {
			db.executeDML(dml);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	}


}
