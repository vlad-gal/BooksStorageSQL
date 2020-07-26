package by.halatsevich.storage.model.service.impl;

import by.halatsevich.storage.exception.DaoException;
import by.halatsevich.storage.exception.ServiceException;
import by.halatsevich.storage.model.creator.BookCreator;
import by.halatsevich.storage.model.dao.BookStorageDao;
import by.halatsevich.storage.model.dao.impl.BookStorageDaoImpl;
import by.halatsevich.storage.model.entity.Book;
import by.halatsevich.storage.model.service.BookService;
import by.halatsevich.storage.model.validator.BookValidator;
import by.halatsevich.storage.parameter.BookParameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BookServiceImpl implements BookService {
    private static BookServiceImpl instance;

    private BookServiceImpl() {
    }

    public static BookServiceImpl getInstance() {
        if (instance == null) {
            return new BookServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addBook(Map<String, String> bookParameters) throws ServiceException {
        if (bookParameters == null) {
            throw new ServiceException("Parameters of the book are null");
        }
        String name = bookParameters.get(BookParameter.NAME);
        String authors = bookParameters.get(BookParameter.AUTHORS);
        String pages = bookParameters.get(BookParameter.PAGES);
        String price = bookParameters.get(BookParameter.PRICE);
        if (!BookValidator.isValidName(name)) {
            throw new ServiceException("Name is empty or has invalid characters");
        }
        if (!BookValidator.isValidAuthors(authors)) {
            throw new ServiceException("Authors is empty or has invalid characters");
        }
        if (!BookValidator.isValidPages(pages)) {
            throw new ServiceException("Count of page of the book is less than 1");
        }
        if (!BookValidator.isValidPrice(price)) {
            throw new ServiceException("Price of the book is less than 0.1");
        }
        BookCreator creator = new BookCreator();
        Book book = creator.createBook(name, authors, price, pages);
        BookStorageDao bookStorageDao = BookStorageDaoImpl.getInstance();
        boolean result = false;
        try {
            int changes = bookStorageDao.addBook(book);
            if (changes > 0) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean removeBookById(String id) throws ServiceException {
        if (!BookValidator.isValidId(id)) {
            throw new ServiceException("Id is less than 1");
        }
        long parsedId = Long.parseLong(id);
        BookStorageDao bookStorageDao = BookStorageDaoImpl.getInstance();
        boolean result = false;
        try {
            int changes = bookStorageDao.removeBookById(parsedId);
            if (changes > 0) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean removeBooksByName(String name) throws ServiceException {
        if (!BookValidator.isValidName(name)) {
            throw new ServiceException("Name is empty or has invalid characters");
        }
        BookStorageDao bookStorageDao = BookStorageDaoImpl.getInstance();
        boolean result = false;
        try {
            int changes = bookStorageDao.removeBooksByName(name);
            if (changes > 0) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Book> selectAllBooks() throws ServiceException {
        BookStorageDao bookStorageDao = BookStorageDaoImpl.getInstance();
        List<Book> books;
        try {
            books = bookStorageDao.selectAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (books.isEmpty()) {
            throw new ServiceException("There are no books in the table");
        }
        return books;
    }

    @Override
    public Book findBookById(String id) throws ServiceException {
        if (!BookValidator.isValidId(id)) {
            throw new ServiceException("Id is less than 1");
        }
        long parsedId = Long.parseLong(id);
        BookStorageDao bookStorageDao = BookStorageDaoImpl.getInstance();
        Book foundBook;
        try {
            foundBook = bookStorageDao.findBookById(parsedId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (foundBook == null) {
            throw new ServiceException("Book was not found in the table");
        }
        return foundBook;
    }

    @Override
    public List<Book> findBooksByName(String bookName) throws ServiceException {
        if (!BookValidator.isValidName(bookName)) {
            throw new ServiceException("Name is empty or has invalid characters");
        }
        BookStorageDao bookStorageDao = BookStorageDaoImpl.getInstance();
        List<Book> books;
        try {
            books = bookStorageDao.findBooksByName(bookName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (books.isEmpty()) {
            throw new ServiceException("Books were not found in the table");
        }
        return books;
    }

    @Override
    public List<Book> findBooksByAuthors(String authors) throws ServiceException {
        if (!BookValidator.isValidAuthors(authors)) {
            throw new ServiceException("Authors is empty or has invalid characters");
        }
        String[] splitAuthors = authors.split(BookParameter.REGEX_DELIMITER);
        List<String> listAuthors = new ArrayList<>();
        Collections.addAll(listAuthors, splitAuthors);
        BookStorageDao bookStorageDao = BookStorageDaoImpl.getInstance();
        List<Book> books;
        try {
            books = bookStorageDao.findBooksByAuthors(listAuthors);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (books.isEmpty()) {
            throw new ServiceException("Books were not found in the table");
        }
        return books;
    }

    @Override
    public List<Book> findBooksByCountOfPages(String pages) throws ServiceException {
        if (!BookValidator.isValidPages(pages)) {
            throw new ServiceException("Count of page of the book is less than 1");
        }
        int parsedPages = Integer.parseInt(pages);
        BookStorageDao bookStorageDao = BookStorageDaoImpl.getInstance();
        List<Book> books;
        try {
            books = bookStorageDao.findBooksByCountOfPages(parsedPages);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (books.isEmpty()) {
            throw new ServiceException("Books were not found in the table");
        }
        return books;
    }

    @Override
    public List<Book> findBooksByPrice(String price) throws ServiceException {
        if (!BookValidator.isValidPrice(price)) {
            throw new ServiceException("Price of the book is less than 0.1");
        }
        double parsedPrice = Double.parseDouble(price);
        BookStorageDao bookStorageDao = BookStorageDaoImpl.getInstance();
        List<Book> books;
        try {
            books = bookStorageDao.findBooksByPrice(parsedPrice);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (books.isEmpty()) {
            throw new ServiceException("Books were not found in the table");
        }
        return books;
    }

    @Override
    public boolean updateBook(Map<String, String> bookParameters) throws ServiceException {
        if (bookParameters == null) {
            throw new ServiceException("Parameters of the book are null");
        }
        String id = bookParameters.get(BookParameter.ID);
        String name = bookParameters.get(BookParameter.NAME);
        String authors = bookParameters.get(BookParameter.AUTHORS);
        String pages = bookParameters.get(BookParameter.PAGES);
        String price = bookParameters.get(BookParameter.PRICE);
        if (!BookValidator.isValidId(id)) {
            throw new ServiceException("Id is less than 1");
        }
        if (!BookValidator.isValidName(name)) {
            throw new ServiceException("Name is empty or has invalid characters");
        }
        if (!BookValidator.isValidAuthors(authors)) {
            throw new ServiceException("Authors is empty or has invalid characters");
        }
        if (!BookValidator.isValidPages(pages)) {
            throw new ServiceException("Count of page of the book is less than 1");
        }
        if (!BookValidator.isValidPrice(price)) {
            throw new ServiceException("Price of the book is less than 0.1");
        }
        BookCreator creator = new BookCreator();
        Book book = creator.createBook(id, name, authors, price, pages);
        BookStorageDao bookStorageDao = BookStorageDaoImpl.getInstance();
        boolean result = false;
        try {
            int changes = bookStorageDao.updateBook(book);
            if (changes > 0) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
