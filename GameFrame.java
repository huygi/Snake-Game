import javax.swing.JFrame;

public class GameFrame extends JFrame {
     public GameFrame() {

        GamePanel panel = new GamePanel();              
        this.add(panel);                                        // We can do shortcut: this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();                                            // This method fixes sizes of the frame so that all its components are at or above their preferred sizes. 
        this.setVisible(true);
        this.setLocationRelativeTo(null);                     // This will set the window frame to appear in the middle of our computer screen
     }
}
