import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        new ScreenshotMakerThread().start();
    }
}

class ScreenshotMakerThread extends Thread {
    private String generateFilename() {
        /*
         * Method generate unique filename from current timestamp
         */
        return "C://Tmp//" + (new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())) + ".png";
    }

    public void run() {
        for (; ; ) {
            try {
                BufferedImage image =
                        new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ImageIO.write(image, "png", new File(generateFilename()));
                sleep(5000);
            } catch (AWTException | IOException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}