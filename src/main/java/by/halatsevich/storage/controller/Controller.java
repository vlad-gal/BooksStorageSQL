package by.halatsevich.storage.controller;

import by.halatsevich.storage.controller.command.Command;

import java.util.Map;

public class Controller {
    private static Controller instance;
    private final CommandProvider provider = new CommandProvider();

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            return new Controller();
        }
        return instance;
    }

    public Map<String, Object> executeRequest(String command, Map<String, String> bookParameters) {
        Command defineCommand = provider.defineCommand(command);
        return defineCommand.execute(bookParameters);
    }
}
