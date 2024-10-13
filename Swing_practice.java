import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.JPanel;

public class Swing_practice{

    public static void frames(){
        //jFrame=gui window to add components to

        JFrame frame=new JFrame(); // creates a frame that we gonna be working on
        frame.setTitle("noah's first gui"); // sets the title of our frame
        frame.setSize(500,500); // sets the size of our Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allows us to exit our application
        frame.setResizable(true); // resize our frame
        frame.setVisible(true); // make our frame visible

        ImageIcon image=new ImageIcon("pexels-pixabay-33045.jpg");// creates an image icon
        frame.setIconImage(image.getImage());// changing the icon of our frame
        frame.getContentPane().setBackground(new Color(0,0,200)); // change color of the bg
    }

    public static void labels(){
        //JLabel = a gui display are for String of text, an image,or both

        ImageIcon image=new ImageIcon("wallpaperflare.com_wallpaper (16).jpg");
        Border border=BorderFactory.createLineBorder(Color.GREEN);

        JLabel label=new JLabel(); // Creates a label and can set text by passing the text as parameters OR
        label.setText("do you even code");// set the text in the frame
        //label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);// set text LEFT,CENTER,RIGHT of icon
        label.setVerticalTextPosition(JLabel.CENTER); // set text TOP,CENTER,BOTTOM of icon
        label.setForeground(new Color(0X00FF00));// set the font colour of text
        label.setFont(new Font("MV Boli",Font.PLAIN,25));// set the font of text
        label.setIconTextGap(-25);// set the gap of text to image
        label.setBackground(Color.BLACK); // set background color
        label.setOpaque(true); // display background colour by painting every pixel
        label.setBorder(border);
        label.setVerticalAlignment(JLabel.CENTER); // set vertical position of Icon+Text within the label
        label.setHorizontalAlignment(JLabel.CENTER);// set horizontal position of Icon+Text within the label
        //label.setBounds(100,100,250,250);// set the x,y position of the frame and the dimension


        JFrame frame=new JFrame();
        frame.setSize(500,500); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(500,500);
        //frame.setLayout(null); // we wanna set the layout such that the label does not take up the entire frame 
        frame.setVisible(true);
        frame.add(label); // this adds our text to the frame
        frame.pack();// will resize the size of the frame to accomodate all the components that you have 
                     // this works without the setBounds,setSize, setLayout methods
                    // you can only use the pack methods after you've added all the components

    }
    public static void Panels(){
        // JPanel= a gui component that functions as a container to hold other components
        
        JFrame frame=new JFrame();
        JPanel redpanel=new JPanel();
        redpanel.setBounds(0,0,250,250);
        redpanel.setBackground(Color.RED);
        
        JPanel bluepanel=new JPanel();
        bluepanel.setBounds(0,0,250,250);
        bluepanel.setBackground(Color.BLUE);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(750,750);
        frame.setVisible(true);
        frame.add(redpanel);
        frame.add(bluepanel);
    }
    public static void main(String[] args){
        //frames();
       labels();
       //Panels();
       //new Myframe();
    }
}