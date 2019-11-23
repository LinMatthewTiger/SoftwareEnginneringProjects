package lab7out;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateAccountControl implements ActionListener
{
  // Private data fields for the container and chat client.
  private JPanel container;
  private ChatClient client;
  
  
  // Constructor for the login controller.
  public CreateAccountControl(JPanel container, ChatClient client)
  {
    this.container = container;
    this.client = client;
    this.client.setCreateAccountControl(this);
   
  }
  



public void actionPerformed(ActionEvent ae) {
	// TODO Auto-generated method stub
	
	// Get the name of the button clicked.
    String command = ae.getActionCommand();
   
    
    
	  // The Cancel button takes the user back to the initial panel.
	  if (command == "Cancel")
	  {
	    CardLayout cardLayout = (CardLayout)container.getLayout();
	    cardLayout.show(container, "1");
	  }
	  // The Submit button submits the login information to the server.
	    else if (command == "Submit")
	    {
	      // Get the username and password the user entered.
	      CreateAccountPanel createPanel = (CreateAccountPanel)container.getComponent(2);
	      CreateAccountData data = new CreateAccountData(createPanel.getUsername(), createPanel.getPassword(), createPanel.getPassword2());
	      
	      // Check the validity of the information locally first.
	      if (data.getUsername().equals("") || data.getPassword().equals(""))
	      {
	        displayError("You must enter a username and password.");
	        return;
	      }
	      else if (!data.getPassword2().equals(data.getPassword()))
	      {
	        displayError("Passwords do not match");
	        return;
	      }
	      else if (data.getPassword().length() < 6)
	      {
	        displayError("Password must be at least 6 characters long");
	        return;
	      }
	    	  displayError("");
	    	  
	    	// Submit the login information to the server.
		      try {
				client.sendToServer((CreateAccountData)data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	      
	     
	    }
	    }


//Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
public void displayError(String error)
{
 CreateAccountPanel createPanel = (CreateAccountPanel)container.getComponent(2);
 createPanel.setError(error);
 
}

public void taken() {
	displayError("Username has already been selected");
}

}
  