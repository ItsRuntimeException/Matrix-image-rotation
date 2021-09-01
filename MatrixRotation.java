import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.awt.geom.AffineTransform;

public class MatrixRotation extends drawFunc
{
    private static double radians;
    private static double m00;
    private static double m10;
    private static double m01;
    private static double m11;
    
    private static double[][] rotationMatrix(double radians)
    {
        double root = Math.sqrt(2);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        
        if (Math.toDegrees(radians) <= 90 || Math.toDegrees(radians) >= 180)
        {
            m00 =    root*cos;      m01 = root*sin;
            
            m10 = -1*root*sin;      m11 = root*cos;
        }
        else if (Math.toDegrees(radians) > 90 || Math.toDegrees(radians) <= 360)
        {
            m00 = root*cos;     m01 = -1*root*sin;
            
            m10 = root*sin;     m11 = root*cos;
        }
        
        double rotationMat[][] = {{m00, m01},
                                  {m10, m11}};
                                  
                                  
        return rotationMat;
    }
    
    public static void rotated(BufferedImage img)
    {  
        // 0 <= Theta <= Math.PI;
        for (int degrees = 0; degrees <= 360; degrees+=90)
        {
            double      [][] matrix_R = rotationMatrix(Math.toRadians(degrees));
            int         [][] matrix_I = getImageMatrix(img);
            int sizeX = img.getWidth();
            int sizeY = img.getHeight();
            int         [][] target   = new int[sizeY*2][sizeY*2];
            BufferedImage new_Img = new BufferedImage(sizeY*2, sizeY*2, 1);
            
            // Applying mat_R to mat_Image to obtain new [x, y];
            for (int y = 0; y < sizeY; y++)    // y
            {
                for (int x = 0; x < sizeX; x++) // x
                {
                    int new_X = -1;
                    int new_Y = -1;
                    int centerY = sizeY / 2;
                    int centerX = sizeX / 2 + 100;
                    if (degrees <= 90 || degrees >= 180)
                    {
                        new_X = ((int)matrix_R[0][0] * x + (int)matrix_R[0][1] * y)+1;
                        new_Y = ((int)matrix_R[1][0] * x + (int)matrix_R[1][1] * y)+sizeX;
                    }
                    else if (degrees > 90 || degrees <= 360)
                    {
                        new_X = ((int)matrix_R[0][0] * x + (int)matrix_R[0][1] * y)+sizeX;
                        new_Y = ((int)matrix_R[1][0] * x + (int)matrix_R[1][1] * y)+1;
                    }
                    target[new_Y/2 + centerY][new_X/2 + centerX] = matrix_I[y][x];
                }
            }
            // set RGB Pixel
            for (int i = 0; i < sizeY*2; i++)
            {
                for (int j = 0; j < sizeX*2; j++)
                {
                    new_Img.setRGB(i,j, target[j][i]);
                }
            }
            
            // delay function;
            try{
                try{
                    //radians = Math.toRadians(degrees);
                    drawThis(new_Img);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                Thread.sleep(500); // in ms.
            }
            catch(InterruptedException e) {
                ; // do noting.
            }
        }
    }
    
    private static int[][] getImageMatrix(BufferedImage img)
    {
        final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        final int width = img.getWidth();
        final int height = img.getHeight();
        final boolean hasAlphaChannel = (img.getAlphaRaster() != null);
        
        int[][] matrix_Img = new int[height][height];
        if (hasAlphaChannel)
        {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                matrix_Img[row][col] = argb;
                col++;
                if (col == width)
                {
                    col = 0;
                    row++;
                }
            }
        } 
        else
        {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                matrix_Img[row][col] = argb;
                col++;
                if (col == width)
                {
                    col = 0;
                    row++;
                }
            }
        }
        return matrix_Img;
    }
}