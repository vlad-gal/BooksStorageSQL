package test.halatsevich.storage.controller;

import by.halatsevich.storage.controller.Controller;
import by.halatsevich.storage.model.entity.Book;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class ControllerTest {
    Controller controller;
    Map<String, String> bookParameters = new HashMap<>();

    @BeforeMethod
    public void setUp() {
        controller = Controller.getInstance();
        bookParameters.put("id", "61");
    }

    @Test
    public void testExecuteRequestSuccess() {
        Map<String, Object> actual = controller.executeRequest("Find_by_id", bookParameters);
        Map<String, Object> expected = new HashMap<>();
        expected.put("result of command ->", new Book(61, "Планирование в аудите", Arrays.asList("С.М. Бычкова", "А.В. Газорян"), 293, 594.0));
        assertEquals(actual, expected);
    }

    @Test
    public void testExecuteRequestFailure() {
        Map<String, Object> actual = controller.executeRequest("", bookParameters);
        Map<String, Object> expected = new HashMap<>();
        expected.put("result of command ->", true);
        assertNotEquals(actual, expected);
    }
}