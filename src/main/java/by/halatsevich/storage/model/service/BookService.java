package by.halatsevich.storage.model.service;

import by.halatsevich.storage.exception.ServiceException;
import by.halatsevich.storage.model.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    boolean addBook(Map<String, String> bookParameters) throws ServiceException;

    boolean removeBookById(String id) throws ServiceException;

    boolean removeBooksByName(String name) throws ServiceException;

    List<Book> selectAllBooks() throws ServiceException;

    Book findBookById(String id) throws ServiceException;

    List<Book> findBooksByName(String bookName) throws ServiceException;

    List<Book> findBooksByAuthors(String authors) throws ServiceException;

    List<Book> findBooksByCountOfPages(String pages) throws ServiceException;

    List<Book> findBooksByPrice(String price) throws ServiceException;

    boolean updateBook(Map<String, String> bookParameters) throws ServiceException;
}
