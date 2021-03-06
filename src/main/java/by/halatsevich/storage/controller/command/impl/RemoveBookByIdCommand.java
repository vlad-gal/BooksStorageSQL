package by.halatsevich.storage.controller.command.impl;

import by.halatsevich.storage.controller.command.Command;
import by.halatsevich.storage.controller.command.CommandParameter;
import by.halatsevich.storage.exception.ServiceException;
import by.halatsevich.storage.model.service.BookService;
import by.halatsevich.storage.model.service.impl.BookServiceImpl;
import by.halatsevich.storage.parameter.BookParameter;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

public class RemoveBookByIdCommand implements Command {

    @Override
    public Map<String, Object> execute(Map<String, String> bookParameters) {
        String id = bookParameters.get(BookParameter.ID);
        BookService bookService = BookServiceImpl.getInstance();
        Map<String, Object> result = new HashMap<>();
        try {
            boolean isRemove = bookService.removeBookById(id);
            result.put(CommandParameter.RESULT_KEY, isRemove);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while removing book by id", e);
            result.put(CommandParameter.RESULT_KEY, e.getMessage());
        }
        return result;
    }
}
