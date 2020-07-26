package by.halatsevich.storage.model.entity;

import java.util.List;

public class Book {
    private long bookId;
    private String name;
    private List<String> authors;
    private int pages;
    private double price;

    public Book(long bookId, String name, List<String> authors, int pages, double price) {
        this.bookId = bookId;
        this.name = name;
        this.authors = authors;
        this.pages = pages;
        this.price = price;
    }

    public Book(String name, List<String> authors, int pages, double price) {
        this.name = name;
        this.authors = authors;
        this.pages = pages;
        this.price = price;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        if (pages != book.pages) {
            return false;
        }
        if (Double.compare(price, book.price) != 0) {
            return false;
        }
        if (!name.equals(book.name)) {
            return false;
        }
        return authors.equals(book.authors);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + authors.hashCode();
        result = prime * result + pages;
        result = prime * result + Double.hashCode(price);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("bookId=").append(bookId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", authors=").append(authors);
        sb.append(", pages=").append(pages);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
