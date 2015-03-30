package befit;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;
import tools.Tools;
/**
 * Created by Przemo on 2015-02-28.
 */
public class Befit {
    final static Logger log = Logger.getLogger(Befit.class);
    private WebDriver driver;

    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void testCase() {

        driver.get("https://befit-cms.efitness.com.pl/kalendarz-zajec");
        driver.findElement(By.id("loginmenu_log_in")).click();
        driver.findElement(By.id("Login")).clear();
        driver.findElement(By.id("Login")).sendKeys(Tools.getLogin());
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(Tools.getPassword());
        driver.findElement(By.id("SubmitCredentials")).click();
        if(Tools.isAfterTuesday()) {
            driver.findElement(By.xpath("//*[@id=\"innercontainer\"]/div/div[2]/section[2]/div[6]/a[2]")).click();
        }
        driver.findElement(By.xpath("//*[@id=\"scheduler\"]/div[1]/table/tbody/tr[" + Tools.getTdValue() + "]/td[" + Tools.getTrValue() + "]/div/p[2]")).click();

        String message = driver.findElement(By.xpath("//*[@id=\"OverlayEventContent\"]/div/div[1]")).getText();
        message = message.substring(message.lastIndexOf("\n")).replace("\n", "");

        if(isWorkoutStatusFree(message)) {
            driver.findElement(By.xpath("//*[@id=\"calendar-register-for-class\"]")).click();
            log.info("Workout signed! :)");
        } else {
            log.info("Status: " + message);
        }
    }

    public void tearDown() {
        driver.close();
        driver.quit();
    }

    private boolean isWorkoutStatusFree(String message) {
        if(message.equals("ZAPISZ SIĘ »")){
            return true;
        } else {
            return false;
        }
    }

}
