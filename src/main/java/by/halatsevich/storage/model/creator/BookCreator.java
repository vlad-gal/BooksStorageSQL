package by.halatsevich.storage.model.creator;

import by.halatsevich.storage.model.entity.Book;
import by.halatsevich.storage.parameter.BookParameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookCreator {

    public Book createBook(String id, String name, String authors, String price, String pages) {
        long parsedId = Long.parseLong(id);
        Book book = createBook(name, authors, price, pages);
        book.setBookId(parsedId);
        return book;
    }

    public Book createBook(String name, String authors, String price, String pages) {
        String[] splitAuthors = authors.split(BookParameter.REGEX_DELIMITER);
        List<String> listAuthors = new ArrayList<>();
        Collections.addAll(listAuthors, splitAuthors);
        int parsedPages = Integer.parseInt(pages);
        double parsedPrice = Double.parseDouble(price);
        return new Book(name, listAuthors, parsedPages, parsedPrice);
    }
}
