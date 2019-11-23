package ServerComm;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import Database.CreateAccountData;
import Database.Database;
import Database.LoginData;
import Database.Player;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.actor.Pacman;
import br.ol.pacman.infra.Display;
import br.ol.pacman.infra.Game;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class GameServer extends AbstractServer
{
	private JTextArea log;
	private JLabel status;
	private Database database;
	private int logincount = 0;
	ConnectionToClient arg2 = null;
	ArrayList<ConnectionToClient> clients;
	

	public GameServer()
	{
		super(12345);
		ArrayList<ConnectionToClient> clients = new ArrayList<ConnectionToClient>();
	}

	public void setDatabase(Database database)
	{
		this.database = database;
	}

	public void setLog(JTextArea log)
	{
		this.log = log;
	}

	public JTextArea getLog()
	{
		return log;
	}

	public void setStatus(JLabel status)
	{
		this.status = status;
	}

	public JLabel getStatus()
	{
		return status;
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		//If client sends log in info to server, check username and password
		
		
		if (arg0 instanceof LoginData)
		{
			log.append("Log In info from Client " + arg1.getId() + "\n");

			LoginData loginData = (LoginData)arg0;

			Player user = new Player(loginData.getUsername(), loginData.getPassword());

			database.findUser(user);

			//If can't find user, send error msg to client
			if (!database.getFoundUser())
			{
				log.append("Cannot find Client " + arg1.getId() + "\n");
				try
				{
					arg1.sendToClient("Cannot find log in info. Check username/password or create an account.");
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			//If user found
			else 
			{
				log.append("Log in for Client " + arg1.getId() + " successful\n");
//				if (logincount < 1)
//				{
//					logincount++;
//					try
//					{
//						arg2 = arg1;
//						arg1.wait();
//					} catch (InterruptedException e)
//					{
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				this.send
//				if (logincount == 2)
//				{
//					this.notifyAll();
				logincount++;
				if(logincount == 2) {

					Game game = new PacmanGame();
					this.sendToAllClients(game);
				}
					

//				}
			}
		}

		//If client sends create account info to server, check if username exists
		else if (arg0 instanceof CreateAccountData)
		{
			log.append("Create Account info from Client " + arg1.getId() + "\n");
			CreateAccountData createData = (CreateAccountData)arg0;

			Player user = new Player(createData.getUsername(), createData.getPassword());

			log.append("Create Account for Client " + arg1.getId() + " successful\n");
			user.setID();
			database.addUser(user);

			//If can't find username, send CreateAccountData instance to client
			if (!database.getFoundUser())
			{
				try
				{
					arg1.sendToClient(createData);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			//If username found, send error msg to client
			else 
			{
				log.append("Username already existed\n");
				try
				{
					arg1.sendToClient("Username already existed.");
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	protected void listeningException(Throwable exception) 
	{
		System.out.println("Listening Exception:" + exception);
		exception.printStackTrace();
		System.out.println(exception.getMessage());

		if (this.isListening())
		{
			log.append("Server not Listening\n");
			status.setText("Not Connected");
			status.setForeground(Color.RED);
		}
	}

	protected void serverStarted() 
	{
		log.append("Server Started\n");
		status.setText("Listening");
		status.setForeground(Color.GREEN);
	}

	protected void serverStopped() 
	{
		//Pause for half a second to show message from serverClosed() 
		//in case the method is called first
		try
		{
			Thread.sleep(500);
		} catch (InterruptedException e) {}

		log.append("Server stopped accepting new clients - Press Listen to start accepting new clients\n");
		status.setText("Stopped");
		status.setForeground(Color.RED);
	}

	protected void serverClosed() 
	{
		log.append("Server and all current clients are closed\n");
		status.setText("Closed");
		status.setForeground(Color.RED);
	}

	protected void clientConnected(ConnectionToClient client) 
	{
		//clients.add(client);
		log.append("Client Connected\n");
		
	}
}
