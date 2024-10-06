import javax.swing.JFrame;

public class pacman extends JFrame{
    public pacman(){
        add(new Model());
    }// constructor
    public static void main(String[] args) {
        pacman game=new pacman();

        game.setVisible(true);
        game.setTitle("Pac-Man Game");// set the tittle for the game
        game.setSize(650,670); // setting the window size, full size is given by 1920x1080
        game.setResizable(true); // resize our frame
        game.setDefaultCloseOperation(EXIT_ON_CLOSE);// stops the program when closing the window
        game.setLocationRelativeTo(null); // setting the position of the window
        
    }
}