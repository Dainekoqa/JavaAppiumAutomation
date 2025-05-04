import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class FirstTest {

    private AppiumDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws Exception {
        String appPath = System.getProperty("user.dir") + "/src/test/resources/apks/org.wikipedia.apk";

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("emulator-5554")
                .setAutomationName("UiAutomator2")
                .setApp(appPath)
                .setAppPackage("org.wikipedia")
                .setAppActivity("org.wikipedia.main.MainActivity");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void firstTest() {
        System.out.println("App launched successfully!");
    }

    @Test
    public void testSearchFieldText() {
        WebElement skipButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("org.wikipedia:id/fragment_onboarding_skip_button"))
        );
        skipButton.click();

        WebElement searchFieldContainer = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("org.wikipedia:id/search_container"))
        );

        WebElement searchText = searchFieldContainer.findElement(By.className("android.widget.TextView"));
        String actualText = searchText.getText();

        assertEquals("Search Wikipedia", actualText);
    }

    @Test
    public void testSearchCancel() {
        WebElement skipButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("org.wikipedia:id/fragment_onboarding_skip_button"))
        );
        skipButton.click();

        WebElement searchField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("org.wikipedia:id/search_container"))
        );
        searchField.click();

        WebElement input = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("org.wikipedia:id/search_src_text"))
        );
        input.sendKeys("Java");

        List<WebElement> results = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("org.wikipedia:id/page_list_item_title"))
        );
        assertTrue("Expected more than one result", results.size() > 1);

        WebElement cancel = driver.findElement(By.id("org.wikipedia:id/search_close_btn"));
        cancel.click();

        List<WebElement> clearedResults = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        assertEquals("Search results should be cleared after cancel", 0, clearedResults.size());
    }

    @Test
    public void testSearchResultsContainWord() {
        WebElement skipButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("org.wikipedia:id/fragment_onboarding_skip_button"))
        );
        skipButton.click();

        WebElement searchField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("org.wikipedia:id/search_container"))
        );
        searchField.click();

        WebElement input = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("org.wikipedia:id/search_src_text"))
        );
        input.sendKeys("Java");

        List<WebElement> results = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("org.wikipedia:id/page_list_item_title"))
        );

        assertFalse("No search results found", results.isEmpty());

        for (WebElement result : results) {
            String text = result.getText().toLowerCase();
            assertTrue("Search result does not contain 'java': " + text, text.contains("java"));
        }
    }
}
