package by.halatsevich.storage.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public interface Command {
    Logger logger = LogManager.getLogger();

    Map<String, Object> execute(Map<String, String> bookParameters);
}
