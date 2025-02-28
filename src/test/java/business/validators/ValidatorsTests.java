// package business.validators;

// import static org.junit.Assert.assertFalse;
// import static org.junit.Assert.assertTrue;

// import org.junit.Test;

// import common.validators.Validator;

// public class ValidatorsTests {

//     @Test
//     public void testIsDate() {
//         assertTrue(Validator.isDate("01-01-2025"));
//         assertFalse(Validator.isDate(""));
//         assertFalse(Validator.isDate("2025-01-01"));
//         assertFalse(Validator.isDate("01-01-25"));
//     }

//     @Test
//     public void testIsEmail() {
//         assertTrue(Validator.isEmail("email@domain.com"));
//         assertTrue(Validator.isEmail("email@gmail.com"));
//         assertTrue(Validator.isEmail("email@ucm.es"));
//         assertFalse(Validator.isEmail(""));
//         assertFalse(Validator.isEmail("@ucm.es"));
//         assertFalse(Validator.isEmail("@gmail.com"));
//         assertFalse(Validator.isEmail("gmail@com"));
//         assertFalse(Validator.isEmail("gmail@"));
//         assertFalse(Validator.isEmail("gmail@."));
//     }

//     @Test
//     public void testIsPhone() {
//         assertTrue(Validator.isPhone("123456789"));
//         assertTrue(Validator.isPhone("987654321"));
//         assertFalse(Validator.isPhone(""));
//         assertFalse(Validator.isPhone("12345678"));
//         assertFalse(Validator.isPhone("hello"));
//         assertFalse(Validator.isPhone("hello@gmail.com"));
//     }

//     @Test
//     public void testIsDni() {
//         assertTrue(Validator.isDni("12345678A"));
//         assertTrue(Validator.isDni("12345678B"));
//         assertFalse(Validator.isDni("12345678c"));
//         assertFalse(Validator.isDni("123456"));
//         assertFalse(Validator.isDni("c123456"));
//         assertFalse(Validator.isDni("alksjfd"));
//     }

//     @Test
//     public void testIsName() {
//         assertTrue(Validator.isName("name"));
//         assertTrue(Validator.isName("name name"));
//         assertFalse(Validator.isName(""));
//         assertFalse(Validator.isName("45"));
//     }

//     // @Test
//     // public void testHasSQLInjection() {
//     //     assertTrue(false);
//     // }
// }
