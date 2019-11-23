package lab7out;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ContactPanel extends JPanel
{
  private JTextArea contactList;
  
  public ContactPanel()
  {    

			// Client Data Text Area
			JLabel data = new JLabel("Waiting on Player 2");
			this.add(data);

			
			setSize(800, 400);
			setVisible(true);
    
  }
}
