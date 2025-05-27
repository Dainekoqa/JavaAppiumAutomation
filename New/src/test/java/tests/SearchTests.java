package tests;

import FirstTest.FirstTest;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.Assert.*;

public class SearchTests extends FirstTest {

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
}
