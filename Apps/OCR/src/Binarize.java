import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Binarize {

    public int threshold(BufferedImage image){
        int[] histogram = histogram(image);
        int pixels = image.getHeight() * image.getWidth();

        float sum = 0;
        for(int i = 0; i < 256; i++)
            sum += i * histogram[i];

        float sumB = 0;
        int wB = 0;
        int wF;

        float varMax = 0;
        int threshold = 0;

        for(int i = 0; i < 256; i++){
            wB += histogram[i];
            if(wB == 0) continue;
            wF = pixels - wB;

            if(wF == 0) break;

            sumB += (float)(i * histogram[i]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;

            float varBetween = (float)wB * (float)wF * (mB - mF) * (mB - mF);

            if(varBetween > varMax){
                varMax = varBetween;
                threshold = i;
            }
        }

        return threshold;
    }

    public BufferedImage binarize(BufferedImage image) throws IOException {
        int red;
        int newPixel;

        int threshold = threshold(image);

        BufferedImage binarizedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                red = new Color(image.getRGB(j, i)).getRed();
                int alpha = new Color(image.getRGB(j, i)).getAlpha();
                if(red > threshold){
                    newPixel = 255;
                }else{
                    newPixel = 0;
                }

                newPixel = toRGB(alpha, newPixel, newPixel, newPixel);
                binarizedImage.setRGB(j, i, newPixel);
            }
        }

        return binarizedImage;
    }

    public int[] histogram(BufferedImage image){
        int[] histogram = new int[256];

        for(int i = 0; i < histogram.length; i++)
            histogram[i] = 0;

        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                int red = new Color(image.getRGB(j, i)).getRed();
                histogram[red]++;
            }
        }

        return histogram;
    }

    public int toRGB(int alpha, int r, int g, int b){
        int newPixel = 0;
        newPixel += alpha;
        newPixel <<= 8;
        newPixel += r;
        newPixel <<= 8;
        newPixel += g;
        newPixel <<= 8;
        newPixel += b;

        return newPixel;
    }
}