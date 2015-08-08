import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Scan {
    private int leftMargin = 0;
    private int rightMargin = 0;

    private BufferedImage image;
    private int width;
    private int height;

    int[] lines; //1 = Empty
    int[] words;
    public Scan(BufferedImage i){
        this.image = i;
        width = i.getWidth();
        height = i.getHeight();

        lines = new int[height];
        words = new int[width];
    }

    public void findMargin(){
        int spaceCounter = 0;
        int resets = 0;
        int avgSpace = 0;
        for (int i = 10; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (isBlack(i, j)) {
                    avgSpace += spaceCounter;
                    resets++;
                    spaceCounter = 0;
                } else {
                    spaceCounter++;
                }
            }

            if (resets != 0 && (avgSpace / resets) < (height / 100)) {
                leftMargin = i - 5;
                break;
            }
            spaceCounter = resets = avgSpace = 0;
        }

        for (int i = width - 10; i >= 0; i--) {
            for (int j = 0; j < height; j++) {
                if (isBlack(i, j)) {
                    avgSpace += spaceCounter;
                    resets++;
                    spaceCounter = 0;
                } else {
                    spaceCounter++;
                }
            }

            avgSpace += spaceCounter;
            if (resets != 0 && (avgSpace / resets) < (height / 100)) {
                rightMargin = i + 5;
                break;
            }
            spaceCounter = resets = avgSpace = 0;
        }

        /**DEBUG*/
        System.out.println("Margins - Left: x = " + leftMargin + ", Right: x = " + rightMargin);
        System.out.println("Highlighting margins...");
        for(int i = 0; i < height; i++){
            try{
                image.setRGB(leftMargin, i, new Color(0, 255, 0).getRGB());
                image.setRGB(rightMargin, i, new Color(0, 255, 0).getRGB());
                ImageIO.write(image, "jpg", new File(System.getProperty("user.dir") + "/margin-test.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Margins highlighted.");
        System.out.println("Check margin-test.jpg for results.");
        /**DEBUG*/
    }

    public void findLineBreaks(){
        int marginDifference = rightMargin - leftMargin;
        int fillCount = 0;
        int yCount = 0; //DEBUG
        int[] yVals = new int[height];
        for(int i = 0; i < height; i++) {
            for(int j = leftMargin; j < rightMargin; j++){
                if(isBlack(j, i)){
                    fillCount++;
                }
            }
            if(fillCount > (marginDifference * .08)){ //If more than 8% of the line has text(black pixels)
                yVals[i] = 0; //Not a blank line
            }else{
                yVals[i] = 1;
                yCount++; //DEBUG
            }
            fillCount = 0;
        }

        lines = yVals;
        /**DEBUG*/
        System.out.println(yCount + " lines detected.");
        System.out.println("Highlighting lines...");
        for(int i = 0; i < yVals.length; i++) {
            try {
                if(yVals[i] == 1) {
                    for(int j = leftMargin; j < rightMargin; j++){
                        image.setRGB(j, i, new Color(255, 0, 0).getRGB());
                    }
                    ImageIO.write(image, "jpg", new File("C:/Users/carte_000/IdeaProjects/OCR/linebreak-test.jpg"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Line breaks highlighted.");
        System.out.println("Check linebreak-test.jpg for results.");
        /**DEBUG*/
    }

    public void findSpaces(){

    }

    public boolean isBlack(int x, int y){
        int rgb = image.getRGB(x, y);
        int r = (0x00ff0000 & rgb) >> 16;
        int g = (0x0000ff00 & rgb) >> 8;
        int b = (0x000000ff & rgb);

        return r == g && b == g && g == 0;
    }
}
