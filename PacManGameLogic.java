import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PacManGameLogic extends JPanel implements ActionListener{
    // defining my data fields

    private Dimension d;
    private final Font smallFont=new Font("Arial",Font.BOLD,14);
    private boolean inGame=false;
    private boolean dying=false;

    private final int blockSize=30;
    private final int n_blocks=20; // number of blocks to fill up my frame
    private final int screenSize=n_blocks*30; // Screen size = number of blocks x block size
    private final int maxGhosts=12;
    private final int pacmanSpeed=6;
    
    private int nGhosts=6;
    private int lives,score;
    private int[] dy,dx;
    private int[] ghost_x,ghost_y,ghost_dx,ghost_dy,ghostSpeed;

    private Image heart, ghosts;

    private Image up,down,left,right;

    private int pacman_x,pacman_y,pacmand_x,pacmand_y;
    private int reg_dx, reg_dy;

    private final int validSpeed[] ={1,2,3,4,6,8}; // Speed that is allowed 
    private final int maxSpeed=8;
    private int currentSpeed = 4;
    private short[] screenData;
    private Timer timer;
    private final short levelData[]={
        // the numbers represents a level
        // 16 are the points that pac man can eat
        // 0 are borders or walls

        19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
        17, 16, 16, 16, 16, 24, 16, 16, 16, 16, 24, 24, 24, 24, 24, 24, 24, 24, 16, 20,
        17, 24, 24, 24, 28,  0, 17, 16, 16, 20, 0 , 0 , 0 ,  0,  0, 0 , 0 , 0 , 17, 20,
        21,  0,  0,  0,  0,  0, 17, 16, 16, 16, 18, 18, 18, 18, 18, 18, 22, 0 , 17, 20,
        17, 18, 18, 18, 18, 18, 16, 16, 16, 16, 24, 24, 24, 24, 16, 16, 20, 0 , 17, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 20,  0,  0,  0,  0, 17, 16, 20, 0 , 17, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 20,  0, 19, 26, 26, 16, 16, 16, 18, 16, 20,
        17, 16, 16, 16, 24, 16, 16, 16, 16, 20,  0, 21,  0,  0, 17, 16, 16, 16, 16, 20,
        17, 16, 16, 20,  0, 17, 16, 16, 16, 16, 18, 24, 26, 26, 24, 24, 24, 24, 16, 20,
        17, 24, 24, 28,  0, 25, 24, 24, 16, 16, 20, 0 , 0 , 0 , 0 , 0 , 0 , 0 , 17, 20,
        21,  0,  0,  0,  0,  0,  0,  0, 17, 16, 16, 18, 18, 18, 18, 18, 22, 0 , 17, 20,
        17, 18, 18, 22,  0, 19, 18, 18, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0 , 17, 20,
        17, 16, 16, 20,  0, 17, 16, 16, 16, 16, 16, 24, 16, 16, 16, 16, 20, 0 , 17, 20,
        17, 16, 16, 20,  0, 17, 16, 16, 16, 16, 20, 0 , 17, 16, 16, 16, 20, 0 , 17, 20,
        17, 16, 16, 16, 18, 16, 16, 16, 16, 16, 20, 0 , 17, 16, 16, 16, 20, 0 , 17, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0 , 17, 20, 
        17, 16, 16, 16, 16, 16, 16, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28, 0 , 17, 20, 
        17, 16, 16, 16, 16, 16, 20,  0,  0, 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 17, 20, 
        17, 16, 16, 16, 16, 16, 16, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 16, 20, 
        25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28,
        
    };

    public PacManGameLogic(){

        loadImage();
        initVariable(); // initiase the variables
        addKeyListener(new TAdapter());// the controller function
        setFocusable(true); // for the window
        initGame(); // starts the game
    }// constructor

    private void loadImage(){
        // load the image for the characters
        down = new ImageIcon("down.gif").getImage();
        up = new ImageIcon("up.gif").getImage();
        left = new ImageIcon("left.gif").getImage();
        right = new ImageIcon("right.gif").getImage();
        ghosts = new ImageIcon("ghost.gif").getImage();
        heart = new ImageIcon("heart.png").getImage();
    }

    private void initVariable(){
        // we are initializing our variables

        screenData=new short[n_blocks*n_blocks];
        d = new Dimension(1920,1080); // full dimensions are 1920x1080
        ghost_x= new int[maxGhosts];
        ghost_dx= new int[maxGhosts];
        ghost_y= new int[maxGhosts];
        ghost_dy= new int[maxGhosts];
        ghostSpeed= new int[maxGhosts];
        dx= new int[4];
        dy=new int[4];

        // the timer is responsible for the animation since it determines how often the images are re-drawn
        // game is re-drawn every 40s, the bigger the number the slower the images are re-drawn
        // again the smaller the number the faster the images are re-drawn
        timer = new Timer(40,this);//
        timer.start(); // start the timer
    }
    private void playGame(Graphics2D g2d){
        // calls other functions or methods to display the graphics
        if(dying){
            death();
        }else{
            movePacMan();
            drawPacMan(g2d);
            moveGhosts(g2d);
            chechMaze();
        }

    }
    public void showIntroScreen(Graphics2D g2d){
		// display the following info just before playing the game
		
        String start="press SPACE to start";
        g2d.setColor(Color.YELLOW); // set the text colour to be yellow
        g2d.drawString(start,220, 300);// defining the position of the text
    }
        
    public void drawScore(Graphics2D g2d){
        g2d.setFont(smallFont);
        g2d.setColor(new Color(5,181,79));

        String text="SCORE: "+score;
        g2d.drawString(text,screenSize/2+96,screenSize+16);// determining the position to place the score

        for(int i=0;i<lives;i++){
            // check how many lives are left thus display on the screen
            g2d.drawImage(heart,(i*28)+8,screenSize+1,this);
        }
    }
    public void chechMaze(){
        // check if there are any points for pac-man to eat
        int i=0;
        boolean finished=true;
        while(i<n_blocks*n_blocks && finished){
            if((screenData[i])!=0){
                finished=false;
            }
            i++;
        }
        // if all points are finished, we move to the next level
        if(finished){
            score+=50;
            // the ghosts and speed are increased by 1
            if(nGhosts<maxGhosts){
                nGhosts++;
            }
            if(currentSpeed<maxSpeed){
                currentSpeed++;
            }
            initLevel();
        }
    }
    private void death(){
        // if pac-man dies one life is deducted
        lives--;
        if(lives==0){
            inGame=false;// game continues until pac-man has 0 lives
        }
        continueLevel(); // restart the game
    }
    public void moveGhosts(Graphics2D g2d){
        // move ghosts
        int pos;
        int count;
        for(int i=0;i<nGhosts;i++){
            if(ghost_x[i]%blockSize==0 && ghost_y[i]%blockSize==0){
                pos=ghost_x[i]/blockSize+n_blocks* (int) (ghost_y[i]/blockSize);

                count=0;
                // now we use the border information(1,2,4,8) on how the ghosts should move
                if((screenData[pos]&1)==0 && ghost_dx[i]!=1){
                    dx[count]=-1;
                    dy[count]=0;
                    count++;
                }
                if((screenData[pos]&2)==0 && ghost_dy[i]!=1){
                    dx[count]=0;
                    dy[count]=-1;
                    count++;
                }
                if((screenData[pos]& 4)==0 && ghost_dx[i]!=-1){
                    dx[count]=1;
                    dy[count]=0;
                    count++;
                }
                if((screenData[pos]& 8 )==0 && ghost_dx[i]!=-1){
                    dx[count]=0;
                    dy[count]=1;
                    count++;
                }
                if(count==0){
                    if((screenData[pos]&15)==15){
                        ghost_dy[i]=0;
                        ghost_dx[i]=0;
                    }else{
                        ghost_dy[i]=-ghost_dy[i];// determines where the ghost is located(which square it is positioned in)
                        ghost_dx[i]=-ghost_dx[i];
                    }
                }else{
                    count=(int) (Math.random()*count);
                    if(count>3){
                        count=3;
                    }
                    ghost_dx[i]=dx[count];
                    ghost_dy[i]=dy[count];
                }
            }
            ghost_x[i]=ghost_x[i]+(ghost_dx[i]*ghostSpeed[i]);
            ghost_y[i]=ghost_y[i]+(ghost_dy[i]*ghostSpeed[i]);
            drawGhost(g2d,ghost_x[i]+1,ghost_y[i]+1);
            
            // if pac-man touches the ghosts he loses a life
            if(pacman_x>(ghost_x[i]-12) && pacman_x<(ghost_x[i]+12)
            && pacman_y>(ghost_y[i]-12) && pacman_y<(ghost_y[i]+12)
            && inGame){
                dying=true;
            }
        }
    }
    public void drawGhost(Graphics2D g2d,int x,int y){
        g2d.drawImage(ghosts,x,y,this);
    }
    public void movePacMan(){
        // this method allows us to move pacman
        int pos;
        short ch;
        if(pacman_x%blockSize==0 && pacman_y%blockSize==0){
            pos=pacman_x/blockSize+n_blocks*(int) (pacman_y/blockSize);
            ch=screenData[pos];
            // 16 is the point that pac-man can eat thus...

            if((ch&16)!=0){
                // if pacman is on a field he can eat then he's score increases by 1
                screenData[pos]=(short) (ch&15);
                score++;
            }
            // pac-man is controlled by reg_dx & reg_dy
            if(reg_dx!=0 || reg_dy!=0){
                if(!((reg_dx==-1 && reg_dy==0 && (ch & 1)!=0)
                || (reg_dx==1 && reg_dy==0 && (ch & 4)!=0)
                || (reg_dx==0 && reg_dy==-1 && (ch & 2)!=0)
                || (reg_dx==0 && reg_dy==1 && (ch & 8)!=0))){
                    pacmand_x=reg_dx;
                    pacmand_y=reg_dy;
                
                }
            }
            // check when pac-man is stand still
            if( (pacmand_x==-1 && pacman_y==0 && (ch&1)!=0)
            || (pacmand_x==1 && pacmand_y==0 && (ch & 4)!=0)
            || (pacmand_x==0 && pacmand_y==-1 && (ch & 2)!=0)
            || (pacmand_x==0 && pacmand_y==1 && (ch & 8)!=0)
            ){
                pacmand_x=0;
                pacmand_y=0;
            }
        }
        // we adjast the speed accordingly
        pacman_x=pacman_x+pacmanSpeed*pacmand_x;
        pacman_y=pacman_y+pacmanSpeed*pacmand_y;
    }
    public void drawPacMan(Graphics2D g2d){
        // draws pac-man according to keys being pressed
        if(reg_dx==-1){
            g2d.drawImage(left,pacman_x+1,pacman_y+1,this);
        }else if(reg_dx==1){
            g2d.drawImage(right,pacman_x+1,pacman_y+1,this);
        }else if(reg_dy==-1){
            g2d.drawImage(up,pacman_x+1,pacman_y+1,this);
        }else{
            g2d.drawImage(down,pacman_x+1,pacman_y+1,this);
        }
    }
    public void drawMaze(Graphics2D g2d){
        // we wanna draw the maize for the game
        // and will be drawn using the paint method
        short i=0;
        int x,y;

        for(y=0;y<screenSize;y+=blockSize){// this will be used for the x,y axis of the array
            for(x=0;x<screenSize;x+=blockSize){
                g2d.setColor(new Color(0,72,251)); // blue colour
                g2d.setStroke(new BasicStroke(5));// thickness of the bourder

                // you can uncomment the following code if you want the walls to be painted blue.
                /*if((levelData[i]==0)){
                    // paint our walls to blue
                    g2d.fillRect(x,y,blockSize,blockSize);
                }*/
                if((screenData[i]&1)!=0){
                    // the left border is drawn
                    g2d.drawLine(x, y, x, y+blockSize-1);
                }if((screenData[i]&2)!=0){
                    // the top border is drawn
                    g2d.drawLine(x, y, x+blockSize-1, y);
                }if((screenData[i]&4)!=0){
                    // the right border is drawn
                    g2d.drawLine( x+blockSize-1, y, x+blockSize-1,y+blockSize-1);
                }if((screenData[i]&8)!=0){
                    // the bottom border is drawn
                    g2d.drawLine( x, y+blockSize-1, x+blockSize-1,y+blockSize-1);
                }if((screenData[i]&16)!=0){
                    // represents the white dots
                    g2d.setColor(new Color(255,255,255));
                    g2d.fillOval(x+10, y+10, 6, 6);
                }
                i++;

            }
        }

    }
    private void initGame(){
        // starting values
        lives=3;
        score=0;
        initLevel();// the level is initialized
        nGhosts=6;// number of ghosts
        currentSpeed=4;
    }
    private void initLevel(){
        // initialize the level
        for(int i = 0; i < n_blocks*n_blocks; i++) {
            screenData[i]=levelData[i];  
        }
        continueLevel();
    }

    private void continueLevel(){
        // defines the position of the ghosts
        int dx=1;
        int random;
        for(int i=0;i<nGhosts;i++){
            ghost_y[i]=4*blockSize; // starting position for the ghosts
            ghost_x[i]=4*blockSize;
            ghost_dy[i]=0;
            ghost_dx[i]=dx;
            dx=-dx;

            random=(int) (Math.random())*(currentSpeed+1);

            if(random>currentSpeed){
                random=currentSpeed;
            }
            ghostSpeed[i]=validSpeed[random];// the speed may only have a value available in validSpeed
        }
        // define the start position of pacman
        // can be changed to your own needs
        pacman_x=7*blockSize; // starting position for pac man
        pacman_y=11*blockSize;
        // reseting direction
        pacmand_x=0;
        pacmand_y=0;
        // reseting direction controls
        reg_dx=0;
        reg_dy=0;
        dying=false;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
		
        Graphics2D g2d=(Graphics2D) g;
		
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, d.width,d.height);

        drawMaze((Graphics2D)g2d);
        drawScore(g2d);

        if(inGame){
            playGame(g2d);
        }else{
            showIntroScreen(g2d);
        }
        Toolkit.getDefaultToolkit().sync();
		g2d.dispose(); // causes our jFrame window to be cleaned up and destroyed
    }

    class TAdapter extends KeyAdapter{
		// the controls
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if (inGame){
                if(key==KeyEvent.VK_LEFT){
                    reg_dx=-1;
                    reg_dy=0;
                }else if(key==KeyEvent.VK_RIGHT){
                    reg_dx=1;
                    reg_dy=0;
                }else if(key==KeyEvent.VK_UP){
                    reg_dx=0;
                    reg_dy=-1;
                }else if(key==KeyEvent.VK_DOWN){
                    reg_dx=0;
                    reg_dy=1;
                }else if(key==KeyEvent.VK_ESCAPE && timer.isRunning()){
                    inGame=false;
                }
            }else{
                // with the space key the game has started
                if(key==KeyEvent.VK_SPACE){
                    inGame=true;
                    initGame();
                }
            }
        }
        
    }
    @Override
    public void actionPerformed(ActionEvent e){
		repaint();
            
    }
}