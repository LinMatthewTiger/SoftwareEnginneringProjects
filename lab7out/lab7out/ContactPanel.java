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
	        JPanel whole = new JPanel(new GridLayout(2, 1, 0, 0));
	        JPanel north = new JPanel(new GridLayout(2, 1, 0, 0));
			// Client Data Text Area
			JLabel data = new JLabel("Contacts");
			data.setHorizontalAlignment(JLabel.CENTER);
			contactList = new JTextArea(7,7);
			north.add(data, BorderLayout.NORTH);
			north.add(new JScrollPane(contactList), BorderLayout.NORTH);
	

			// Make buttons
			JButton Listen = new JButton("Delete Contact");
			JButton Close = new JButton("Add Contact");
			JButton Stop = new JButton("Log Out");

			// Make Jpanel named south
			JPanel south = new JPanel(new FlowLayout());

			// Add and display buttons
			south.add(Listen);
			south.add(Close);
			south.add(Stop);

			whole.add(north, BorderLayout.CENTER);
			whole.add(south, BorderLayout.CENTER);
			
			this.add(whole,BorderLayout.CENTER);
			
			setSize(800, 400);
			setVisible(true);
    
  }
}
