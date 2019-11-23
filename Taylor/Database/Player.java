package Database;

import java.io.Serializable;

public class Player implements Serializable
{
	private int id;
	private String username;
	private String password;
	static int userID = 0;
	
	public Player(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public void setID()
	{
		id = userID;
		userID++;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public int getID()
	{
		return id;
	}
}
