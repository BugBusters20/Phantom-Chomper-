import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPacManFrame extends JFrame implements ActionListener{

    private Image backGroundImage; // background Image
    private JButton button;// play button
    private JButton button2;// exit button
    private JButton button3;// help button
    private ImageIcon icon;

    public MyPacManFrame(){
        try{
            backGroundImage=ImageIO.read(new File("a-retro-revival-pacman-in-action-8y2oy0gy4ip8dn6p.jpg"));

        }catch(IOException e){
            e.printStackTrace();

        }
        JPanel panel=new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                if(backGroundImage!=null){
                    g.drawImage(backGroundImage,0,0,getWidth(),getHeight(),this);
                }
            }
        };
    //Set JFrame properties

    setLayout(null);
    setTitle("PacMan");
    setSize(650,670);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Center the frame

    int buttonWidth = 100;
    int buttonHeight = 30;

    //  create Buttons for our user interface.

    button = new JButton("PLAY");
    button2 = new JButton("Exit");
    button3=new JButton("Help");

    button.setFont(new Font("comic sans",Font.BOLD,24));
    button.setBackground(Color.BLUE);
    button.setBounds(200,100,150,50);
    button.setFocusable(false);
    button.addActionListener(this);
    button.setIcon(icon);
    
    button2.setFont(new Font("comic sans",Font.BOLD,24));
    button2.setBackground(Color.BLUE);
    button2.setBounds(200,100,150,50);
    button2.setFocusable(false);
    button2.addActionListener(this);
    button2.setIcon(icon);

    button3.setFont(new Font("comic sans",Font.BOLD,24));
    button3.setBackground(Color.BLUE);
    button3.setBounds(200,100,150,50);
    button3.setFocusable(false);
    button3.addActionListener(this);
    button3.setIcon(icon);

    //ActionListener exitListener=e-> System.exit(0);// exit application
    // defining the button sizes

    // get the frame dimension to center the buttons
    int frameWidth = getWidth();
    int frameHeight = getHeight();

    int xPosition = (frameWidth - buttonWidth) / 2;  // Center buttons horizontally
    int yPosition = (frameHeight - (3 * buttonHeight + 40)) / 2;
    
    button.setBounds(xPosition, yPosition, buttonWidth, buttonHeight);
    button3.setBounds(xPosition, yPosition + buttonHeight + 10, buttonWidth, buttonHeight); // 10px gap
    button2.setBounds(xPosition, yPosition + 2 * (buttonHeight + 10), buttonWidth, buttonHeight);

    button2.addActionListener(e -> System.exit(0));
    // Add buttons to the frame
    //frame.add(clickButton);
    //frame.add(exitButton);

    // ActionListener with massage for the help button
    button3.addActionListener(e -> {
        String helpMessage = "How to Play Pac-Man:\n\n"
                           + "1. Move Pac-Man using the arrow keys.\n"
                           + "2. Eat all the dots in the maze to advance to the next level.\n"
                           + "3. Avoid the ghosts! If they touch you, you'll lose a life.\n"
                           + "4. Eat power pellets to turn the ghosts blue and make them edible for a short time.\n"
                           + "5. Clear the maze and earn points. Good luck!";
        JOptionPane.showMessageDialog(this, helpMessage, "Pac-Man Help", JOptionPane.INFORMATION_MESSAGE);
    });

    // Set the content pane to the custom JPanel
    setContentPane(panel);

    // adding my buttons
    panel.add(button);
    panel.add(button2);
    panel.add(button3);
    //setLayout(new FlowLayout());
    setVisible(true);
    
    //button2.addActionListener(new ExitButtonListener());
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button){
            pacman game=new pacman();
        
            game.setTitle("Pac-Man Game");// set the tittle for the game
            game.setSize(650,670); // setting the window size, full size is given by 1920x1080
            game.setResizable(true); // resize our frame
            game.setDefaultCloseOperation(EXIT_ON_CLOSE);// stops the program when closing the window
            game.setLocationRelativeTo(null); // setting the position of the window
            game.setVisible(true);
        }
    }
}