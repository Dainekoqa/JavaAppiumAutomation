package tests;

import FirstTest.FirstTest;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class SavedArticlesTests extends FirstTest {

    @Test
    public void testSaveTwoArticlesAndDeleteOne() throws InterruptedException {
        // 1. Skip onboarding
        List<WebElement> skipButtons = driver.findElements(By.id("org.wikipedia:id/fragment_onboarding_skip_button"));
        if (!skipButtons.isEmpty()) {
            skipButtons.get(0).click();
            Thread.sleep(1000);
        }

        // 2. Search and save Java
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.id("org.wikipedia:id/search_container")));
        searchField.click();
        Thread.sleep(1000);

        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("org.wikipedia:id/search_src_text")));
        searchInput.sendKeys("Java");
        Thread.sleep(2000);

        WebElement javaArticle = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']")));
        javaArticle.click();
        Thread.sleep(1000);

        WebElement saveBtnJava = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("org.wikipedia:id/page_save")));
        saveBtnJava.click();
        Thread.sleep(1000);

        clickNavigateUp();
        Thread.sleep(500);

        // 3. Clear search
        WebElement clearBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("org.wikipedia:id/search_close_btn")));
        clearBtn.click();
        Thread.sleep(1000);

        // 4. Search and save Appium
        searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("org.wikipedia:id/search_src_text")));
        searchInput.sendKeys("Appium");
        Thread.sleep(2000);

        WebElement appiumArticle = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Appium']")));
        appiumArticle.click();
        Thread.sleep(1000);

        WebElement saveBtnAppium = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("org.wikipedia:id/page_save")));
        saveBtnAppium.click();
        Thread.sleep(1000);

        clickNavigateUp();
        Thread.sleep(500);
        clickNavigateUp();
        Thread.sleep(500);

        // 5. Open "Saved" tab
        WebElement savedTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.FrameLayout[@content-desc='Saved']")));
        savedTab.click();
        Thread.sleep(1500);

        // 6. Dismiss sync popup if present
        dismissSyncPopupIfPresent();

        // 7. Open saved reading list
        WebElement savedList = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("org.wikipedia:id/item_title_container")));
        savedList.click();
        Thread.sleep(1500);

        // 8. Dismiss "Got it" tooltip if present
        dismissReadingListTooltipIfPresent();

        // 9. Swipe Java article to the left
        swipeArticleJavaToLeft();
        Thread.sleep(1000);

        // 10. Verify snackbar message
        WebElement snackbar = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("org.wikipedia:id/snackbar_text")));
        assertEquals("Java (programming language) removed from Saved", snackbar.getText());

        // 11. Verify only Appium article remains
        List<WebElement> titles = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        assertEquals("Expected only 1 article left", 1, titles.size());
        assertEquals("Appium", titles.get(0).getText());
    }

    private void clickNavigateUp() throws InterruptedException {
        WebElement backBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")));
        backBtn.click();
        Thread.sleep(500);
    }

    private void dismissSyncPopupIfPresent() throws InterruptedException {
        List<WebElement> popup = driver.findElements(By.id("android:id/button2"));
        if (!popup.isEmpty()) {
            popup.get(0).click();
            Thread.sleep(1000);
        }
    }

    private void dismissReadingListTooltipIfPresent() throws InterruptedException {
        List<WebElement> gotItBtn = driver.findElements(By.id("org.wikipedia:id/buttonView"));
        if (!gotItBtn.isEmpty()) {
            gotItBtn.get(0).click();
            Thread.sleep(1000);
        }
    }

    private void swipeArticleJavaToLeft() {
        WebElement javaArticle = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']")));

        int startX = javaArticle.getLocation().getX() + javaArticle.getSize().getWidth() - 50;
        int endX = javaArticle.getLocation().getX() + 50;
        int y = javaArticle.getLocation().getY() + (javaArticle.getSize().getHeight() / 2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, y));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), endX, y));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(swipe));
    }
}
