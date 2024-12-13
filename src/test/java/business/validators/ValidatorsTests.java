package business.validators;

import org.junit.Assert;
import org.junit.Test;

public class ValidatorsTests {

    @Test
    public void testIsDate() {
        Assert.assertTrue("", Validator.isDate("01-01-2025"));
    }

    @Test
    public void testIsEmail() {
    }

    @Test
    public void testIsPhone() {
    }

    @Test
    public void testIsDni() {
    }

    @Test
    public void testHasSQLInjection() {
    }
}
