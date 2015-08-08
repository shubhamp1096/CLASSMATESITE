import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    private static BufferedImage binary;

    public static void main(String[] args){
        Grayscale g = new Grayscale();
        Binarize b = new Binarize();
        Scan s;
        BufferedImage testB;
        try {
            File file = new File(Main.class.getResource("/page.jpg").toURI());
            testB = b.binarize(g.grayscale(file));
            ImageIO.write(testB, "jpg", new File(System.getProperty("user.dir") + "/page-binary.jpg"));
            binary = ImageIO.read(new File(System.getProperty("user.dir") + "/page-binary.jpg"));
            s = new Scan(binary);
            s.findMargin();
            s.findLineBreaks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}