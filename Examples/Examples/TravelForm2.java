package Examples;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TravelForm2 extends JFrame 
{
 
  private String[] names = {"Number of Days of Trip","Amount of Airfare","Amount of car rental","Miles driven:",
  		                      "Parking fees","Taxi fees","Conference Registration","Lodging charges per night"};


  public TravelForm2()
  {
    super("Travel Form");

    

    this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

   

    //Create Jpanel for Title on Form
    JLabel jl = new JLabel("Travel Expense Form",JLabel.LEFT);
    JPanel top = new JPanel();
    top.add(jl);
    JPanel outer = new JPanel(new FlowLayout());

    //Create Grid
    JPanel center = new JPanel(new GridLayout(names.length,1,10,10));
    int i = 0;
    for (i = 0; i < names.length; i++)
    {
      
    
      JLabel j2 = new JLabel(names[i]);
      JTextField jtf = new JTextField("Enter data",10);
      
      center.add(j2);
      center.add(jtf);
     
    }
    
    
    
    JButton start = new JButton("Start");
    JButton stop = new JButton("Stop");
    JPanel bottom = new JPanel();
    bottom.add(start);
    bottom.add(stop);
    
    outer.add(center);
    this.add(top,BorderLayout.NORTH);
    this.add(outer,BorderLayout.CENTER);
    this.add(bottom, BorderLayout.SOUTH);

    this.setSize(400,500);
    //this.pack();
    this.setVisible(true);


    




  }

  
  

  public static void main(String[] args)
  {
    TravelForm2 travelform = new TravelForm2();
  }


 
}

