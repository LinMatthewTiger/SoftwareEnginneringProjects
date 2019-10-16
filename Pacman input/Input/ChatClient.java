//Matthew Lin
package Input;

import java.awt.Color;

import javax.swing.*;
import ocsf.client.*;

public class ChatClient extends AbstractClient {
	// Private values for Classes/ClientGUI fields
	private JLabel status;
	private JTextArea serverMsg;
	private JTextField clientID;
	private String clientid = "";
	
	//Constructor
	public ChatClient() {
		super("localhost", 8300);
	}
	
	//Setters
	public void setStatus(JLabel status) {
		this.status = status;
		
	}
	
	public void setServerMsg(JTextArea serverMsg) {
		this.serverMsg = serverMsg;
	}	
	
	public void setClientID(JTextField clientID) {
		this.clientID = clientID;
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0.toString().contains("Username:")) {  // Find Key value Pair
			clientid = arg0.toString().substring(9); // Save the username
			clientID.setText(clientid);				 // Display username 
			
		}
		
		
		serverMsg.append("Server: " + arg0 + "\n"); //Display message sent by server
		
		
	}
	
	public void connectionEstablished() {		//Connection established
		status.setText("Connected"); 
		status.setForeground(Color.GREEN);
	}
	
	public void connectionClosed() {  			//Connection closed
		status.setText("Not Connected");
		status.setForeground(Color.RED);
		clientID.setText("");
	}
	
}
