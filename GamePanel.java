import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.Random;



public class GamePanel extends JPanel implements ActionListener {
    
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;                                                // How big do we want the objects(each item) in this game             
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT ) / UNIT_SIZE;      // We want to calculate how many objects we can actually fit on the screen 
    private static final int DELAY = 75;                                                    // Delay for our timer. The higher the number the delay, the slower the game is                                                  

    private final int x[] = new int[GAME_UNITS];                                            // These arrays are going to hold all of the coordinates for all the body parts of the snake 
    private final int y[] = new int[GAME_UNITS];                                            // Set the size to whatever the game units is. Since the snake isn't going to be bigger than the game itself

    private int bodyParts = 3;                                                              // Begin game with 6 body parts on the snake
    private int applesEaten;
    private int appleX;                                                                      // X-coordinate of where the Apple is located, it's going to appear randomly each time 
    private int appleY;
    private char direction = 'R';                                                           // Snake's begin going right when start the game
    private boolean running = false;                                                        // Snake doesn't run before starting the game

    Timer timer;
    Random random;

    public static int currentState;
    public static Scene currentScene;
 
    public static KL keyListener = new KL();
    public static MousListener mouseListener = new MousListener();

    public GamePanel() {     
        random = new Random();
        MyKeyAdapter myKey = new MyKeyAdapter();
        Dimension dimension = new Dimension( SCREEN_WIDTH, SCREEN_HEIGHT );
        this.setPreferredSize(dimension);                                                   // Set the size for this game panel 
        this.setBackground(new Color(45,180,0));                                
        this.setFocusable(true);
        this.addKeyListener(myKey);
        startGame();

        // addKeyListener(GamePanel.keyListener);
        // addMouseListener(GamePanel.mouseListener);
        // addMouseMotionListener(GamePanel.mouseListener);
        // GamePanel.changeState(0);

    }

    // public static void changeState( int newState ) {
    //     GamePanel.currentState = newState;
    //     switch( GamePanel.currentState ) {
    //         case 0:
    //         GamePanel.currentScene = new MenuScene(keyListener, mouseListener);
    //             break;
    //         case 1:
    //         // GamePanel.currentScene = new GamePanel();
    //             break;
    //         default:
    //             System.out.println("Unknown scene.");
    //             GamePanel.currentScene = null;
    //             break;
    //     }      
    // }

    // public void update(double dt) {
    //     Image dbImage = createImage(getWidth(), getHeight());
    //     Graphics dbg = dbImage.getGraphics();
    //     this.draw(dbg);
    //     getGraphics().drawImage(dbImage, 0, 0, this);
  
    //     currentScene.update(dt);
    // }

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
        if( running ) {
            // Draw a grid for better visualize, all of the items will equal to a square of grid
            // for( int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++ ) {
            //     g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);                     // This will draw all horizontal line 
            //     g.drawLine(0,i*UNIT_SIZE , SCREEN_WIDTH, i*UNIT_SIZE);                      // This will draw all veritical line on the main window.
            // }

            // Draw an Apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);                                  // Draw a circle 

            // Draw a snake 
            for( int i = 0; i < bodyParts; i++ ) {                                            // iterate thru all of the body parts of the snake 
                if( i == 0 ) {                                                                // Snake's head
                    g.setColor(Color.blue);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    g.setColor(Color.black);
                    g.fillOval(x[i], y[i], UNIT_SIZE/2, UNIT_SIZE/2);                         // Snake's eye
                    g.setColor(Color.white);
                    g.fillOval(x[i], y[i], UNIT_SIZE/5, UNIT_SIZE/5);
                    g.setColor(Color.orange);
                    g.fillOval(x[i]+UNIT_SIZE/2, y[i]+UNIT_SIZE/2, UNIT_SIZE/2, UNIT_SIZE/2);
                    
                } else {                                                                      // Snake's body 
                    // g.setColor(new Color(0,0,153));
                    g.setColor( new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)) );
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor( Color.black ); 
            g.setFont( new Font("Ink Free",Font.BOLD, 35 ));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

        } else {
            gameOver(g);
        }

        // currentScene.draw(g);

    }

    public void newApple() {                                                       // We want to generate coordinates of a new apple. Any time we begin the game/score a point
        appleX = random.nextInt( (int) SCREEN_WIDTH/UNIT_SIZE) * UNIT_SIZE;        // This is the x-coordinate of Apple
        appleY = random.nextInt( (int) SCREEN_HEIGHT/UNIT_SIZE) * UNIT_SIZE;       // Multiply with UNIT_SIZE b/c we want this apple to be placed evenly within a square grid
    }


    public void move() {
        for( int i = bodyParts; i > 0; i-- ) {                                      // Shifting the snake's body parts 
            x[i] = x[i-1];                                                          // Shifting all the x-coordinates over by 1 spot
            y[i] = y[i-1];
        }
        // Change direction of the snake where the snakes headed
        switch( direction ) {
            case 'U':                                                               
                y[0] = y[0] - UNIT_SIZE;                                            // Go up, y-coordinate of the head of our snake
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L': 
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if( (x[0] == appleX) && (y[0] == appleY) ) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() { 
    // Checks if the snake's head collies with its body,Iterate thru the snake body's part
        for( int i = bodyParts; i > 0; i-- ) {                             
            if( (x[0] == x[i]) && (y[0] == y[i]) ) {
                running = false;                                    // Game Over 
            }
        }                                                             
    // Checks if snake's head hit the left-borders of the frame 
        if( x[0] < 0 ) {
            running = false;
        }
    // Checks if head hit the right-borders 
        if( x[0] > SCREEN_WIDTH) {
            running = false;
        }
    // Checks if head hit the top-borders 
        if( y[0] < 0 ) {
            running = false;
        }
    // Checks if head hit the bottom of the frame 
        if( y[0] > SCREEN_HEIGHT ) {
            running = false;
        } 
        if( !running ) {
            timer.stop();
        }
    }



    public void gameOver( Graphics g ) {
        // Display score on the frame
        g.setColor( Color.red ); 
        g.setFont( new Font("Ink Free",Font.BOLD, 35 ));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        // Game Over text 
        g.setColor( Color.red ); 
        g.setFont( new Font("Ink Free",Font.BOLD, 75 ));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);                // Game Over text in the center of the frame
        this.setBackground(Color.black);
    }
    
    
    @Override
    public void actionPerformed( ActionEvent e ) {                  // This method to run the snake game
        if( running ) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();                                                  // If this game is no longer running 
    }

    public class MyKeyAdapter extends KeyAdapter{                   // Inner class
        @Override
        public void keyPressed( KeyEvent e ) {
            switch( e.getKeyCode() ) {
                case KeyEvent.VK_LEFT:
                    if( direction != 'R' ) {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if( direction != 'L' ) {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if( direction != 'D' ) {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if( direction != 'U' ) {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
