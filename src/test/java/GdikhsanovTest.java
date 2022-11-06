import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class GdikhsanovTest {

    private WebDriver driver;

    protected WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    protected void beforeMethod() {
        //выбираем файл хромдрайвера в зависимости от системы
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.home")
                    + System.getProperty("file.separator") + "chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.home")
                    + System.getProperty("file.separator") + "chromedriver.exe");
        }

        //скрываем селениум и разворачиваем браузер на весь экран
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("start-maximized");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//        driver = new ChromeDriver(capabilities); // можно убрать capabilities и сделать ChromeDriver(options);
        driver = new ChromeDriver(options);
//        getDriver().manage().window().maximize(); // повторяет options.addArguments("start-maximized");
    }

    @AfterMethod
    protected void afterMethod() {
        getDriver().quit();
    }

//      От Ольги наведение на элемент, проверка на видимость и получение текста.
//        new Actions(getDriver()).moveToElement(getDriver().findElement(
//                By.xpath("//a[text()='Contrary']"))).build().perform();

//               String actualToolTip = new WebDriverWait(getDriver(), 20)
//                .until(ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//div[@class='tooltip-inner']"))).getText();
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void test1Openweathermap_justGoToGuide_gdiksanov() {

        String url = "https://openweathermap.org/";

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        getDriver().get(url);
        new WebDriverWait(getDriver(), 10)
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[@class='owm-loader-container']/div")));

        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(By.xpath("//div[@id='desktop-menu']//a[@href='/guide']")).click();

        String currentUrl = getDriver().getCurrentUrl();
        String currentTitle = getDriver().getTitle();

        Assert.assertEquals(currentUrl, "https://openweathermap.org/guide");
        Assert.assertEquals(currentTitle, "OpenWeatherMap API guide - OpenWeatherMap");
    }

    @Test
    public void test2Openweathermap_units_gdiksanov() {

        String url = "https://openweathermap.org/";
        WebDriverWait wait = new WebDriverWait(getDriver(), 10); // можно назначить один раз за тест или класс

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getDriver().get(url);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(By.xpath("//div[@class='option'][contains (text(),'Imperial: °F, mph')]")).click();

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getDriver().get(url);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement text = getDriver().findElement(By.xpath("//span[@class='heading']"));
        char currentUnit = text.getText().charAt(text.getText().length() - 1);

        Assert.assertEquals(currentUnit, 'F');
    }

    @Test
    public void test3Openweathermap_cookiesPanelButtons_gdiksanov() {
        String url = "https://openweathermap.org/";

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getDriver().get(url);
        new WebDriverWait(getDriver(), 10)
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        String footerCookiesText = getDriver().findElement(
                By.className("stick-footer-panel__description")).getText();
        String expectedPanelText = "We use cookies which are essential for the site to work. " +
                "We also use non-essential cookies to help us improve " +
                "our services. Any data collected is anonymised. " +
                "You can allow all cookies or manage them individually.";
        String allowAllBtnText = getDriver().findElement(
                        By.xpath("//button[@class='stick-footer-panel__link']"))
                .getText();
        String expectedAllowAllBtnText = "Allow all";
        String manageBtnText = getDriver().findElement(
                        By.xpath("//a[@class='stick-footer-panel__link']"))
                .getText();
        String expectedManageBtnText = "Manage cookies";

        //Ирина говорит, что надо отдельно найти панель, отдельно текст на ней "Подтвердить, что внизу страницы есть панель с текстом “We use"

        Assert.assertEquals(footerCookiesText, expectedPanelText);
        Assert.assertEquals(allowAllBtnText, expectedAllowAllBtnText);
        Assert.assertEquals(manageBtnText, expectedManageBtnText);
    }

    @Test
    public void test4Openweathermap_supportMenu_gdiksanov() {

        String url = "https://openweathermap.org/";
        String email = "qwerty@qwerty.org";
        String message = "Hello world";

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getDriver().get(url);
        new WebDriverWait(getDriver(), 10)
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(By.xpath("//div[@id='support-dropdown']")).click();

        WebElement faq = getDriver().findElement(By.xpath("//ul[@id='support-dropdown-menu']//a[@href='/faq']"));
        WebElement how = getDriver().findElement(By.xpath("//ul[@id='support-dropdown-menu']//a[contains (text(), 'How to start')]"));
        WebElement ask = getDriver().findElement(By.xpath("//ul[@id='support-dropdown-menu']//a[contains (text(), 'Ask a question')]"));

        Assert.assertEquals(faq.getText(), "FAQ");
        Assert.assertEquals(how.getText(), "How to start");
        Assert.assertEquals(ask.getText(), "Ask a question");
    }

    @Test
    public void test5Openweathermap_captchaError_gdiksanov() {

        String url = "https://openweathermap.org/";
        String email = "qwerty@qwerty.org";
        String message = "Hello world";

        WebDriverWait wait = new WebDriverWait(getDriver(), 10); // можно назначить один раз за тест или класс

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getDriver().get(url);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(By.xpath("//div[@id='support-dropdown']")).click();

        String originalWindow = getDriver().getWindowHandle();

//Check we don't have other windows open already
        Assert.assertTrue(getDriver().getWindowHandles().size() == 1);

//Click the link which opens in a new window
        getDriver().findElement(
                By.xpath("//ul[@id='support-dropdown-menu']//a[contains (text(), 'Ask a question')]")).click();

//Wait for the new window or tab
        wait.until(numberOfWindowsToBe(2));

//Loop through until we find a new window handle and switch to it
        for (String windowHandle : getDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                getDriver().switchTo().window(windowHandle);
                break;
            }
        }

