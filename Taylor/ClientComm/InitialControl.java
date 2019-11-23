package ClientComm;

import java.awt.*;
import javax.swing.*;

import ClientInterface.CreateAccountPanel;
import ClientInterface.LoginPanel;

import java.awt.event.*;

public class InitialControl implements ActionListener
{
  private JPanel container;
 
  public InitialControl(JPanel container)
  {
    this.container = container;
  }
  
  //Handle button clicks
  public void actionPerformed(ActionEvent ae)
  {
    //Get the name of the button clicked.
    String command = ae.getActionCommand();
    
    //The Login button takes the user to the Login panel
    if (command.equals("Log In"))
    {
      LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
      loginPanel.setError("");
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "2");
    }
    
    //The Create button takes the user to the create account panel
    else if (command.equals("Create"))
    {
    	CreateAccountPanel createPanel = (CreateAccountPanel)container.getComponent(2);
      createPanel.setError("");
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "3");
    }
  }
}
