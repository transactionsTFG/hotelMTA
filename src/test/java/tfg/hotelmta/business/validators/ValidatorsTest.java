package tfg.hotelmta.business.validators;

import org.junit.Assert;
import org.junit.Test;

public class ValidatorsTest {

    @Test public void testIsDate() {
        Assert.assertTrue("", Validator.isDate("01-01-2025"));
    }
    
}
