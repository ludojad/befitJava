package tools;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

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
    }

    @Test
    public void givenPatternShouldNotBeNull () throws Exception {
        String pattern = AppProperties.get("pattern");
        Assert.assertThat("Password should not be null value", pattern, notNullValue());
    }

    @Test
    public void trowExceptionWhenKeyNotFound () throws Exception {
        thrown.expect(IllegalArgumentException.class);
        AppProperties.get("Key that not exist!");
    }
}
