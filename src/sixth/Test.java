package sixth;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        System.out.println("创建Library实例，预存三本书：");
        Library myLittleLibrary = new Library();
        myLittleLibrary.addBook(new Book("Java程序设计", "张三", 45))
                       .addBook(new Book("Java核心设计", "李四", 50))
                       .addBook(new Book("Java程序设计", "王五", 38, true));

        System.out.println("显示图书馆所有图书的信息：");
        myLittleLibrary.getAllBooks();

        System.out.println("创建一个Reader实例，并查找《Java程序设计》：");
        Reader oneBeautifulGirl = new Reader();
        Book[] books = myLittleLibrary.findBook("Java程序设计");
        for (Book book : books) {
            System.out.println(book);
        }

        System.out.println("借了张三著的那本书，显示图书馆所有图书的信息：");
        oneBeautifulGirl.borrowBook(books[0]);
        myLittleLibrary.getAllBooks();

        System.out.println("归还了那本书，再次显示图书馆所有图书的信息：");
        oneBeautifulGirl.returnBook(books[0]);
        myLittleLibrary.getAllBooks();
    }
}

class A extends B{
    int a;
}

class B {
    String s;
}