//  Альтернатива "для бегинеров"
//        List<String> set = new ArrayList<>(getDriver().getWindowHandles());
//
//        for (int i = 0; i < getDriver().getWindowHandles().size(); i++) {
//            if (!originalWindow.equals(set.get(i))) {
//                getDriver().switchTo().window(set.get(i));
//                break;
//            }
//        }

//Wait for the new tab to finish loading content

        wait.until(urlToBe("https://home.openweathermap.org/questions"));

        getDriver().findElement(
                By.xpath("//input[@id='question_form_email']")).sendKeys(email);

        Select select = new Select(getDriver().findElement(
                By.xpath("//select[@id='question_form_subject']")));
        select.selectByValue("Sales");

//        getDriver().findElement(
//                By.xpath("//select[@id='question_form_subject']")).click(); //select у селениума найти метод
//        getDriver().findElement(
//                By.xpath("//option[@value='Sales']")).click();
        getDriver().findElement(
                By.xpath("//textarea[@id='question_form_message']")).sendKeys(message);

        getDriver().findElement(
                By.xpath("//input[@type='submit'][@name='commit']")).click();

//        getDriver().switchTo().frame(getDriver().findElement(By.id("new_question_form")));

        System.out.println(getDriver().findElement(By.xpath("//form[@id='new_question_form']//div[@class='help-block']")).getText());

        Assert.assertTrue(getDriver().findElement(By.xpath("//form[@id='new_question_form']//div[@class='help-block']")).getText().equals("reCAPTCHA verification failed, please try again."));
    }

    @Ignore
    @Test
    public void test6Openweathermap_emailError_gdiksanov() throws InterruptedException {
        //try to sign in with Google account
//        getDriver().get("https://accounts.google.com/signin/v2/identifier?hl=en&passive=true&continue=https%3A%2F%2Fwww.google.com%2F%3Fgws_rd%3Dssl&ec=GAZAmgQ&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
//        Thread.sleep(2000);
//        getDriver().findElement(By.name("identifier")).sendKeys("Email"+ Keys.ENTER);
//        Thread.sleep(3000);
//        getDriver().findElement(By.name("password")).sendKeys("Password"+ Keys.ENTER);
//        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(getDriver(), 10); // можно назначить один раз за тест или класс

        String url = "https://openweathermap.org/";
        String email = "";
        String message = "Hello world";

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        getDriver().get(url);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(By.xpath("//div[@id='support-dropdown']")).click();

        String originalWindow = getDriver().getWindowHandle();

//Check we don't have other windows open already
        Assert.assertTrue(getDriver().getWindowHandles().size() == 1);

//Click the link which opens in a new window
        getDriver().findElement(
                By.xpath("//ul[@id='support-dropdown-menu']//a[contains (text(), 'Ask a question')]")).click();

//Wait for the new window or tab
        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wait.until(numberOfWindowsToBe(2));

//Loop through until we find a new window handle and switch to it
        for (String windowHandle : getDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                getDriver().switchTo().window(windowHandle);
                break;
            }
        }

