package tests;

import FirstTest.FirstTest;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.*;

public class AssertTitleTests extends FirstTest {

    @Test
    public void testAssertTitlePresent() throws InterruptedException {
        // Skip onboarding if present
        List<WebElement> skipButtons = driver.findElements(By.id("org.wikipedia:id/fragment_onboarding_skip_button"));
        if (!skipButtons.isEmpty()) {
            skipButtons.get(0).click();
            Thread.sleep(1000);
        }

        // Open search field
        WebElement searchField = driver.findElement(By.id("org.wikipedia:id/search_container"));
        searchField.click();
        Thread.sleep(1000);

        // Type "Java"
        WebElement searchInput = driver.findElement(By.id("org.wikipedia:id/search_src_text"));
        searchInput.sendKeys("Java");
        Thread.sleep(2000);

        // Click on "Java (programming language)" article
        WebElement javaArticle = driver.findElement(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']")
        );
        javaArticle.click();
        Thread.sleep(1000);

        // Assert that the article title is present immediately (no wait)
        assertElementPresent();
    }

    private void assertElementPresent() {
        List<WebElement> titles = driver.findElements(By.id("org.wikipedia:id/view_page_title_text"));
        assertFalse("Title not found", titles.isEmpty());
    }
}
