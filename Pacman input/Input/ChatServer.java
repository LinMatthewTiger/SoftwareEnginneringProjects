//Matthew Lin
package Input;

import ocsf.server.*;

import java.awt.Color;
import java.io.IOException;

import javax.swing.*;


public class ChatServer extends AbstractServer{
	
	//Private values for ServerGUI
	private JTextArea log;
	private JLabel status;
	
	//Constructor
	public ChatServer() {
		super(123456);
		setTimeout(500);
		
		
	}
	
	//Setters
	public void setLog(JTextArea log) {
		this.log = log;
	}
	
	public void setStatus(JLabel status) {
		this.status = status;
	}
	
	// Output client id and information sent
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		if(arg0.toString().contains("38")) {  // Find Key value Pair 
			log.append("Client " + arg1.getId() + ": Up Arrow Pressed\n");
			flag = true;
		}
		if(arg0.toString().contains("39")) {  // Find Key value Pair
			log.append("Client " + arg1.getId() + ": Right Arrow Pressed\n");
			flag = true;
		}
		if(arg0.toString().contains("40")) {  // Find Key value Pair
			log.append("Client " + arg1.getId() + ": Down Arrow Pressed\n");
			flag = true;
		}
		if(arg0.toString().contains("37")) {  // Find Key value Pair 
			log.append("Client " + arg1.getId() + ": Left Arrow Pressed\n");
			flag = true;
		}
		
		if(flag) {
			arg0 = "You moved";
		}
		else {
			arg0 = "Enemy Moved";
		}
		
		try {
			arg1.sendToClient(arg0); // Send back to client
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Exception catch when something blows up
	public void listeningException (Throwable exception) {
		System.out.println("Listening Exception Occurred\n");
		System.out.println(exception.getMessage());
		exception.printStackTrace();
		
		log.append("Listening Exception Occurred: "+ exception.getMessage() + "\n");
		
		log.append("Press Listen to Restart Server\n");
		status.setText("Exception Occurred when Listening");
		status.setForeground(Color.RED);
	}
	
	//Invoked by listen()
	public void serverStarted() {
		log.append("Server Started\n");
		status.setText("Listening");
		status.setForeground(Color.GREEN);
	}

	//Invoked by stopped()
	public void serverStopped() {
		log.append("Server Stopped Accepting New Clients - Press Listen to Start\n");
		status.setText("Stopped");
		status.setForeground(Color.RED);
		
	}
	
	//Invoked by stopped() and close()
	public void serverClosed() {
		log.append("Server and all current clients are closed - Press Listen to Restart\n");
		status.setText("Close");
		status.setForeground(Color.RED);
		Thread.currentThread().interrupt();
	}

	//Client connected display after conncection to server
	public void clientConnected(ConnectionToClient client) {
		
		log.append("Client-" + client.getId() + " Connected\n");  //Display to server
		try {
			client.sendToClient("Username:" + "Client" + client.getId() + "\n");  // Send key/value to client
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	
	
	
	
	
	
	

}
