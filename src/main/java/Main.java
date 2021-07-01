import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

class Main{
    public static void main(String[] args) {
        Demotivator demo1 = new Demotivator(); // without path
        Demotivator demo2 = new Demotivator("your path"); // with resource directory
        try {
            demo1.createDemo(new URL("your url"), "Hello"); // creating demo using url
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String stringUrl = "your url";
        demo2.createDemo(stringUrl, "Hello"); // creating demo using string url

        demo1.createDemo(new File("your path"), "Hello"); // creating demo using file
    }
}