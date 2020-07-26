package by.halatsevich.storage.model.dao;

import by.halatsevich.storage.exception.DaoException;
import by.halatsevich.storage.model.entity.Book;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BookStorageDao {
    Logger logger = LogManager.getLogger();

    int addBook(Book book) throws DaoException;

    int removeBookById(long id) throws DaoException;

    int removeBooksByName(String name) throws DaoException;

    List<Book> selectAll() throws DaoException;

    Book findBookById(long id) throws DaoException;

    List<Book> findBooksByName(String name) throws DaoException;

    List<Book> findBooksByAuthors(List<String> authors) throws DaoException;

    List<Book> findBooksByCountOfPages(int pages) throws DaoException;

    List<Book> findBooksByPrice(double price) throws DaoException;

    int updateBook(Book book) throws DaoException;

    default void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error while closing statement", e);
        }
    }

    default void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error while closing ResultSet", e);
        }
    }
}
