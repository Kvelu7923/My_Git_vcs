package record;


import Selenium_Recorder.ScreenRecorder;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseTest {
    ChromeDriver driver;

    protected ScreenRecorder screenRecorder;

    @BeforeMethod
    public void startRecording() throws Exception {
        screenRecorder = new ScreenRecorder();
        // Provide directory or file path for recordings
        screenRecorder.startRecording("./videos/testRecording.mp4");
    }
    @AfterMethod
    public void stopRecording() throws Exception {
        if (screenRecorder != null) {
            screenRecorder.stopRecording();
        }
    }

}