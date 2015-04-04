package tools;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

/**
 * Created by przem on 2015-03-30.
 */
public class ToolsTest {

    @Test
    public void testGiveDayOdWeekNumberShouldBeInRange() {
        Assert.assertThat("Grater than 0", Tools.getNowDayOfWeekNumber(), greaterThan(0));
        Assert.assertThat("Lower than 7", Tools.getNowDayOfWeekNumber(), lessThan(8));
    }
}
