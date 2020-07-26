package by.halatsevich.storage.model.validator;

public class BookValidator {
    private static final String REGEX_ID = "[1-9]\\d*";
    private static final String REGEX_NAME = "(\\S+\\s?)+";
    private static final String REGEX_PAGES = "[1-9]\\d*";
    private static final String REGEX_PRICE = "([1-9]+\\.\\d+)|([1-9]\\d*)|([0]\\.\\d*[1-9])";
    private static final String REGEX_AUTHOR =
            "((([a-zA-zа-яА-Я]\\.\\s?)([a-zA-zа-яА-Я]\\.\\s)?[a-zA-zа-яА-Я-]+),?\\s?)+";

    public static boolean isValidId(String id) {
        return !id.isEmpty() && id.matches(REGEX_ID);
    }

    public static boolean isValidName(String name) {
        return !name.isEmpty() && name.matches(REGEX_NAME);
    }

    public static boolean isValidPages(String pages) {
        return !pages.isEmpty() && pages.matches(REGEX_PAGES);
    }

    public static boolean isValidPrice(String price) {
        return !price.isEmpty() && price.matches(REGEX_PRICE);
    }

    public static boolean isValidAuthors(String authors) {
        return !authors.isEmpty() && authors.matches(REGEX_AUTHOR);
    }
}
