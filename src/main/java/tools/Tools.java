package tools;

import java.io.IOException;
import java.util.Calendar;
/**
 * Created by Przemo on 2015-02-28.
 */
public class Tools {
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

    public static int getTdValue() {
        return Tools.getWorkoutDayOfWeekNumber();
    }

    public static String getWorkoutData() throws IOException {
        return AppProperties.get(String.valueOf(Tools.getWorkoutDayOfWeekNumber()));
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

    public static String getPatternStatus() throws Exception {
        return String.valueOf(AppProperties.get("patternStatusReset"));
    }
}
