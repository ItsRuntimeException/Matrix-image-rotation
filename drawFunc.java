import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class drawFunc
{
    public static void drawThis(BufferedImage img) throws IOException
    {
        JFrame frame = buildFrame();
        
        JPanel pane = new JPanel(){
        
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                AffineTransform at = new AffineTransform();
                
                at.translate(this.getWidth()/2, this.getHeight()/2);
                at.translate(-img.getWidth()/2, -img.getHeight()/2);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(img, at, null);
            }
        };
        
            frame.add(pane);
    }
    private static JFrame buildFrame()
    {
        JFrame frame = new JFrame();
        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1366,768);
        frame.setVisible(true);
        
            return frame;
    }
}