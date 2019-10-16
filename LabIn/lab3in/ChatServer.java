//Matthew Lin
package lab3in;

import java.io.IOException;

import ocsf.server.*;

public class ChatServer extends AbstractServer {
	public ChatServer() {
		super(123456);
		setTimeout(500);
	}
	
	public void listeningException (Throwable exception) {
		System.out.println("Listening Exception Occurred");
		System.out.println(exception.getMessage());
		exception.printStackTrace();
	}
	
	public void serverStarted() {
		System.out.print("Server Started");
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// TODO Auto-generated method stub
		String message = arg0.toString();
		long id = arg1.getId();
		System.out.println("\nClient " + id + ": " + message);
		try {
			arg1.sendToClient("Hello Client");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	

}
