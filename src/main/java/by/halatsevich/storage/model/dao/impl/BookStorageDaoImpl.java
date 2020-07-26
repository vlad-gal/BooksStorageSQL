package by.halatsevich.storage.model.dao.impl;

import by.halatsevich.storage.exception.DaoException;
import by.halatsevich.storage.model.dao.BookStorageDao;
import by.halatsevich.storage.model.dao.query.SqlQuery;
import by.halatsevich.storage.model.entity.Book;
import by.halatsevich.storage.model.pool.ConnectionPool;
import by.halatsevich.storage.parameter.BookParameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class BookStorageDaoImpl implements BookStorageDao {
    private static BookStorageDaoImpl instance;

    private BookStorageDaoImpl() {
    }

    public static BookStorageDaoImpl getInstance() {
        if (instance == null) {
            return new BookStorageDaoImpl();
        }
        return instance;
    }

    @Override
    public int addBook(Book book) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        int changes;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SqlQuery.REQUEST_ADD_BOOK);
            statement.setString(1, book.getName());
            StringJoiner authors = new StringJoiner(BookParameter.REGEX_DELIMITER);
            for (String author : book.getAuthors()) {
                authors.add(author);
            }
            statement.setString(2, authors.toString());
            statement.setDouble(3, book.getPrice());
            statement.setInt(4, book.getPages());
            changes = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while adding book into the table", e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
        return changes;
    }

    @Override
    public int removeBookById(long id) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        int changes;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SqlQuery.REQUEST_REMOVE_BOOK_BY_ID);
            statement.setLong(1, id);
            changes = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while removing book from the table", e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
        return changes;
    }

    @Override
    public int removeBooksByName(String name) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        int changes;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SqlQuery.REQUEST_REMOVE_BOOK_BY_NAME);
            statement.setString(1, name);
            changes = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while removing books from the table", e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
        return changes;
    }

    @Override
    public List<Book> selectAll() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Book> books;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SqlQuery.REQUEST_SELECT_ALL);
            resultSet = statement.executeQuery();
            books = parseResult(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while finding books in the table", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
        return books;
    }

    @Override
    public Book findBookById(long id) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Book book = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SqlQuery.REQUEST_FIND_BOOK_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long bookId = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String authors = resultSet.getString(3);
                String[] splitAuthors = authors.split(BookParameter.REGEX_DELIMITER);
                List<String> authorsList = new ArrayList<>();
                Collections.addAll(authorsList, splitAuthors);
                double price = resultSet.getDouble(4);
                int pages = resultSet.getInt(5);
                book = new Book(bookId, name, authorsList, pages, price);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while finding book in the table", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
        return book;
    }

    @Override
    public List<Book> findBooksByName(String name) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Book> books;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SqlQuery.REQUEST_FIND_BOOKS_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            books = parseResult(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while finding books in the table", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
        return books;
    }

    @Override
    public List<Book> findBooksByAuthors(List<String> authors) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Book> books;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SqlQuery.REQUEST_FIND_BOOKS_BY_AUTHORS);
            StringJoiner bookAuthors = new StringJoiner(BookParameter.REGEX_DELIMITER);
            for (String author : authors) {
                bookAuthors.add(author);
            }
            statement.setString(1, bookAuthors.toString());
            resultSet = statement.executeQuery();
            books = parseResult(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while finding books in the table", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
        return books;
    }

    @Override
    public List<Book> findBooksByCountOfPages(int pages) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Book> books;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SqlQuery.REQUEST_FIND_BOOKS_BY_COUNT_OF_PAGES);
            statement.setInt(1, pages);
            resultSet = statement.executeQuery();
            books = parseResult(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while finding books in the table", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
        return books;
    }

    @Override
    public List<Book> findBooksByPrice(double price) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Book> books;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SqlQuery.REQUEST_FIND_BOOKS_BY_PRICE);
            statement.setDouble(1, price);
            resultSet = statement.executeQuery();
            books = parseResult(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while finding books in the table", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
        return books;
    }

    @Override
    public int updateBook(Book book) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        int changes;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SqlQuery.REQUEST_UPDATE_BOOK);
            statement.setString(1, book.getName());
            StringJoiner bookAuthors = new StringJoiner(BookParameter.REGEX_DELIMITER);
            for (String author : book.getAuthors()) {
                bookAuthors.add(author);
            }
            statement.setString(2, bookAuthors.toString());
            statement.setDouble(3, book.getPrice());
            statement.setInt(4, book.getPages());
            statement.setLong(5, book.getBookId());
            changes = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while updating book in the table", e);
        } finally {
            closeStatement(statement);
            connectionPool.releaseConnection(connection);
        }
        return changes;
    }

    private List<Book> parseResult(ResultSet resultSet) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            long bookId = resultSet.getLong(1);
            String bookName = resultSet.getString(2);
            String authors = resultSet.getString(3);
            String[] splitAuthors = authors.split(BookParameter.REGEX_DELIMITER);
            List<String> authorsList = new ArrayList<>();
            Collections.addAll(authorsList, splitAuthors);
            double bookPrice = resultSet.getDouble(4);
            int pages = resultSet.getInt(5);
            books.add(new Book(bookId, bookName, authorsList, pages, bookPrice));
        }
        return books;
    }
}
