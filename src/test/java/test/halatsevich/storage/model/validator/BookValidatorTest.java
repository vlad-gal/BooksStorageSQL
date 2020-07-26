package test.halatsevich.storage.model.validator;

import by.halatsevich.storage.model.validator.BookValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BookValidatorTest {

    @Test
    public void testIsValidIdSuccess() {
        boolean condition = BookValidator.isValidId("1");
        assertTrue(condition);
    }

    @Test
    public void testIsValidIdFailure() {
        boolean condition = BookValidator.isValidId("-7789");
        assertFalse(condition);
    }

    @Test
    public void testIsValidNameSuccess() {
        boolean condition = BookValidator.isValidName("hello");
        assertTrue(condition);
    }

    @Test
    public void testIsValidNameFailure() {
        boolean condition = BookValidator.isValidName("H q   ");
        assertFalse(condition);
    }

    @Test
    public void testIsValidPagesSuccess() {
        boolean condition = BookValidator.isValidPages("22");
        assertTrue(condition);
    }

    @Test
    public void testIsValidPagesFailure() {
        boolean condition = BookValidator.isValidPages("-72");
        assertFalse(condition);
    }

    @Test
    public void testIsValidPriceSuccess() {
        boolean condition = BookValidator.isValidPrice("785.3");
        assertTrue(condition);
    }

    @Test
    public void testIsValidPriceFailure() {
        boolean condition = BookValidator.isValidPrice("0.0");
        assertFalse(condition);
    }

    @Test
    public void testIsValidAuthorsSuccess() {
        boolean condition = BookValidator.isValidAuthors("D. A. Run, Q.A. Bun");
        assertTrue(condition);
    }

    @Test
    public void testIsValidAuthorsFailure() {
        boolean condition = BookValidator.isValidAuthors("jhghjkl");
        assertFalse(condition);
    }
}