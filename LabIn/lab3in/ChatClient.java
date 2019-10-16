//Matthew Lin
package lab3in;

import ocsf.client.*;

public class ChatClient extends AbstractClient {
	public ChatClient() {
		super("localhost", 8300);
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		// TODO Auto-generated method stub
		System.out.println("Server Message sent to Client: " + arg0);
	}
	
	public void connectionException(Throwable exception) {
		System.out.println("Connection Exception Occured");
		System.out.println("\n " + exception.getMessage());
		System.out.println("\n " + exception);
		
	}
	
	public void connecitonEstablished() {
		System.out.println("Client Connected");
	}
	
}
