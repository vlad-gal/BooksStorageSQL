package test.halatsevich.storage.model.creator;

import by.halatsevich.storage.model.creator.BookCreator;
import by.halatsevich.storage.model.entity.Book;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class BookCreatorTest {
    BookCreator creator;

    @BeforeClass
    public void setUp() {
        creator = new BookCreator();
    }

    @Test
    public void testCreateBookSuccess() {
        Book actual = creator.createBook(
                "1", "Планирование в аудите", "С.М. Бычкова, А.В. Газорян", "594.0", "293");
        Book expected = new Book(1, "Планирование в аудите",
                Arrays.asList("С.М. Бычкова", "А.В. Газорян"), 293, 594.0);
        assertEquals(actual, expected);
    }

    @Test
    public void testCreateBookFailure() {
        Book actual = creator.createBook(
                "1", "Планирование в аудите", "С.М. Бычкова, А.В. Газорян", "594.0", "293");
        Book expected = new Book("Планирование в аудите",
                Arrays.asList("С.М. Бычкова", "А.В. Газорян"), 44, 594.0);
        assertNotEquals(actual, expected);
    }
}