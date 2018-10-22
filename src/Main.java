import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        new ScreenshotMakerThread().start();
    }
}

class ScreenshotMakerThread extends Thread {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
    private static final String ACCESS_TOKEN = "cTSc22ZW8BMAAAAAAAAAGKmNoxBu1Va-NB57LhbxezTjrEafniNrHVzmd26HMbct";
    private DbxRequestConfig config;
    private DbxClientV2 client;

    public ScreenshotMakerThread() {
        // DbxRequestConfig.newBuilder is deprecated,
        // see docs Official Dropbox Java API v.3.0.10
        config = new DbxRequestConfig("SkillboxScreenshoterWithThreads/1.0");
        client = new DbxClientV2(config, ACCESS_TOKEN);
    }

    public void run() {
        for (; ; ) {
            try {
                BufferedImage image =
                        new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", outputStream);
                InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                client.files()
                        .uploadBuilder("/" + (dateFormat.format(new Date())) + ".png")
                        .uploadAndFinish(inputStream);
                sleep(5000);
            } catch (AWTException | IOException | InterruptedException | DbxException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}