
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Random;

/**
 *
 * @author tewan2657
 */


//170  , 650
public class Game extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    
    //Title of the window
    String title = "My Game";

    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;

     // YOUR GAME VARIABLES WOULD GO HERE
    
    
    
    int score = 0;
    int life = 3;

    Random generator = new Random();
    
    //Y position for the Brick 
    // int y = 300;
     
     //(MAX + 1 - MIN) + MIN
    int randX = generator.nextInt(550 + 1  - 170) +  170;
    
    Rectangle[] brick = new Rectangle[5];

    
    //BRICKS
  
       
    
   //PLAYER
    Rectangle player = new Rectangle(395, 585, 50, 10);
    
    //KEYBOARD BUTTONS
    boolean leftPressed;
    boolean rightPressed;
    
   
    
    
    
    // GAME VARIABLES END HERE   

    
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public Game(){
        // creates a windows to show my game
        JFrame frame = new JFrame(title);

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        
        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();
        
        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);
    }
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        
        
        //170 & 650
        //player at 495
        //BORDER
        g.setColor(Color.BLUE);
        g.fillRect(150, 0, 20, 600);
        g.fillRect(650, 0, 20, 600);
        
        
        
        // use a for loop to go through the array of blocks :)
        for(int i = 0; i < brick.length; i++){
            g.fillRect(brick[i].x, brick[i].y, brick[i].width, brick[i].height);
        }
        
        //Barrier Blocks 
       // g.drawRect(randX, brick.y, 50, 50);

        
        //PLAYER
        g.drawRect(player.x, player.y, player.width, player.height);
        
        
        
        
        
        //Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);
        
        //Lives
         g.setColor(Color.RED);
         g.setFont(new Font("Arial", Font.BOLD, 25));
         g.drawString("Lives: " + life, 670, 30);
         
         //Game over
//         g.setColor(Color.RED);
//         g.setFont(new Font("Arial", Font.BOLD, 30));
//         g.drawString("GAME OVER", 320, 350);
//         
        
        
        
        
        // GAME DRAWING ENDS HERE
    }


    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void  preSetup(){
       // Any of your pre setup before the loop starts should go here
       
       brick[0] = new Rectangle(randX, 350, 50, 50); 
       brick[1] = new Rectangle(randX, 350, 50, 50); 
       brick[2] = new Rectangle(randX, 350, 50, 50); 
       brick[3] = new Rectangle(randX, 350, 50, 50); 
       brick[4] = new Rectangle(randX, 350, 50, 50); 
       
    
    }
    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        preSetup();

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
     
//            player.setBounds(player.x , player.y, player.width, player.height);
//            brick.setBounds(brick.x, brick.y, brick.width, brick.height);
//            
            collisions();
            hits();
            
         
            //When left key pressed move to the left 
            if(leftPressed){
                player.x = player.x - 5;
            }
            // When right key pressed move to the right 
            if(rightPressed){
                player.x = player.x + 5;
            } 
            
             for(int i = 0; i < brick.length; i++){
            //brick speed
            brick[i].y += 5;
            //Randomly reset the brick to another location 
            if(brick[i].y> HEIGHT){
                brick[i].y = 0;
                getRandom();
                //updated brick x 
                brick[i].x = randX;
            }
             }
            
          
            
            
            
            
           
            
            // GAME LOGIC ENDS HERE 
            
            
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try {
                if (deltaTime > desiredTime) {
                    //took too much time, don't wait
                    Thread.sleep(1);
                } else {
                    // sleep to make up the extra time
                    Thread.sleep(desiredTime - deltaTime);
                }
            } catch (Exception e) {
            };
        }
    }

    

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {
        // if a mouse button has been pressed down
        @Override
        public void mousePressed(MouseEvent e){
            
        }
        
        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e){
            
        }
        
        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            
        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e){
            
        }
    }
    
    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter{
        // if a key has been pressed down
        @Override
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                leftPressed = true;
            } 
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                rightPressed = true;
            }
            
            
        }
        
        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e){
             if(e.getKeyCode() == KeyEvent.VK_LEFT){
                leftPressed = false;
            } 
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                rightPressed = false;
            }
        }
    }
    
    public void getRandom(){
        randX = generator.nextInt(650 + 1  - 170) +  170;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        Game game = new Game();
                
        // starts the game loop
        game.run();
    }
    
    public void collisions(){
        //player can not exceed Border 1 at 170 
        if(player.x <=170){
            player.x = 170;
        }
        // player can not exceed Border 2 at 650 
        if(player.x + 200 >=WIDTH){
            player.x = player.x - 5;
        }
        
      
        
    }
    
    public void hits(){
     for(int i = 0; i < brick.length; i++){
        if(player.intersects(brick[i])){
            resetPlayer();
          
        }
            
        }
      
 
//        int bodyOfBlock = brick.x + brick.width;
//        if( brick.y+ brick.height >= player.y){
//            System.out.println("player x " + player.x + " brick x " + brick.x);
//            if(player.x < brick.x ){
//                  resetPlayer();
//                  life = life - 1;
//            }
//            if(life == 0){
//               
//            }  
//        }
        

    }
    public void resetPlayer(){
        player.x = 395;
    }
    
    }
    
    
    
    
    
    

