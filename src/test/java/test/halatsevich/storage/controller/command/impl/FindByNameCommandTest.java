package test.halatsevich.storage.controller.command.impl;

import by.halatsevich.storage.controller.command.Command;
import by.halatsevich.storage.controller.command.impl.FindByNameCommand;
import by.halatsevich.storage.model.entity.Book;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class FindByNameCommandTest {
    Command command;
    Map<String, String> bookParameters = new HashMap<>();

    @BeforeMethod
    public void setUp() {
        command = new FindByNameCommand();
        bookParameters.put("name", "How to create furnace");
    }

    @Test
    public void testExecuteSuccess() {
        Map<String, Object> actual = command.execute(bookParameters);
        Map<String, Object> expected = new HashMap<>();
        expected.put("result of command ->",
                Arrays.asList(new Book(62, "How to create furnace", Arrays.asList("D.D. Pechkin"), 241, 1.2)));
        assertEquals(actual, expected);
    }

    @Test
    public void testExecuteFailure() {
        bookParameters.put("name", "HI");
        Map<String, Object> actual = command.execute(bookParameters);
        Map<String, Object> expected = new HashMap<>();
        expected.put("result of command ->",
                Arrays.asList(new Book(62, "How to create furnace", Arrays.asList("D.D. Pechkin"), 241, 1.2)));
        assertNotEquals(actual, expected);
    }
}