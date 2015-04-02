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
    private String LOGIN;
    protected String PASSWORD;

    public Befit() throws Exception {

        TD=Tools.getTdValue();
        TR=Tools.getTrValue();
        LOGIN=Tools.getLogin();
        PASSWORD=Tools.getPassword();

        driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void signToWorkout() throws Exception {
        loginAndOpenCalendarPage();
        clickNextTabAfterTuesday();
        clickOnWorkout(TD, TR);
           if (isWorkoutStatusFree(getMessageFromEventContent())) {
                submitWorkout();
            } else {
                log.info("Status: " + getMessageFromEventContent());
            }
    }

    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }

    private void clickNextTabAfterTuesday () throws NoSuchElementException {
        if (Tools.isAfterTuesday()) {
            driver.findElement(By.xpath("//*[@id=\"innercontainer\"]/div/div[2]/section[2]/div[6]/a[2]")).click();
        }
    }

    private void loginAndOpenCalendarPage() throws NoSuchElementException {

        driver.get("https://befit-cms.efitness.com.pl/kalendarz-zajec");
        driver.findElement(By.id("loginmenu_log_in")).click();
        driver.findElement(By.id("Login")).clear();
        driver.findElement(By.id("Login")).sendKeys(LOGIN);
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(PASSWORD);
        driver.findElement(By.id("SubmitCredentials")).click();
    }

    private void clickOnWorkout (int td, int tr) throws NoSuchElementException {
        driver.findElement(By.xpath("//*[@id=\"scheduler\"]/div[1]/table/tbody/tr[" + td + "]/td[" + tr + "]/div/p[2]")).click();
    }

    private void submitWorkout() throws Exception {
        driver.findElement(By.xpath("//*[@id=\"calendar-register-for-class\"]")).click();
        Status.setSigned(true);
        log.info("Workout signed! :)");
    }

    protected boolean isWorkoutStatusFree(String message) {
        if(message.equals("ZAPISZ SIĘ »")){
            return true;
        } else {
            return false;
        }
    }

    private String getMessageFromEventContent() throws Exception {
        String message = driver.findElement(By.xpath("//*[@id=\"OverlayEventContent\"]/div/div[1]")).getText();
        return message.substring(message.lastIndexOf("\n")).replace("\n", "");
    }

}
