package sixth;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private ArrayList<Book> books = new ArrayList<>();

    public Library addBook(Book book) {
        books.add(book);
        return this;
    }

    public Book[] findBook(String bookName) {
        List<Book> list = new ArrayList<>();
        for (Book book : books) {
            if (book.getName().equals(bookName)) {
                list.add(book);
            }
        }
        return list.toArray(books.toArray(new Book[list.size()]));
    }

    public Book[] getAllBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
        return books.toArray(books.toArray(new Book[books.size()]));
    }
}
