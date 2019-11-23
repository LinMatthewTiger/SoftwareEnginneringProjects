package ClientInterface;

import javax.swing.*;

import ClientComm.CreateAccountControl;
import ClientComm.InitialControl;
import ClientComm.LoginControl;
import ServerComm.GameClient;

import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{
  private GameClient client;
	
	public ClientGUI()
  {
    this.setTitle("Client");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    client = new GameClient();
    try
		{
			client.openConnection();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
        
    //Create the card layout container
    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);
    
    //Create the controllers
    InitialControl ic = new InitialControl(container); 
    LoginControl lc = new LoginControl(container,client);
    CreateAccountControl cc = new CreateAccountControl(container,client);
//    ContactControl conc = new ContactControl(container, client);
    
    //Set the controllers in ChatClient
    client.setLoginControl(lc);
    client.setCreateControl(cc);
    
    /*Create the views and associate them with their corresponding controllers
	    View 1: Initial panel
	    View 2: Login panel
	    View 3: Create panel
	   */
    JPanel view1 = new InitialPanel(ic);
    JPanel view2 = new LoginPanel(lc);
    JPanel view3 = new CreateAccountPanel(cc);
    
    //Add the views to the card layout container
    container.add(view1, "1");
    container.add(view2, "2");
    container.add(view3, "3");
    
    //Show the initial view in the card layout
    cardLayout.show(container, "1");
    
    //Add the card layout container to the JFrame
    this.add(container, BorderLayout.CENTER);

    //Show the JFrame
    this.setSize(550, 390);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  public static void main(String[] args)
  {
    new ClientGUI();
  }
}
