package befit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;
import tools.Tools;
/**
 * Created by Przemo on 2015-02-28.
 */
public class Befit {
    final static Logger log = Logger.getLogger(Befit.class);
    private WebDriver driver;
    private int TD;
    private int TR;

    public void setUp() throws Exception {
        try {
            TD=Tools.getTdValue();
            TR=Tools.getTrValue();

            driver = new FirefoxDriver();
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        } catch (Exception e) {
            log.fatal("Unable to initialize FF driver: " + e);
        }
    }

    public void testCase() throws Exception {
        try {
            loginAndOpenCalendarPage();
            clickNextTabAfterTuesday();
            clickOnWorkout(TD, TR);
           if (isWorkoutStatusFree(getMessageFromEventContent())) {
                submitWorkout();
            } else {
                log.info("Status: " + getMessageFromEventContent());
            }
        } catch (NoSuchElementException e) {
            log.warn("Element for given parameters not found");
        } catch (Exception e) {
            log.error("Error in testCase: " + e);
        }
    }

    public void tearDown() {
        try {
            driver.close();
            driver.quit();
        } catch (Exception e) {
            log.fatal("Unable to tearDown driver: " + e);
        }
    }

    protected boolean isWorkoutStatusFree(String message) {
        if(message.equals("ZAPISZ SIĘ »")){
            return true;
        } else {
            return false;
        }
    }

    private void clickNextTabAfterTuesday () throws NoSuchElementException {
        if (Tools.isAfterTuesday()) {
            driver.findElement(By.xpath("//*[@id=\"innercontainer\"]/div/div[2]/section[2]/div[6]/a[2]")).click();
        }
    }

    private void loginAndOpenCalendarPage() throws Exception {

        driver.get("https://befit-cms.efitness.com.pl/kalendarz-zajec");
        driver.findElement(By.id("loginmenu_log_in")).click();
        driver.findElement(By.id("Login")).clear();
        driver.findElement(By.id("Login")).sendKeys(Tools.getLogin());
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(Tools.getPassword());
        driver.findElement(By.id("SubmitCredentials")).click();
    }

    private void clickOnWorkout (int td, int tr) throws NoSuchElementException {
        driver.findElement(By.xpath("//*[@id=\"scheduler\"]/div[1]/table/tbody/tr[" + td + "]/td[" + tr + "]/div/p[2]")).click();
    }

    private String getMessageFromEventContent() throws Exception {
        String message = driver.findElement(By.xpath("//*[@id=\"OverlayEventContent\"]/div/div[1]")).getText();
        return message.substring(message.lastIndexOf("\n")).replace("\n", "");
    }

    private void submitWorkout() throws Exception {
        driver.findElement(By.xpath("//*[@id=\"calendar-register-for-class\"]")).click();
        log.info("Workout signed! :)");
    }
}
