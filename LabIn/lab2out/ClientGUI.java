// Matthew Lin
// Lab 1 out

package lab2out;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ClientGUI extends JFrame {
	private JLabel status;
	private JButton connect;
	private JButton submit;
	private JButton stop;
	private String[] labels = { "Client ID", "Server URL", "Server Port" };
	private JTextField[] textFields = new JTextField[labels.length];
	private JTextArea clientArea;
	private JTextArea serverArea;

	public ClientGUI(String title) {
		int i = 0;

		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ADD YOUR CODE HERE TO CREATE THE STATUS JLABEL AND THE JBUTTONS

		// Make a label and color it Red
		status = new JLabel("Not Connected", JLabel.CENTER);
		status.setForeground(Color.RED);

		// Make a Jpanel named north
		JPanel north = new JPanel(new FlowLayout());
		north.add(new JLabel("Status: "));
		north.add(status);

		// Inputs
		JPanel Inputs = new JPanel(new FlowLayout());
		JPanel center = new JPanel(new GridLayout(labels.length, 1, 10, 10));

		for (i = 0; i < labels.length; i++) {

			JLabel j2 = new JLabel(labels[i]);
			JTextField jtf = new JTextField(10);

			if (i == 0) {
				jtf.setEditable(false);
			}

			center.add(j2);
			center.add(jtf);

		}

		JPanel center2 = new JPanel(new GridLayout(4, 1, -15, -15));

		// Client Data Text Area
		JLabel data = new JLabel("Enter Client Data Below");
		data.setHorizontalAlignment(JLabel.CENTER);
		clientArea = new JTextArea(5, 20);
		center2.add(data);
		center2.add(new JScrollPane(clientArea));

		// Server Data Text Area
		data = new JLabel("Recieve Server Data");
		data.setHorizontalAlignment(JLabel.CENTER);
		serverArea = new JTextArea(5, 20);
		serverArea.setEditable(false);
		center2.add(data);
		center2.add(new JScrollPane(serverArea));

		// Add inputs to panel 
		Inputs.add(center);
		Inputs.add(center2);


		// Make buttons
		connect = new JButton("connect");
		submit = new JButton("submit");
		stop = new JButton("stop");

		// Make Jpanel named south
		JPanel south = new JPanel(new FlowLayout());

		// Add and display buttons
		south.add(connect);
		south.add(submit);
		south.add(stop);


		// Event handlers for Buttons
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Connect Button Pressed");
				System.out.println("Connect Button Pressed");
			}
		});

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Client Data: " + clientArea.getText());
				System.out.println( "Client Data: " + clientArea.getText());
			}
		});

		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Stop Button Pressed");
				System.out.println("Stop Button Pressed");
			}
		});

		// Display View
		this.add(Inputs, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		this.add(north, BorderLayout.NORTH);
		setSize(400, 500);
		setVisible(true);

	}

	public static void main(String[] args) {
		new ClientGUI(args[0]); // args[0] represents the title of the GUI
	}
}
