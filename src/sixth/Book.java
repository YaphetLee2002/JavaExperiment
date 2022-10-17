package sixth;

public class Book {
    private final String author;
    private boolean isBorrowed;
    private final String name;
    private final double price;

    public Book() {
        this.author = null;
        this.name = null;
        this.price = 0;
        this.isBorrowed = false;
    }

    public Book(String name, String author, double price) {
        this.author = author;
        this.name = name;
        this.price = price;
        this.isBorrowed = false;
    }

    public Book(String name, String author, double price, boolean isBorrowed) {
        this(name, author, price);
        this.setBorrowed(isBorrowed);
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    @Override
    public String toString() {
        String borrowState;
        if (this.isBorrowed()) {
            borrowState = "未还";
        } else {
            borrowState = "可借";
        }
        return name + "，" + author + "著，" + price + "元，" + borrowState;

    }
}
