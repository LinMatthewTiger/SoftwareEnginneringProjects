package lab7out;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CreateAccountPanel extends JPanel
{
  // Private data fields for the important GUI components.
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JPasswordField passwordField2;
  private JLabel errorLabel;
  
  // Getter for the text in the username field.
  public String getUsername()
  {
    return usernameField.getText();
  }
  
  // Getter for the text in the password field.
  public String getPassword()
  {
    return new String(passwordField.getPassword());
  }
  
  public String getPassword2()
  {
    return new String(passwordField2.getPassword());
  }
  
  // Setter for the error text.
  public void setError(String error)
  {
    errorLabel.setText(error);
  }
  
  // Constructor for the login panel.
  public CreateAccountPanel(CreateAccountControl cp)
  {
    // Create the controller and set it in the chat client.
    //LoginControl controller = new LoginControl(container, client);
    //client.setLoginControl(controller);
        
    // Create a panel for the labels at the top of the GUI.
    JPanel labelPanel = new JPanel(new GridLayout(3, 1, 5, 5));
    errorLabel = new JLabel("", JLabel.CENTER);
    errorLabel.setForeground(Color.RED);
    JLabel instructionLabel = new JLabel("Enter your username and password to create an account.", JLabel.CENTER);
    JLabel instructionLabel2 = new JLabel("Your password must be 6 characters long.", JLabel.CENTER);
    labelPanel.add(errorLabel);
    labelPanel.add(instructionLabel);
    labelPanel.add(instructionLabel2);

    // Create a panel for the login information form.
    JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
    JLabel usernameLabel = new JLabel("Username:", JLabel.RIGHT);
    usernameField = new JTextField(10);
    JLabel passwordLabel = new JLabel("Password:", JLabel.RIGHT);
    passwordField = new JPasswordField(10);
    JLabel verifyLabel = new JLabel("Verify Password:", JLabel.RIGHT);
    passwordField2 = new JPasswordField(10);
    loginPanel.add(usernameLabel);
    loginPanel.add(usernameField);
    loginPanel.add(passwordLabel);
    loginPanel.add(passwordField);
    loginPanel.add(verifyLabel);
    loginPanel.add(passwordField2);
    
    // Create a panel for the buttons.
    JPanel buttonPanel = new JPanel();
    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(cp);
    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(cp);    
    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);

    // Arrange the three panels in a grid.
    JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
    grid.add(labelPanel);
    grid.add(loginPanel);
    grid.add(buttonPanel);
    this.add(grid);
  }
}