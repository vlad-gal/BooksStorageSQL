package test.halatsevich.storage.controller.command.impl;

import by.halatsevich.storage.controller.command.Command;
import by.halatsevich.storage.controller.command.impl.FindByPriceCommand;
import by.halatsevich.storage.model.entity.Book;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class FindByPriceCommandTest {
    Command command;
    Map<String, String> bookParameters = new HashMap<>();

    @BeforeMethod
    public void setUp() {
        command = new FindByPriceCommand();
        bookParameters.put("price", "54");
    }

    @Test
    public void testExecuteSuccess() {
        Map<String, Object> actual = command.execute(bookParameters);
        Map<String, Object> expected = new HashMap<>();
        expected.put("result of command ->",
                Arrays.asList(new Book(63, "How to create russian furnace", Arrays.asList("D.D. Pechkin", "I.I. Ivanov"), 441, 54)));
        assertEquals(actual, expected);
    }

    @Test
    public void testExecuteFailure() {
        bookParameters.put("price", "0");
        Map<String, Object> actual = command.execute(bookParameters);
        Map<String, Object> expected = new HashMap<>();
        expected.put("result of command ->",
                "Price of the book is less than 0.1");
        assertEquals(actual, expected);
    }
}