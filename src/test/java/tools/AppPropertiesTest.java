package tools;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static com.jcabi.matchers.RegexMatchers.matchesPattern;
/**
 * Created by przem on 2015-03-30.
 */
public class AppPropertiesTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenLoginShouldBeNumeric () throws Exception {
        String login = AppProperties.get("login");
        Assert.assertThat("Login should not be null value", login, notNullValue());
        Assert.assertThat("login should contains only numbers", login, matchesPattern("^[0-9]+$"));
    }

    @Test
    public void givenPasswordShouldNotBeNull () throws Exception {
        String password = AppProperties.get("password");
        Assert.assertThat("Password should not be null value", password, notNullValue());
        Assert.assertThat("Password should not be empty value", password, not(""));
    }

    @Test
    public void givenPatternShouldNotBeNull () throws Exception {
        String pattern = AppProperties.get("pattern");
        Assert.assertThat("Password should not be null value", pattern, notNullValue());
        Assert.assertThat("Password should not be empty value", pattern, not(""));

    }

    @Test
    public void trowExceptionWhenNumericKeyNotFound () throws Exception {
        thrown.expect(IllegalArgumentException.class);
        AppProperties.get("9");
    }
}
