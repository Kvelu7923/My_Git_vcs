package unittest;

import Selenium_Recorder.ScreenRecorder;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestWithRecording {

    public static void main(String[] args) throws Exception {
        ScreenRecorder recorder = new ScreenRecorder();
        String videoPath = "./videos/testRecording.mp4";

        try {
            recorder.startRecording(videoPath);

            // Set up Selenium WebDriver
            ChromeDriver driver = new ChromeDriver();
            // Example Selenium actions
            driver.get("http://leaftaps.com/opentaps/control/main");
            System.out.println("Page title is: " + driver.getTitle());

            driver.quit();

        } finally {
            recorder.stopRecording();
        }
    }

}


