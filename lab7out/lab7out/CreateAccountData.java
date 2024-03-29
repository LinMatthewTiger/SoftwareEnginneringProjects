package lab7out;
import java.io.Serializable;

public class CreateAccountData implements Serializable
{
  // Private data fields for the username and passwords.
  private String username = "";
  private String password = "";
  private String password2 = "";
  
  // Getters for the username and passwords.
  public String getUsername()
  {
    return username;
  }
  public String getPassword()
  {
    return password;
  }
  
  // Setters for the username and passwords.
  public void setUsername(String username)
  {
    this.username = username;
  }
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public void setPassword2(String password2)
  {
    this.password2 = password2;
  }
  
  public String getPassword2()
  {
    return password2;
  }
  
  // Constructor that initializes the username and password.
  public CreateAccountData(String username, String password, String password2)
  {
    setUsername(username);
    setPassword(password);
    setPassword2(password2);
  }
}