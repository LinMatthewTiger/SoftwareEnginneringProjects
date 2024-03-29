package ServerInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ClientInterface.ClientGUI;
import Database.Database;
import ServerComm.GameServer;

public class ServerGUI extends JFrame
{
	private JLabel status; //Initialized to “Not Connected”
	private JLabel logLabel;

	private String[] labels = {"Port #", "Timeout"};

	private JTextField[] textFields = new JTextField[labels.length];

	private JTextArea log;

	private JScrollPane scroll;

	private JButton listen;
	private JButton close;
	private JButton stop;
	private JButton quit;

	private GameServer server;

	public ServerGUI()
	{
		server = new GameServer();
		
		//Declare Database object
		try
		{
			Database database = new Database();
			server.setDatabase(database);
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.setTitle("Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//---- Panel for status ----
		//Create a status containing “Status: Not Connected”
		JLabel statusLb = new JLabel("Status: ");

		status = new JLabel("Not Connected");
		status.setForeground(Color.RED);
		server.setStatus(status);

		//Store the JLabel in a JPanel named “north” that implements FlowLayout as the Layout Manager
		JPanel north = new JPanel(new FlowLayout());
		north.add(statusLb);
		north.add(status);


		//---- Panel for info ----
		JPanel info = new JPanel(new FlowLayout());

		//--- Panels for each info
		int numLabels = labels.length;
		JPanel infoGrid = new JPanel(new GridLayout(numLabels,2,5,5));

		for (int i = 0; i < numLabels; i++)
		{
			//Label
			JLabel label = new JLabel(labels[i], SwingConstants.RIGHT);

			//Text field
			textFields[i] = new JTextField();
			textFields[i].setPreferredSize(new Dimension(70,20));

			infoGrid.add(label);
			infoGrid.add(textFields[i]);
		}

		//Add the grid to the info panel
		info.add(infoGrid);


		//---- Panel for server log ----
		JPanel serverLog = new JPanel(new FlowLayout());

		//A nested panel for all components
		JPanel logJp = new JPanel();
		logJp.setLayout(new BoxLayout(logJp, BoxLayout.Y_AXIS));

		//Put each label in a panel for alignment
		JPanel logPanel = new JPanel(new FlowLayout());
		logLabel = new JLabel("Server Log Below", SwingConstants.CENTER);
		logPanel.add(logLabel);
		logJp.add(logPanel);

		logJp.add(Box.createVerticalStrut(5));		//Extra space btwn components

		log = new JTextArea();
		log.setEditable(false);
		server.setLog(log);
		scroll = new JScrollPane(log);
		scroll.setPreferredSize(new Dimension(400,200));
		logJp.add(scroll);

		logJp.add(Box.createVerticalStrut(10));

		//Add the panel into serverLog panel
		serverLog.add(logJp);


		//---- Panel for server info ----
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

		center.add(info);
		center.add(Box.createVerticalStrut(40));		//Add a space between the panel
		center.add(serverLog);


		//---- Panel for buttons ----
		//Create 3 JButtons
		listen = new JButton("Listen");
		close = new JButton("Close");
		stop = new JButton("Stop");
		quit = new JButton("Quit");

		//Store the JButtons in a JPanel named “south” that implements FlowLayout as the Layout Manager
		JPanel south = new JPanel(new FlowLayout());
		south.add(listen);
		south.add(close);
		south.add(stop);
		south.add(quit);


		//---- Panel to contain north, center, and south components ----
		JPanel all_panel = new JPanel();
		all_panel.setLayout(new BoxLayout(all_panel, BoxLayout.Y_AXIS));
		all_panel.add(north);
		all_panel.add(center);
		all_panel.add(south);

		//---- Main panel to be added to frame ----
		JPanel main = new JPanel();
		main.add(all_panel);

		this.add(main,BorderLayout.CENTER);
		
		//Display frame
		setSize(450,450);
		setVisible(true);


		//---- Buttons functionality ----
		listen.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				if (textFields[0].getText().isEmpty() || textFields[1].getText().isEmpty())
					log.append("Port Number/timeout not entered before pressing Listen\n");
				else
				{
					//Get and set the input port # and timeout
					int port = Integer.parseInt(textFields[0].getText());
					int timeout = Integer.parseInt(textFields[1].getText());
					server.setPort(port);
					server.setTimeout(timeout);

					try
					{
						server.listen();
					} catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});

		close.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				if (!server.isListening())
					log.append("Server not currently started\n");
				else
					try
				{
						server.close();
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		});

		stop.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				if (!server.isListening())
					log.append("Server not currently started\n");
				else
				{
					server.stopListening();
				}
			}
		});

		quit.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args)
  {
    new ServerGUI();
  }
}