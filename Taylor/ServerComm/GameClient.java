package ServerComm;

import java.io.IOException;

import javax.swing.JFrame;

import ClientComm.CreateAccountControl;
import ClientComm.LoginControl;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.actor.Pacman;
import br.ol.pacman.infra.Display;
import br.ol.pacman.infra.Game;
import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient
{
	private LoginControl lc;
	private CreateAccountControl cc;
	private Pacman pc;
	//Need these 2 controls to "deliver" the response based on server's msg

	public GameClient()
	{
		super("localhost",8300);
	}

	@Override
	public void handleMessageFromServer(Object arg0)
	{
		if (arg0 instanceof Game)
		{
			Game game = (Game) arg0;
			Display view = new Display(game);
			JFrame frame = new JFrame();
			frame.setTitle("Pacman");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(view);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			view.requestFocus();
			view.start();
			
		}
		//Server sending back a CreateAccountData instance = successful creation
		else if (arg0 instanceof CreateAccountControl)
		{
			cc.createSuccess();
		}
		//Server sending back a string
		else if (arg0 instanceof String)
		{
			String msg = (String)arg0;

			if (msg.equals("Cannot find log in info. Check username/password or create an account."))
			{
				lc.displayError(msg);
			}
			else if (msg.equals("Username already existed."))
			{
				cc.displayError(msg);
			}
			else if (msg.equals("Create Pacman"))
			{
				PacmanGame game = new PacmanGame();
				lc.loginSuccess(game);
			}
		}
	}

	public void connectionException (Throwable exception) 
	{
		System.out.println("Connection Exception Occurred");
		System.out.println(exception.getMessage());
		exception.printStackTrace();
	}

	public void setLoginControl(LoginControl lc)
	{
		this.lc = lc;
	}

	public void setCreateControl(CreateAccountControl cc)
	{
		this.cc = cc;
	}
}