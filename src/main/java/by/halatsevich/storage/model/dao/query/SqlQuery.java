package by.halatsevich.storage.model.dao.query;

public class SqlQuery {
    public static final String REQUEST_ADD_BOOK =
            "INSERT Books(name, authors, price, pages) VALUES (?,?,?,?)";
    public static final String REQUEST_REMOVE_BOOK_BY_ID =
            "DELETE FROM Books WHERE bookId = ?";
    public static final String REQUEST_REMOVE_BOOK_BY_NAME =
            "DELETE FROM Books WHERE name = ?";
    public static final String REQUEST_SELECT_ALL =
            "SELECT bookId, name, authors, price, pages FROM Books";
    public static final String REQUEST_FIND_BOOK_BY_ID =
            "SELECT bookId, name, authors, price, pages FROM Books WHERE bookId = ?";
    public static final String REQUEST_FIND_BOOKS_BY_NAME =
            "SELECT bookId, name, authors, price, pages FROM Books WHERE name = ?";
    public static final String REQUEST_FIND_BOOKS_BY_AUTHORS =
            "SELECT bookId, name, authors, price, pages FROM Books WHERE authors = ?";
    public static final String REQUEST_FIND_BOOKS_BY_COUNT_OF_PAGES =
            "SELECT bookId, name, authors, price, pages FROM Books WHERE pages = ?";
    public static final String REQUEST_FIND_BOOKS_BY_PRICE =
            "SELECT bookId, name, authors, price, pages FROM Books WHERE price = ?";
    public static final String REQUEST_UPDATE_BOOK =
            "UPDATE Books SET name = ?, authors = ?, price = ?, price = ? WHERE bookId = ?";
}
