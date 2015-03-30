package befit;

import org.apache.log4j.Logger;

/**
 * Created by Przemo on 2015-03-04.
 */
public class RunBefit {
    final static Logger log = Logger.getLogger(RunBefit.class);
    public static void run() {
        Befit befit = new Befit();
        try {
            befit.setUp();
            befit.testCase();
        } catch (NumberFormatException e) {
            log.warn("Traing day not found, nothing to sign...");
        } catch (Exception e) {
            log.error("Exception: ", e);
        } finally {
            befit.tearDown();
        }
    }
}
