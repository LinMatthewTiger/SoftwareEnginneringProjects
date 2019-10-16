//Matthew Lin
package lab3in;


public class TestChatServer {
	
	private ChatServer server;
	
	public TestChatServer(int port, int timeout) {
		server = new ChatServer();
		server.setPort(port);
		server.setTimeout(timeout);
		try {
			server.listen();
		}
		catch(Exception e) {
			System.out.println("Error");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = Integer.parseInt(args[0]);
		int timeout = Integer.parseInt(args[1]);
		
		new TestChatServer(port, timeout);
	}

}
