package sixth;

public class Reader {
    private String name;

    public void borrowBook(Book book) {
        book.setBorrowed(true);
    }

    public void returnBook(Book book) {
        book.setBorrowed(false);
    }
}
