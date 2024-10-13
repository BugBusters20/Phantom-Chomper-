
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class Myframe extends JFrame implements ActionListener{
    JButton button=new JButton();
    Myframe(){
        ImageIcon icon=new ImageIcon("wallpaperflare-cropped.jpg");
        
        button.setBounds(200,100,250,100);
        button.addActionListener(this);
        /*you can use the following, instead of using the ActionPerformed method
         * below....
         *  butoon.addActionListener(e -> System.out.println("noah"));
         * this does the same thing.
         */
        button.setText("I'm a button"); // set the text within the button
        button.setFocusable(false);// this getride of the box arround the text
        button.setIcon(icon);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500,500);
        this.setVisible(true);

        this.add(button);

    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button){
            System.out.println("noah");
        }

    }
}