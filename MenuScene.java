import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class MenuScene extends Scene {
    public KL keyListener;
    public MousListener mouseListener;
    public BufferedImage title, play, playPressed, exit, exitPressed;
    public Rectangle playRect, exitRect, titleRect;

    public BufferedImage playCurrentImage, exitCurrentImage;


    public MenuScene(KL keyListener, MousListener mouseListener ) {
        this.keyListener = keyListener;
        this.mouseListener = mouseListener;

        try {
            BufferedImage spritesheet = ImageIO.read(new File("pics/menuSprite.png"));
            title = spritesheet.getSubimage(0, 242, 960, 240);
            play = spritesheet.getSubimage(0, 121, 261, 121);
            playPressed = spritesheet.getSubimage(264, 121, 261, 121);
            exit = spritesheet.getSubimage(0, 0, 233, 93);
            exitPressed = spritesheet.getSubimage(264, 0, 233, 93);

        } catch(Exception e) {
            e.printStackTrace();
        }

        playCurrentImage = play;
        exitCurrentImage = exit;

        titleRect = new Rectangle(142, 40, 300, 100);
        playRect = new Rectangle(220, 280, 150, 70);
        exitRect = new Rectangle(228, 355, 130, 55);

    }

    @Override
    public void update(double dt) {
        if( (mouseListener.getX() >= playRect.x) && (mouseListener.getX() <= playRect.x + playRect.width) &&
        (mouseListener.getY() >= playRect.y) && (mouseListener.getY() <= playRect.y + playRect.height) ) {
            playCurrentImage = playPressed;
            // if( mouseListener.isPressed() ) {
            //     GamePanel.changeState(1);
            // }
        } else {
            playCurrentImage = play;
        }
        if (mouseListener.getX() >= exitRect.x && mouseListener.getX() <= exitRect.x + exitRect.width &&
                mouseListener.getY() >= exitRect.y && mouseListener.getY() <= exitRect.y + exitRect.height) {
            exitCurrentImage = exitPressed;
            if( mouseListener.isPressed() ) {

            }
        } else {
            exitCurrentImage = exit;
        }
    }

    @Override
    public void draw(Graphics g) {
      g.setColor( new Color( 255,102,102));
      g.fillRect(0, 0, 600, 600);

      g.drawImage(title, (int) titleRect.x, (int) titleRect.y, (int) titleRect.width, (int) titleRect.height, null);
      g.drawImage(playCurrentImage, (int) playRect.x, (int) playRect.y, (int) playRect.width, (int) playRect.height, null);
      g.drawImage(exitCurrentImage, (int) exitRect.x, (int) exitRect.y, (int) exitRect.width, (int) exitRect.height, null);

    }
    
}
