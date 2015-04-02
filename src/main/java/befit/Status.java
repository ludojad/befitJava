package befit;

/**
 * Created by przem on 2015-04-02.
 */
public class Status {
    public static boolean signed =false;

    public static boolean isSigned() {
        return signed;
    }

    public static void setSigned(boolean signed) {
        Status.signed = signed;
    }
}
