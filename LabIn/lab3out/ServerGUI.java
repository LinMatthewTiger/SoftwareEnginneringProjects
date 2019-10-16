// Matthew Lin
// Lab 1 out
package lab3out;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ServerGUI extends JFrame {
	// Data Fields go here
	private JLabel status; // Initialized to “Not Connected”
	private String[] labels = { "Port #", "Timeout" };
	private JTextField[] textFields = new JTextField[labels.length];
	private JTextArea log;
	private ChatServer server;

	// Methods go here
	public ServerGUI(String title) {
		
		server = new ChatServer();
		
		// Inputs
		int i = 0;
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Make a label and color it Red
		status = new JLabel("Not Connected", JLabel.CENTER);
		status.setForeground(Color.RED);

		// Make a Jpanel named north
		JPanel north = new JPanel(new FlowLayout());
		north.add(new JLabel("Status: "));
		north.add(status);

		// Create Inputs
		JPanel Inputs = new JPanel(new FlowLayout());
		JPanel center = new JPanel(new GridLayout(labels.length, 1, 10, 10));

		for (i = 0; i < labels.length; i++) {

			JLabel j2 = new JLabel(labels[i]);
			JTextField jtf = new JTextField(10);
			
			textFields[i] = jtf;
			

			center.add(j2);
			center.add(jtf);

		}
		
		//New Panel for data
		JPanel center2 = new JPanel(new GridLayout(1, 1, -15, -15));

		// Client Data Text Area
		JLabel data = new JLabel("Server Log Below");
		data.setHorizontalAlignment(JLabel.CENTER);
		log = new JTextArea(10, 30);
		center2.add(data);
		center2.add(new JScrollPane(log));
		
		//Add panels 
		Inputs.add(center);
		Inputs.add(center2);

		// Make buttons
		JButton Listen = new JButton("Listen");
		JButton Close = new JButton("Close");
		JButton Stop = new JButton("Stop");
		JButton Quit = new JButton("Quit");

		// Make Jpanel named south
		JPanel south = new JPanel(new FlowLayout());

		// Add and display buttons
		south.add(Listen);
		south.add(Close);
		south.add(Stop);
		south.add(Quit);
		
		//Setters and getters for ChatServer manipulation
		server.setLog(log);
		server.setStatus(status);

		// Event handlers for Buttons
		Listen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Condition if Port and timeout fields are empty
				if(textFields[0].getText().equals("") || textFields[1].getText().equals("")) {
					log.append("Port Number/timeout not entered before pressing Listen\n");
					System.out.println("Port Number/timeout not entered before pressing Listen\\n");
				}
				else {
					try {
						server.setPort(Integer.parseInt(textFields[0].getText())); //Get Values
						server.setTimeout(Integer.parseInt(textFields[1].getText()));
						server.listen(); // Server starts listening
					}
					catch(Exception e1) {
						System.out.println(e1);
					}
					
				}
				
				
				
			}
		});

		Close.addActionListener(new ActionListener() {
			//Condition if the server is not listening
			public void actionPerformed(ActionEvent e) {
				if(!server.isListening()) {
					log.append("Server not currently started\n");
					System.out.println("Server not currently started\\n");
					
				}
				else {
					try {
						server.close(); // Close server
					}
					catch(Exception e1) {
						System.out.println(e1);
					}
				}
				
			}
		});

		Stop.addActionListener(new ActionListener() {
			//Condition if the server is not listening
			public void actionPerformed(ActionEvent e) {
				if(!server.isListening()) {
					log.append("Server not currently started\n");
					System.out.println("Server not currently started\n");
				}
				else {
					try {
						server.stopListening(); //Stop server
					}
					catch(Exception e1) {
						System.out.println(e1);
					}
				}
			}
		});
		
		Quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		//Display
		this.add(south, BorderLayout.SOUTH);
		this.add(Inputs, BorderLayout.CENTER);
		this.add(north, BorderLayout.NORTH);
		setSize(800, 400);
		setVisible(true);

	}

	public static void main(String[] args) {
		new ServerGUI(args[0]); // args[0] represents the title of the GUI
	}
}
