package tools;

import java.util.Calendar;
/**
 * Created by Przemo on 2015-02-28.
 */
public class Tools {
// pon = 1; wt = 2; ...; sun = 7;
    public static int getNowDayofWeekNumber(){
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (day == 0) { day = 7; }
        return day;
    }

    public static int getWorkoutDayOfWeekNumber() {
        int td = Tools.getNowDayofWeekNumber();
        return  (td + 5) % 7;
    }

    public static boolean isAfterTuesday () {
        if (Tools.getNowDayofWeekNumber() > 2) {
            return true;
        } else {
            return false;
        }
    }
    public static int getTrValue() {
        return Tools.getWorkoutDayOfWeekNumber() + 1;
    }

    public static int getTdValue() throws NumberFormatException {
        return Integer.parseInt(AppProperties.get(String.valueOf(Tools.getWorkoutDayOfWeekNumber())));
    }

    public static String getLogin() {
        return String.valueOf(AppProperties.get("login"));
    }
    public static String getPassword() {
        return String.valueOf(AppProperties.get("password"));
    }
    public static String getPattern() { return String.valueOf(AppProperties.get("pattern")); }
}
