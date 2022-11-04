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

    @BeforeMethod
    protected void beforeMethod() {
        System.setProperty("webdriver.chrome.driver", "c:\\Users\\Helga\\IdeaProjects\\chromedriver.exe");

        driver = new ChromeDriver();
    }

    @AfterMethod
    protected void afterMethod() {
        driver.quit();
    }

    protected WebDriver getDriver() {
        return driver;
    }

    @Ignore
    @Test
    public void testH2TagText_WhenSearchingCityCountry(){

        System.setProperty("webdriver.chrome.driver", "c:\\Users\\Helga\\IdeaProjects\\chromedriver.exe");


        getDriver().quit();
    }

    @Test
    public void testTC_11_01() throws InterruptedException {

        String url = "https://openweathermap.org/";

        getDriver().get(url);
        Thread.sleep(5000);

//      От Ольги
//        new Actions(getDriver()).moveToElement(getDriver().findElement(
//                By.xpath("//a[text()='Contrary']"))).build().perform();

//        String actualToolTip = new WebDriverWait(getDriver(), 20)
//                .until(ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//div[@class='tooltip-inner']"))).getText();

//       Нажимается и без allow cookies
        WebElement cookiesButton = new WebDriverWait(getDriver(), 20)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[@class='stick-footer-panel__link']")));

        cookiesButton.click();

//        if (driver.findElement(By.xpath("//button[@class='stick-footer-panel__link']")).getText().equals("Allow all")){
//
//            Thread.sleep(1000);
//            driver.findElement(By.xpath("//button[@class='stick-footer-panel__link']")).click();
//        }

        getDriver().findElement(By.xpath("//div[@id='desktop-menu']//a[@href='/guide']")).click();
        String currentUrl = getDriver().getCurrentUrl();
        String currentTitle = getDriver().getTitle();


        Assert.assertEquals(currentUrl, "https://openweathermap.org/guide");
        Assert.assertEquals(currentTitle, "OpenWeatherMap API guide - OpenWeatherMap");

        getDriver().quit();
    }
}