//Wait for the new tab to finish loading content
        wait.until(urlToBe("https://home.openweathermap.org/questions"));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(
                By.xpath("//input[@id='question_form_email']")).sendKeys(email);

//        Select select = new Select(getDriver().findElement(
//                By.xpath("//select[@id='question_form_subject']")));
//        select.selectByValue("Sales");

        WebElement enterSubject = getDriver().findElement(By.xpath(
                "//select[@class='form-control select required']"));

        enterSubject.click();

        Thread.sleep(1000);

        enterSubject.sendKeys("Other");

        Thread.sleep(4000);

        getDriver().findElement(
                By.xpath("//textarea[@id='question_form_message']")).sendKeys(message);

        Thread.sleep(2000);
// моё
//        getDriver().switchTo().frame(getDriver().findElement(By.xpath("//iframe[@title='reCAPTCHA']")));
//        getDriver().findElement(
//                By.xpath("//span[@id='recaptcha-anchor']")).click();
// из интернета
//        new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
//                By.xpath("//iframe[starts-with(@name, 'a-') and starts-with(@src, 'https://www.google.com/recaptcha')]")));
//        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(
//                By.cssSelector("div.recaptcha-checkbox-checkmark"))).click();

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                By.xpath("//iframe[starts-with(@name, 'a-') and starts-with(@src, 'https://www.google.com/recaptcha')]")));

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='recaptcha-checkbox-border']"))).click();
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().switchTo().defaultContent();

        Thread.sleep(2000);

        getDriver().findElement(
                By.xpath("//input[@type='submit'][@name='commit']")).click();

        Thread.sleep(2000);

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//span[@class='help-block']")).getText(), "can't be blank");
    }

    @Test
    public void test7Openweathermap_unisFC_gdiksanov() {

        String url = "https://openweathermap.org/";
        WebDriverWait wait = new WebDriverWait(getDriver(), 10); // можно назначить один раз за тест или класс

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getDriver().get(url);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(By.xpath("//div[@class='option'][contains (text(),'Metric: °C, m/s')]")).click();

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getDriver().get(url);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement text = getDriver().findElement(By.xpath("//span[@class='heading']"));
        char currentUnit = text.getText().charAt(text.getText().length() - 1);

        Assert.assertEquals(currentUnit, 'C');
    }

    @Test
    public void test8Openweathermap_logo_gdiksanov() {

        String url = "https://openweathermap.org/";
        WebDriverWait wait = new WebDriverWait(getDriver(), 10); // можно назначить один раз за тест или класс

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getDriver().get(url);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        url = getDriver().getCurrentUrl();

        getDriver().findElement(By.xpath("//ul[@id='first-level-nav']/li/a/img")).click();

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        Assert.assertTrue(getDriver().getCurrentUrl().equals(url));
    }

    @Test
    public void test9Openweathermap_searchRome_gdiksanov() {

        String url = "https://openweathermap.org/";
        WebDriverWait wait = new WebDriverWait(getDriver(), 10); // можно назначить один раз за тест или класс

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getDriver().get(url);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(By.xpath("//div[@id='desktop-menu']//input[@name='q']")).sendKeys("Rome", Keys.RETURN);

        Assert.assertTrue(getDriver().getCurrentUrl().contains("find") && getDriver().getCurrentUrl().contains("Rome"));
        Assert.assertTrue(getDriver().findElement(By.xpath("//input[@id='search_str']")).getAttribute("value").equals("Rome"));
    }

    @Test
    public void test10Openweathermap_APIButton_gdiksanov() {

        String url = "https://openweathermap.org/";
        WebDriverWait wait = new WebDriverWait(getDriver(), 10); // можно назначить один раз за тест или класс

        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getDriver().get(url);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='owm-loader-container']/div")));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getDriver().findElement(By.xpath("//div[@id='desktop-menu']//a[@href='/api']")).click();

//        List<WebElement> buttons = getDriver().findElements(By.xpath("//a[contains(@class,'orange')]"));
//
//        int result = 0;
////
////        for (WebElement element: buttons
////             ) {
////        result++;
////        }
//        result = buttons.size();
//
//        Assert.assertEquals(result, 30);

        Assert.assertEquals(getDriver().findElements(By.xpath("//a[contains(@class,'orange')]")).size(), 30);
    }
}
