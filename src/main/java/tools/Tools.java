package tools;

import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.Calendar;
/**
 * Created by Przemo on 2015-02-28.
 */
public class Tools {
    final static Logger log = Logger.getLogger(tools.Tools.class);
    // pon = 1; wt = 2; ...; sun = 7;
    public static int getNowDayOfWeekNumber() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (day == 0) {
            day = 7;
        }
        return day;
    }

    public static int getWorkoutDayOfWeekNumber() {
        int td = Tools.getNowDayOfWeekNumber();
        return (td + 5) % 7;
    }

    public static boolean isAfterTuesday() {
        if (Tools.getNowDayOfWeekNumber() > 2) {
            return true;
        } else {
            return false;
        }
    }

    public static int getTrValue() {
        return Tools.getWorkoutDayOfWeekNumber() + 1;
    }

    public static int getTdValue() throws NullPointerException, IOException {
        return Integer.parseInt(AppProperties.get(String.valueOf(Tools.getWorkoutDayOfWeekNumber())));
    }

    public static String getLogin() throws Exception {
        return String.valueOf(AppProperties.get("login"));
    }

    public static String getPassword() throws Exception {
        return String.valueOf(AppProperties.get("password"));
    }

    public static String getPattern() throws Exception {
        return String.valueOf(AppProperties.get("pattern"));
    }
}
