package ClientComm;

import java.awt.*;
import javax.swing.*;

import ClientInterface.LoginPanel;
import Database.LoginData;
import Database.Player;
import ServerComm.GameClient;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.actor.Pacman;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class LoginControl implements ActionListener
{
	private JPanel container;
	private GameClient client;
	private Pacman pacman;

	public LoginControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}

	//Handle button clicks
	public void actionPerformed(ActionEvent ae)
	{
		//Get the name of the button clicked
		String command = ae.getActionCommand();

		//The Cancel button takes the user back to the initial panel
		if (command == "Cancel")
		{
			//Reset the log in panel
			LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
			loginPanel.clearContents();
			
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}

		//The Submit button submits the create information to the server
		else if (command == "Submit")
		{
			//Get the username and password the user entered
			LoginPanel loginPanel = (LoginPanel)container.getComponent(1);	//2nd panel of the CardLayout container
			LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());

			//Check the validity of the info locally
			if (data.getUsername().equals("") || data.getPassword().equals(""))
			{
				displayError("You must enter a username and password.");
				return;
			}

			//Submit the login info to the server
			try
			{
				client.sendToServer(data);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	//After the login is successful, set the User object and display the contacts screen
	//Invoked by the ChatClient
	public void loginSuccess(PacmanGame game)
	{
		//Reset the log in panel 
		LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
		loginPanel.clearContents();
		
		try
		{
			pacman = new Pacman(game);
			client.sendToServer(pacman);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void displayError(String error)
	{
		LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
		loginPanel.setError(error);
	}
}
