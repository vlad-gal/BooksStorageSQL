package test.halatsevich.storage.model.dao.impl;

import by.halatsevich.storage.exception.DaoException;
import by.halatsevich.storage.model.dao.BookStorageDao;
import by.halatsevich.storage.model.dao.impl.BookStorageDaoImpl;
import by.halatsevich.storage.model.entity.Book;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class BookStorageDaoImplTest {
    BookStorageDao storageDao;
    Book book1 = new Book(61, "Планирование в аудите", Arrays.asList("С.М. Бычкова", "А.В. Газорян"), 293, 594);
    Book book2 = new Book(62, "How to create furnace", Arrays.asList("D.D. Pechkin"), 241, 1.2);
    Book book3 = new Book(63, "How to create russian furnace", Arrays.asList("D.D. Pechkin", "I.I. Ivanov"), 441, 54);

    @BeforeMethod
    public void setUp() {
        storageDao = BookStorageDaoImpl.getInstance();
    }

    @Test
    public void testSelectAllSuccess() throws DaoException {
        List<Book> actual = storageDao.selectAll();
        List<Book> expected = Arrays.asList(book1, book2, book3);
        assertEquals(actual, expected);
    }

    @Test
    public void testSelectAllFailure() throws DaoException {
        List<Book> actual = storageDao.selectAll();
        List<Book> expected = Arrays.asList(book1, book2);
        assertNotEquals(actual, expected);
    }

    @Test
    public void testFindBookByIdSuccess() throws DaoException {
        Book actual = storageDao.findBookById(61);
        assertEquals(actual, book1);
    }

    @Test
    public void testFindBookByIdFailure() throws DaoException {
        Book actual = storageDao.findBookById(1000);
        Book expected = null;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindBooksByNameSuccess() throws DaoException {
        List<Book> actual = storageDao.findBooksByName("Планирование в аудите");
        List<Book> expected = Arrays.asList(book1);
        assertEquals(actual, expected);
    }

    @Test
    public void testFindBooksByNameFailure() throws DaoException {
        List<Book> actual = storageDao.findBooksByName("Планирование в аудите");
        List<Book> expected = Arrays.asList(book3);
        assertNotEquals(actual, expected);
    }

    @Test
    public void testFindBooksByAuthorsSuccess() throws DaoException {
        List<Book> actual = storageDao.findBooksByAuthors(Arrays.asList("С.М. Бычкова", "А.В. Газорян"));
        List<Book> expected = Arrays.asList(book1);
        assertEquals(actual, expected);
    }

    @Test
    public void testFindBooksByAuthorsFailure() throws DaoException {
        List<Book> actual = storageDao.findBooksByAuthors(Arrays.asList("Author 1", "Author 2"));
        List<Book> expected = Arrays.asList(book1);
        assertNotEquals(actual, expected);
    }

    @Test
    public void testFindBooksByCountOfPagesSuccess() throws DaoException {
        List<Book> actual = storageDao.findBooksByCountOfPages(241);
        List<Book> expected = Arrays.asList(book2);
        assertEquals(actual, expected);
    }

    @Test
    public void testFindBooksByCountOfPagesFailure() throws DaoException {
        List<Book> actual = storageDao.findBooksByCountOfPages(111);
        List<Book> expected = Arrays.asList(book1, book2);
        assertNotEquals(actual, expected);
    }

    @Test
    public void testFindBooksByPriceSuccess() throws DaoException {
        List<Book> actual = storageDao.findBooksByPrice(54);
        List<Book> expected = Arrays.asList(book3);
        assertEquals(actual, expected);
    }

    @Test
    public void testFindBooksByPriceFailure() throws DaoException {
        List<Book> actual = storageDao.findBooksByPrice(13.1);
        List<Book> expected = Arrays.asList(book1);
        assertNotEquals(actual, expected);
    }
}