//Matthew Lin
package lab7out;

import ocsf.client.*;

public class ChatClient extends AbstractClient {
	private LoginControl lc;
	private CreateAccountControl cac;
	
	public ChatClient() {
		super("localhost", 8300);
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		// TODO Auto-generated method stub
		System.out.println("Server Message sent to Client: " + arg0);
		if(arg0.equals("Authenticated")) {
			lc.loginSuccess();
		}
		else if(arg0.equals("Unauthenticated")) {
			lc.displayError("The Username and Password are incorrect.");
		}
		else if(arg0.equals("Taken")) {
			cac.taken();
		}
	}
	
	public void connectionException(Throwable exception) {
		System.out.println("Connection Exception Occured");
		System.out.println("\n " + exception.getMessage());
		System.out.println("\n " + exception);
		
	}
	
	public void connecitonEstablished() {
		System.out.println("Client Connected");
	}
	
	public void setLoginControl(LoginControl lc) {
		this.lc = lc;
	}
	
	public void setCreateAccountControl(CreateAccountControl cac) {
		this.cac = cac;
	}
}
