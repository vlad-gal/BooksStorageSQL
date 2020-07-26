package test.halatsevich.storage.model.service.impl;

import by.halatsevich.storage.exception.ServiceException;
import by.halatsevich.storage.model.entity.Book;
import by.halatsevich.storage.model.service.impl.BookServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class BookServiceImplTest {
    BookServiceImpl service = null;
    Book book1 = new Book(61, "Планирование в аудите", Arrays.asList("С.М. Бычкова", "А.В. Газорян"), 293, 594);
    Book book2 = new Book(62, "How to create furnace", Arrays.asList("D.D. Pechkin"), 241, 1.2);
    Book book3 = new Book(63, "How to create russian furnace", Arrays.asList("D.D. Pechkin", "I.I. Ivanov"), 441, 54);
    Map<String, String> bookParameters = new HashMap<>();

    @BeforeMethod
    public void setUp() {
        service = BookServiceImpl.getInstance();
        bookParameters.put("id", "1");
        bookParameters.put("name", "How to built smth");
        bookParameters.put("authors", "D.D. Pechkin");
        bookParameters.put("pages", "7843");
        bookParameters.put("price", "78.43");
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Parameters of the book are null")
    public void testAddBookIntoStorageNullDataExceptionMessage() throws ServiceException {
        service.addBook(null);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Name is empty or has invalid characters")
    public void testAddBookIntoStorageInvalidNameExceptionMessage() throws ServiceException {
        bookParameters.put("name", "");
        service.addBook(bookParameters);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Authors is empty or has invalid characters")
    public void testAddBookIntoStorageInvalidAuthorsExceptionMessage() throws ServiceException {
        bookParameters.put("authors", "asdqweqw");
        service.addBook(bookParameters);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Count of page of the book is less than 1")
    public void testAddBookIntoStorageInvalidPagesExceptionMessage() throws ServiceException {
        bookParameters.put("pages", "0");
        service.addBook(bookParameters);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Price of the book is less than 0.1")
    public void testAddBookIntoStorageInvalidPriceExceptionMessage() throws ServiceException {
        bookParameters.put("price", "0");
        service.addBook(bookParameters);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Id is less than 1")
    public void testRemoveBookFromStorageByIdExceptionMessage() throws ServiceException {
        service.removeBookById("-14");
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Name is empty or has invalid characters")
    public void testRemoveBooksFromStorageByNameInvalidNameExceptionMessage() throws ServiceException {
        service.removeBooksByName("");
    }

    @Test
    public void testSelectAllBooksFromStorageSuccess() throws ServiceException {
        List<Book> actual = service.selectAllBooks();
        List<Book> expected = Arrays.asList(book1, book2, book3);
        assertEquals(actual, expected);
    }

    @Test
    public void testSelectAllBooksFromStorageFailure() throws ServiceException {
        List<Book> actual = service.selectAllBooks();
        List<Book> expected = Arrays.asList(book1);
        assertNotEquals(actual, expected);
    }

    @Test
    public void testFindBookByIdIntoStorageSuccess() throws ServiceException {
        Book actual = service.findBookById("61");
        assertEquals(actual, book1);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Id is less than 1")
    public void testFindBookByIdIntoStorageInvalidIdExceptionMessage() throws ServiceException {
        service.findBookById("-1");
    }

    @Test
    public void testFindBooksByNameIntoStorageSuccess() throws ServiceException {
        List<Book> actual = service.findBooksByName("Планирование в аудите");
        List<Book> expected = Arrays.asList(book1);
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Name is empty or has invalid characters")
    public void testFindBooksByNameIntoStorageInvalidNameExceptionMessage() throws ServiceException {
        service.findBooksByName("");
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Books were not found in the table")
    public void testFindBooksByNameIntoStorageNotFoundExceptionMessage() throws ServiceException {
        service.findBooksByName("Books");
    }

    @Test
    public void testFindBooksByAuthorsIntoStorageSuccess() throws ServiceException {
        List<Book> actual = service.findBooksByAuthors("D.D. Pechkin");
        List<Book> expected = Arrays.asList(book2);
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Authors is empty or has invalid characters")
    public void testFindBooksByAuthorsIntoStorageInvalidAuthorsExceptionMessage() throws ServiceException {
        service.findBooksByAuthors("as.qweq2");
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Books were not found in the table")
    public void testFindBooksByAuthorsIntoStorageNotFoundExceptionMessage() throws ServiceException {
        service.findBooksByAuthors("D.A. G");
    }

    @Test
    public void testFindBooksByCountOfPagesIntoStorageSuccess() throws ServiceException {
        List<Book> actual = service.findBooksByCountOfPages("241");
        List<Book> expected = Arrays.asList(book2);
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Count of page of the book is less than 1")
    public void testFindBooksByCountOfPagesIntoStorageInvalidPagesExceptionMessage() throws ServiceException {
        service.findBooksByCountOfPages("-6");
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Books were not found in the table")
    public void testFindBooksByCountOfPagesIntoStorageNotFoundExceptionMessage() throws ServiceException {
        service.findBooksByCountOfPages("778");
    }

    @Test
    public void testFindBooksByPriceIntoStorageSuccess() throws ServiceException {
        List<Book> actual = service.findBooksByPrice("1.2");
        List<Book> expected = Arrays.asList(book2);
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Price of the book is less than 0.1")
    public void testFindBooksByPriceIntoStorageInvalidPriceExceptionMessage() throws ServiceException {
        service.findBooksByPrice("0");
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Books were not found in the table")
    public void testFindBooksByPriceIntoStorageNotFoundExceptionMessage() throws ServiceException {
        service.findBooksByPrice("77521.2");
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Parameters of the book are null")
    public void testUpdateBookIntoStorageNullDataExceptionMessage() throws ServiceException {
        service.updateBook(null);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Id is less than 1")
    public void testUpdateBookIntoStorageInvalidIdExceptionMessage() throws ServiceException {
        bookParameters.put("id", "-122");
        service.updateBook(bookParameters);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Name is empty or has invalid characters")
    public void testUpdateBookIntoStorageInvalidNameExceptionMessage() throws ServiceException {
        bookParameters.put("name", "");
        service.updateBook(bookParameters);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Authors is empty or has invalid characters")
    public void testUpdateBookIntoStorageInvalidAuthorsExceptionMessage() throws ServiceException {
        bookParameters.put("authors", "asdqweqw");
        service.updateBook(bookParameters);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Count of page of the book is less than 1")
    public void testUpdateBookIntoStorageInvalidPagesExceptionMessage() throws ServiceException {
        bookParameters.put("pages", "0");
        service.updateBook(bookParameters);
    }

    @Test(expectedExceptions = ServiceException.class,
            expectedExceptionsMessageRegExp = "Price of the book is less than 0.1")
    public void testUpdateBookIntoStorageInvalidPriceExceptionMessage() throws ServiceException {
        bookParameters.put("price", "0");
        service.updateBook(bookParameters);
    }
}