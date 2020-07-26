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

public class FindByNameCommand implements Command {

    @Override
    public Map<String, Object> execute(Map<String, String> bookParameters) {
        String name = bookParameters.get(BookParameter.NAME);
        BookService bookService = BookServiceImpl.getInstance();
        Map<String, Object> result = new HashMap<>();
        try {
            List<Book> books = bookService.findBooksByName(name);
            result.put(CommandParameter.RESULT_KEY, books);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while finding books by name", e);
            result.put(CommandParameter.RESULT_KEY, e.getMessage());
        }
        return result;
    }
}
