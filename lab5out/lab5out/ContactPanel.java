package lab5out;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ContactPanel extends JPanel
{
  private JLabel staticContactLbl = new JLabel("Contacts", JLabel.CENTER);
  private JTextArea contactList = new JTextArea("Mark, \nSam");
  private JButton deleteContact = new JButton("Delete Contact");
  private JButton addContact = new JButton("Add Contact");
  private JButton logout = new JButton("Log Out");
  
  public ContactPanel()
  {    
    JPanel contactPane1 = new JPanel(new GridLayout(2, 1, 5, 5));
    contactPane1.add(staticContactLbl);
    contactPane1.add(contactList);
    
    
    JPanel contactPane2 = new JPanel(new GridLayout(1, 2, 5, 5));
    contactPane2.add(deleteContact);
    contactPane2.add(addContact);
    
    
    JPanel contactPane3 = new JPanel(new GridLayout(1, 1, 5, 5));
    contactPane3.add(logout);
    

    JPanel gridy = new JPanel(new GridLayout(3, 1, 5,5));
    gridy.add(contactPane1);
    gridy.add(contactPane2);
    gridy.add(contactPane3);
    this.add(gridy);
    
  }
}
