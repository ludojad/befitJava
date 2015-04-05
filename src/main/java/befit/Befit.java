package befit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import tools.Tools;
/**
 * Created by Przemo on 2015-02-28.
 */
public class Befit {
    final static Logger log = Logger.getLogger(Befit.class);
    private int TD;
    private WebDriver driver;
    private String[] WORKOUT;
    private String LOGIN;
    protected String PASSWORD;

    public Befit() throws Exception {
        TD=Tools.getTdValue();
        LOGIN=Tools.getLogin();
        PASSWORD=Tools.getPassword();
        WORKOUT=Tools.getWorkoutData().split(",");

        driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void signToWorkout() throws Exception {
        loginAndOpenCalendarPageInListView();
        clickNextTabAfterTuesday();
        getWorkoutContainsData(WORKOUT, 3).click();
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

    public boolean isWorkoutStatusFree(String message) {
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

    private void loginAndOpenCalendarPageInListView() throws NoSuchElementException {

        driver.get("https://befit-cms.efitness.com.pl/kalendarz-zajec");
        driver.findElement(By.id("loginmenu_log_in")).click();
        driver.findElement(By.id("Login")).clear();
        driver.findElement(By.id("Login")).sendKeys(LOGIN);
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(PASSWORD);
        driver.findElement(By.id("SubmitCredentials")).click();
        driver.findElement(By.xpath("//*[@id=\"innercontainer\"]/div/div[2]/section[2]/div[5]/a[3]")).click();

    }

    private void submitWorkout() throws NoSuchElementException {
        driver.findElement(By.xpath("//*[@id=\"calendar-register-for-class\"]")).click();
        Status.setSigned(true);
        log.info("Workout signed! :)");
    }

    private String getMessageFromEventContent() throws Exception {
        String message = driver.findElement(By.xpath("//*[@id=\"OverlayEventContent\"]/div/div[1]")).getText();
        return message.substring(message.lastIndexOf("\n")).replace("\n", "");
    }

    public WebElement getWorkoutContainsData(String[] dataWorkout, int td) throws ArrayIndexOutOfBoundsException {
        if(dataWorkout.length != 2) {
            throw new IllegalArgumentException("Config value in wrong format");
        }

        List <WebElement> column = driver.findElements(By.xpath("//*[@id=\"scheduler\"]/div[1]/table/tbody/tr/td["+td+"]/div"));
        List <WebElement> workouts = new ArrayList<WebElement>();

        for (WebElement webElement: column) {
            if(webElement.getText().toLowerCase().contains(dataWorkout[0]) && webElement.getText().toLowerCase().contains(dataWorkout[1])){
                workouts.add(webElement);
                log.info(webElement.getText());
            }
        }
        return workouts.get(workouts.size()-1);
    }
}
