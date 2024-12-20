package record;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class RecordTest extends BaseTest{

    @Test
    public void testGoogleSearch() throws Exception {

        // Navigate to Google
        driver.get("https://www.google.com");

        WebElement searchBox = driver.findElement(By.name("q"));

        // Enter a search query
        searchBox.sendKeys("Selenium");

        // Submit the query
        searchBox.submit();



    }
}

