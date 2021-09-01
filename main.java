import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
public class main extends MatrixRotation
{
    public static void main()
    {
        try{
            BufferedImage imgBuffer = 
                ImageIO.read(new File("C:\\Users\\run-l\\Desktop\\Matrix-Image Rotation\\smile.jpg"));
            
            rotated(imgBuffer);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}