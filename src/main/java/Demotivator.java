import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Demotivator {


    private int widthBorder;
    private int heightBorder;
    private static final int WIDTH_OUT = 10;
    private static final int HEIGHT_OUT = 10;
    private String resultPath = "";

    public Demotivator() {

    }

    /*
    *   Constructor with setting saving path
     */

    public Demotivator(String resultPath) {
        this.resultPath = resultPath;
    }

    /*
     *   Creating with class-based url
     *   Gets an image and then creates demo
     *   Uses default path
     */

    public void createDemo(URL url, String text){
        getFile(url);
        try {
            File file = new File("input.png");
            creatingDemo(file, text);
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     *   Creating with string url
     *   Converts url to class-based url, gets an image and then creates demo
     *   Uses default path
     */
    public void createDemo(String url, String text){
        try {
            URL urling = new URL(url);
            getFile(urling);
            File file = new File("input.png");
            creatingDemo(file, text);
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *   Creating with file from user
     */
    public void createDemo(File file, String text){
        try {
            creatingDemo(file, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     *   Method for getting an image from url
     */
    private void getFile(URL url){
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
            ImageIO.write(image, "png", new File("input.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *   Method for resizing image
     */

    private File resizeImg(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);



        Image newImage = image.getScaledInstance(350, 350, Image.SCALE_DEFAULT);
        BufferedImage resized = new BufferedImage(newImage.getWidth(null), newImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = resized.createGraphics();
        gr.drawImage(newImage, 0, 0, null);
        gr.dispose();

        ImageIO.write(resized, "png", file);
        return file;
    }

    /*
     *   Method for creating demo
     */
    private void creatingDemo(File file, String text) throws IOException {
        BufferedImage image = ImageIO.read(resizeImg(file));
        /*
        *   To adapt the size of the frame and text
         */
        widthBorder = (image.getWidth() + WIDTH_OUT * 2) / 4;
        heightBorder = (image.getHeight() + HEIGHT_OUT * 2) / 4;

        /*
         *   Creating black frame image
         */

        BufferedImage result = new BufferedImage(image.getWidth() + widthBorder * 2 + WIDTH_OUT * 2, image.getHeight() + heightBorder * 2 + HEIGHT_OUT * 2, image.getType());
        Graphics2D graphics = result.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fill(new Rectangle(0, 0, result.getWidth(), result.getHeight()));
        graphics.dispose();

        /*
         *   Creating white frame round the input image
         */

        BufferedImage frames = new BufferedImage(image.getWidth() + WIDTH_OUT * 2, image.getHeight() + HEIGHT_OUT * 2, image.getType());
        graphics = frames.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.setStroke(new BasicStroke(5));
        graphics.draw(new Rectangle2D.Double(0, 0, frames.getWidth(), frames.getHeight()));
        graphics.drawImage(image, WIDTH_OUT, HEIGHT_OUT, null);
        graphics.dispose();

        /*
         *   Concat black frame and input with white frame
         */

        graphics = result.createGraphics();
        graphics.drawImage(frames, frames.getWidth() / 4, frames.getHeight() / 4, null);


        /*
        *   Searching for necessary font-size
         */

        int size = 100;

        Font font = new Font("TimesRoman", Font.PLAIN, size);
        graphics.setFont(font);
        FontMetrics metrics = graphics.getFontMetrics(font);

        int textWidth = size * text.length();

        while((textWidth >= result.getWidth() - WIDTH_OUT * 4) && (result.getHeight() - heightBorder / 2 - metrics.getHeight() <= frames.getHeight() + heightBorder)){
            size -= 1;
            font = new Font("TimesRoman", Font.PLAIN, size);
            graphics.setFont(font);
            metrics = graphics.getFontMetrics(font);
            textWidth = size * text.length();
        }
        /*
        *   Adding text
         */
        graphics.drawString(text, (result.getWidth() - metrics.stringWidth(text)) / 2, result.getHeight() - heightBorder / 2);

        graphics.dispose();


         /*
         *  Saving using path
          */

        if(!resultPath.isEmpty()){
            ImageIO.write(result, "png", new File(resultPath + "\\result.png"));
        } else{
            ImageIO.write(result, "png", new File("result.png"));
        }

    }


}
