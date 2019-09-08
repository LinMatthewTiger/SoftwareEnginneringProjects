//Matthew Lin
package lab2out;

import ocsf.server.*;

import java.awt.Color;

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
	
	//Something
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// TODO Auto-generated method stub
		System.out.println("Client Message sent to Server\n");
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
		System.out.print("Server Started\n");
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
		
	}

	//Something
	public void clientConnected(ConnectionToClient client) {
		log.append("Client connected\n");
	}


	
	
	
	
	
	
	

}
