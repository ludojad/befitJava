package befit;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;

/**
 * Created by Przemo on 2015-03-04.
 */
public class RunBefit {
    final static Logger log = Logger.getLogger(RunBefit.class);
    public static void run() throws Exception {
        Befit befit = new Befit();
        try {
            befit.signToWorkout();
        } catch (NoSuchElementException e) {
            log.warn("Page element not found");
        } catch (Exception e) {
            log.error("Exception: ", e);
        } finally {
            befit.tearDown();
        }
    }
}