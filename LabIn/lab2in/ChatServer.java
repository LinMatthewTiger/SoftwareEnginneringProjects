//Matthew Lin
package lab2in;

import ocsf.server.*;

public class ChatServer extends AbstractServer {
	public ChatServer() {
		super(123456);
		setTimeout(500);
	}
	
	public void handleMessgeFromClient(Object arg0, ConnectionToClient arg1) {
		System.out.println("Client MEssage sent to Server");
	}
	
	public void listeningException (Throwable exception) {
		System.out.println("Listening Exception Occurred");
		System.out.println(exception.getMessage());
		exception.printStackTrace();
	}
	
	public void serverStarted() {
		System.out.print("Server Started");
	}

	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
