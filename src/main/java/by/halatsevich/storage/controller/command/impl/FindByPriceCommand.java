package by.halatsevich.storage.controller.command.impl;

import by.halatsevich.storage.controller.command.Command;
import by.halatsevich.storage.controller.command.CommandParameter;
import by.halatsevich.storage.exception.ServiceException;
import by.halatsevich.storage.model.entity.Book;
import by.halatsevich.storage.model.service.BookService;
import by.halatsevich.storage.model.service.impl.BookServiceImpl;
import by.halatsevich.storage.parameter.BookParameter;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindByPriceCommand implements Command {

    @Override
    public Map<String, Object> execute(Map<String, String> bookParameters) {
        String price = bookParameters.get(BookParameter.PRICE);
        BookService bookService = BookServiceImpl.getInstance();
        Map<String, Object> result = new HashMap<>();
        try {
            List<Book> books = bookService.findBooksByPrice(price);
            result.put(CommandParameter.RESULT_KEY, books);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while finding books by price", e);
            result.put(CommandParameter.RESULT_KEY, e.getMessage());
        }
        return result;
    }
}
