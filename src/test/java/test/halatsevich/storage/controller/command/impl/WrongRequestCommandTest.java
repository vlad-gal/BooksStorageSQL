package test.halatsevich.storage.controller.command.impl;

import by.halatsevich.storage.controller.command.Command;
import by.halatsevich.storage.controller.command.impl.WrongRequestCommand;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class WrongRequestCommandTest {
    Command command = new WrongRequestCommand();
    Map<String, String> bookParameters = new HashMap<>();

    @Test
    public void testExecute() {
        Map<String, Object> actual = command.execute(bookParameters);
        Map<String, Object> expected = new HashMap<>();
        expected.put("result of command ->", "wrong request command");
        assertEquals(actual, expected);
    }
}