package by.halatsevich.storage.controller.command.impl;

import by.halatsevich.storage.controller.command.Command;
import by.halatsevich.storage.controller.command.CommandParameter;
import by.halatsevich.storage.exception.ServiceException;
import by.halatsevich.storage.model.service.BookService;
import by.halatsevich.storage.model.service.impl.BookServiceImpl;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

public class UpdateBookCommand implements Command {

    @Override
    public Map<String, Object> execute(Map<String, String> bookParameters) {
        BookService bookService = BookServiceImpl.getInstance();
        Map<String, Object> result = new HashMap<>();
        try {
            boolean isUpdate = bookService.updateBook(bookParameters);
            result.put(CommandParameter.RESULT_KEY, isUpdate);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while updating book", e);
            result.put(CommandParameter.RESULT_KEY, e.getMessage());
        }
        return result;
    }
}
