import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;



public class GamePanel extends JPanel implements ActionListener {
    
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;                                                // How big do we want the objects(each item) in this game             
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT ) / UNIT_SIZE;      // We want to calculate how many objects we can actually fit on the screen 
    private static final int DELAY = 75;                                                    // Delay for our timer. The higher the number the delay, the slower the game is                                                  

    private final int x[] = new int[GAME_UNITS];                                            // These arrays are going to hold all of the coordinates for all the body parts of the snake 
    private final int y[] = new int[GAME_UNITS];                                            // Set the size to whatever the game units is. Since the snake isn't going to be bigger than the game itself

    private int bodyParts = 6;                                                              // Begin game with 6 body parts on the snake
    private int applesEaten;
    private int appleX;                                                                     // X-coordinate of where the Apple is located, it's going to appear randomly each time 
    private int appleY;
    private char direction = 'R';                                                           // Snake's begin going right when start the game
    private boolean running = false;                                                        // Snake doesn't run before starting the game

    Timer timer;
    Random random;

    
    public GamePanel() {     
        random = new Random();
        MyKeyAdapter myKey = new MyKeyAdapter();
        Dimension dimension = new Dimension( SCREEN_WIDTH, SCREEN_HEIGHT );
        this.setPreferredSize(dimension);                                                   // Set the size for this game panel 
        this.setBackground(Color.black);                                
        this.setFocusable(true);
        this.addKeyListener(myKey);
        startGame();

    }
    
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer( DELAY, this );                                                   // Dictate how fast the game is running             
        timer.start();                       
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw( Graphics g ) {                                
        // Draw a grid for better visualize, all of the items will equal to a square of grid
        for( int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++ ) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);                     // This will draw all horizontal line 
            g.drawLine(0,i*UNIT_SIZE , SCREEN_WIDTH, i*UNIT_SIZE);                      // This will draw all veritical line on the main window.
        }
        // Draw an Apple
        g.setColor(Color.red);
        g.drawLine(FRAMEBITS, ERROR, ALLBITS, ABORT);// Draw a circle 
    }

    public void newApple() {                                              // We want to generate coordinates of a new apple. Any time we begin the game/score a point

        appleX = random.nextInt( (int) SCREEN_WIDTH/UNIT_SIZE) * UNIT_SIZE;        // This is the x-coordinate of Apple
        appleY = random.nextInt( (int) SCREEN_HEIGHT/UNIT_SIZE) * UNIT_SIZE;       // Multiply with UNIT_SIZE b/c we want this apple to be placed evenly within a square grid
    }


    public void move() {

    }

    public void checkApple() {

    }

    public void checkCollisions() { 

    }

    public void gameOver( Graphics g ) {

    }
    
    
    @Override
    public void actionPerformed( ActionEvent e ) {

    }

    public class MyKeyAdapter extends KeyAdapter{                   // Inner class
        @Override
        public void keyPressed( KeyEvent e ) {

        }
    }
}
