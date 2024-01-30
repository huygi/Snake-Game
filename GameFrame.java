import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

   public static int currentState;
   public static Scene currentScene;

   public static KL keyListener = new KL();
   public static MousListener mouseListener = new MousListener();

   public GameFrame() {
      GamePanel panel = new GamePanel();              
      this.add(panel);                                        // We can do shortcut: this.add(new GamePanel());
      this.setTitle("Snake");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.pack();                                            // This method fixes sizes of the frame so that all its components are at or above their preferred sizes. 
      this.setVisible(true);
      this.setLocationRelativeTo(null);                     // This will set the window frame to appear in the middle of our computer screen

      addKeyListener(GameFrame.keyListener);
      addMouseListener(GameFrame.mouseListener);
      addMouseMotionListener(GameFrame.mouseListener);
      GameFrame.changeState(0);

   }

   public static void changeState( int newState ) {
      GameFrame.currentState = newState;
      switch( GameFrame.currentState ) {
          case 0:
              GameFrame.currentScene = new MenuScene(keyListener, mouseListener);
              break;
          case 1:
              GameFrame.currentScene = new GameScene();
              break;
          default:
              System.out.println("Unknown scene.");
              GameFrame.currentScene = null;
              break;
      }      
  }

   public void update(double dt) {
      Image dbImage = createImage(getWidth(), getHeight());
      Graphics dbg = dbImage.getGraphics();
      this.draw(dbg);
      getGraphics().drawImage(dbImage, 0, 0, this);

      currentScene.update(dt);
  }

   public void draw(Graphics g) {
      currentScene.draw(g);
   }

}
