import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class GdikhsanovTest {
    //TC_1_1  - Тест кейс:
    //1. Открыть страницу https://openweathermap.org/
    //2. Набрать в строке поиска город Paris
    //3. Нажать пункт меню Search
    //4. Из выпадающего списка выбрать Paris, FR
    //5. Подтвердить, что заголовок изменился на "Paris, FR"

    private WebDriver driver;

    protected WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    protected void beforeMethod() {

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.home") + System.getProperty("file.separator") + "chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.home") + System.getProperty("file.separator") + "chromedriver.exe");
        }
        driver = new ChromeDriver();
    }

    @AfterMethod
    protected void afterMethod() {
        getDriver().quit();
    }

    @Test
    public void testTC_11_01() throws InterruptedException {

        String url = "https://openweathermap.org/";

        getDriver().get(url);
        Thread.sleep(5000);

//      От Ольги наведение на элемент, проверка на видимость и получение текста.
//        new Actions(getDriver()).moveToElement(getDriver().findElement(
//                By.xpath("//a[text()='Contrary']"))).build().perform();

//        String actualToolTip = new WebDriverWait(getDriver(), 20)
//                .until(ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//div[@class='tooltip-inner']"))).getText();

        WebElement cookiesAllowButton = new WebDriverWait(getDriver(), 20)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[@class='stick-footer-panel__link']")));

        cookiesAllowButton.click();

        getDriver().findElement(By.xpath("//div[@id='desktop-menu']//a[@href='/guide']")).click();
        String currentUrl = getDriver().getCurrentUrl();
        String currentTitle = getDriver().getTitle();

        Assert.assertEquals(currentUrl, "https://openweathermap.org/guide");
        Assert.assertEquals(currentTitle, "OpenWeatherMap API guide - OpenWeatherMap");
    }
}
