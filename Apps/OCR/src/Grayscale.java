import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Grayscale {

    public BufferedImage grayscale(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        int height = image.getHeight();
        int width = image.getWidth();

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Color c = new Color(image.getRGB(j, i));
                int red = (int)(c.getRed() * 0.299);
                int green = (int)(c.getGreen() * 0.587);
                int blue = (int)(c.getBlue() * 0.114);
                int rgb = red + green + blue;
                image.setRGB(j, i, new Color(rgb, rgb, rgb).getRGB());
            }
        }
        return image;
    }
}
