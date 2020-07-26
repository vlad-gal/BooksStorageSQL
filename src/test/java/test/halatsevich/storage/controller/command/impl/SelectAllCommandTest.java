package test.halatsevich.storage.controller.command.impl;

import by.halatsevich.storage.controller.command.Command;
import by.halatsevich.storage.controller.command.impl.SelectAllCommand;
import by.halatsevich.storage.model.entity.Book;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class SelectAllCommandTest {
    Command command;
    Map<String, String> bookParameters = new HashMap<>();

    @BeforeMethod
    public void setUp() {
        command = new SelectAllCommand();
    }

    @Test
    public void testExecuteSuccess() {
        Map<String, Object> actual = command.execute(bookParameters);
        Map<String, Object> expected = new HashMap<>();
        expected.put("result of command ->",
                Arrays.asList(new Book(61, "Планирование в аудите", Arrays.asList("С.М. Бычкова", "А.В. Газорян"), 293, 594),
                        new Book(62, "How to create furnace", Arrays.asList("D.D. Pechkin"), 241, 1.2),
                        new Book(63, "How to create russian furnace", Arrays.asList("D.D. Pechkin", "I.I. Ivanov"), 441, 54)));
        assertEquals(actual, expected);
    }

    @Test
    public void testExecuteFailure() {
        Map<String, Object> actual = command.execute(bookParameters);
        Map<String, Object> expected = new HashMap<>();
        expected.put("result of command ->",
                Arrays.asList(new Book(61, "Планирование в аудите", Arrays.asList("С.М. Бычкова", "А.В. Газорян"), 293, 594)));
        assertNotEquals(actual, expected);
    }
}