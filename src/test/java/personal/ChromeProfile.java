package personal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ChromeProfile {
    WebDriver driver;

    @BeforeMethod
    public void runChannel() {
        String chromeProfilePath = "C:\\Users\\Admin\\AppData\\Local\\Google\\Chrome\\User Data";
        String youtubeChannelUrl = "https://www.youtube.com/@Anbuvlogs46";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=" + chromeProfilePath);
        options.addArguments("--profile-directory=Default");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("user-data-dir=C:\\Users\\Admin\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("--profile-directory=Profile 3");
        driver = new ChromeDriver(options);
        driver.get(youtubeChannelUrl);
    }

    @Test(invocationCount = 1, threadPoolSize = 5)
    public void playAllVideosAndShorts() throws InterruptedException {
        Thread.sleep(5000);

        // Get all video and short elements
        List<WebElement> videoElements = driver.findElements(By.xpath("//ytd-grid-video-renderer | //ytd-reel-grid-renderer"));

        for (int i = 0; i < videoElements.size(); i += 3) {
            int end = Math.min(i + 3, videoElements.size());

            for (int j = i; j < end; j++) {
                WebElement videoElement = videoElements.get(j);
                // Open video in new tab
                String videoUrl = videoElement.findElement(By.xpath(".//a[@id='video-title'] | .//a[@id='reel-title']")).getAttribute("href");
                ((ChromeDriver) driver).executeScript("window.open('" + videoUrl + "')");
            }

            // Switch to new tabs and play videos
            for (int j = i + 1; j <= end; j++) {
                driver.switchTo().window((String) ((ChromeDriver) driver).getWindowHandles().toArray()[j]);
                playVideo();
            }

            // Close new tabs
            for (int j = i + 1; j <= end; j++) {
                driver.close();
                driver.switchTo().window((String) ((ChromeDriver) driver).getWindowHandles().toArray()[0]);
            }
        }
    }

    private void playVideo() throws InterruptedException {
        // Check if it's a Short
        if (driver.findElements(By.xpath("//ytd-reel-player")).size() > 0) {
            // Handle Short video player
            System.out.println("Playing Short...");
            Thread.sleep(60000); // Adjust this to Short length dynamically if needed
        } else {
            // Handle regular video player
            System.out.println("Playing Video...");
            WebElement playPauseButton = driver.findElement(By.className("ytp-play-button"));
            WebElement seekBar = driver.findElement(By.className("ytp-progress-bar"));
            WebElement speedButton = driver.findElement(By.className("ytp-speed-button"));

            // Set video speed to 2x
            speedButton.click();
            driver.findElement(By.xpath("//div[@role='menu']//div[@role='menuitem'][2]")).click();

            // Pause the video
            playPauseButton.click();
            System.out.println("Paused the video.");
            Thread.sleep(2000);

            // Play the video
            playPauseButton.click();
            System.out.println("Resumed playing.");
            Thread.sleep(5000);

            // Forward the video
            seekBar.sendKeys("l"); // Keyboard shortcut to seek forward
            System.out.println("Skipped forward.");
            Thread.sleep(2000);

            // Backward the video
            seekBar.sendKeys("j"); // Keyboard shortcut to seek backward
            System.out.println("Rewound the video.");
            Thread.sleep(2000);

            // Wait for the video to finish (simulate full playback)
            Thread.sleep(30000); // Adjust this to video length dynamically if needed (2x speed)
        }
    }
}