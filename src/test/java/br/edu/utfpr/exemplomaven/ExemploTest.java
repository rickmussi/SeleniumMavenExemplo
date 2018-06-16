package br.edu.utfpr.exemplomaven;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author andreendo
 */
public class ExemploTest {

    /**
     * Vc precisa identificar onde estah o chromedriver. Baixar de:
     * https://sites.google.com/a/chromium.org/chromedriver/downloads
     *
     * Versão utilizada do chromedriver: 2.35.528139
     */
    private static String CHROMEDRIVER_LOCATION = "c:\\wcd\\TestadorAPP\\chromedriver.exe";

    private static int scId = 0;

    WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_LOCATION);
    }

    @Before
    public void before() {
        ChromeOptions chromeOptions = new ChromeOptions();
        //Opcao headless para MacOS e Linux
        chromeOptions.addArguments("headless");
        chromeOptions.addArguments("window-size=1200x600");
        chromeOptions.addArguments("start-maximized");

        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void after() {
        driver.close();
    }

    @Ignore
    @Test
    public void testGoogleSearch() {
        driver.get("https://www.google.com.br/");
        WebElement searchInput = driver.findElement(By.name("q"));
        searchInput.sendKeys("teste de software");

        takeScreenShot();

        searchInput.submit();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until((ExpectedCondition<Boolean>) (WebDriver d) -> d.getTitle().toLowerCase().startsWith("teste"));

        takeScreenShot();

        assertTrue(driver.getTitle().startsWith("teste de software"));
    }

    @Test
    public void testRationSearch() {
        driver.get("https://ration.io");
        WebElement searchSign = driver.findElement(By.xpath("//*[@id=\"page-header\"]/div/a[2]"));
        searchSign.click();

        WebElement searchEmail = driver.findElement(By.xpath("//*[@id=\"login\"]/div/div/form/div[1]/input"));
        searchEmail.sendKeys("rickmussi@hotmail.com");

        WebElement searchSenha = driver.findElement(By.xpath("//*[@id=\"login\"]/div/div/form/div[2]/input"));
        searchSenha.sendKeys("123");

        WebElement searchBtnSign = driver.findElement(By.xpath("//*[@id=\"login\"]/div/div/form/div[4]/button"));
        searchBtnSign.click();
                                                                
        WebElement searchRetorno = driver.findElement(By.xpath("//*[@id=\"login\"]/div/div/form/p/small"));
        assertEquals(searchRetorno.getText(), "The credentials you entered are not associated with an account. Please check your email and/or password and try again.");
    }
      
    @Test
    public void testFaq() {
        driver.get("https://ration.io");
        WebElement searchFaq = driver.findElement(By.xpath("//*[@id=\"page-header\"]/div/a[1]"));
        searchFaq.click();        
        assertEquals(driver.getCurrentUrl(), "https://ration.io/faq");
    }
    
    @Test
    public void testRationTitulo() {
        driver.get("https://ration.io");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        assertTrue(driver.getTitle().equals("Ration"));
    }

    private void takeScreenShot() {
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            scId++;
            FileUtils.copyFile(sourceFile, new File("./res/" + scId + ".png"));
        } catch (IOException e) {
        }
    }
}
