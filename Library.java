import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {

    private String booksFile;
    public Library(String booksFile) {
        this.booksFile = booksFile;
    }
    
    private List<Book> loadBooks(){
        List<Book> books = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(booksFile))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if (parts.length == 4){
                    books.add(new Book(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3])));
                } else{
                    System.out.println("Invalid book format: " + line);
                }
            }
        } catch(IOException e) {
            System.out.println("Error reading books file: " + e.getMessage());
        }
        return books;
    }

    private void saveBooks(List<Book> books){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(booksFile))){
            for(Book book: books){
                bw.write(book.getBookTitle() + "," + book.getBookAuthor() + "," + book.getBookISBN() + "," + book.isBorrowed() + "\n");
            }
        } catch(IOException e) {
            System.out.println("Error writing books file: " + e.getMessage());
        }
    }

    public boolean addBook(String bookTitle, String bookAuthor, String bookISBN){
        List<Book> books = loadBooks();
        if (books.stream().anyMatch(book -> book.getBookISBN().equals(bookISBN))){
            System.out.println("Book with ISBN " + bookISBN + " already exists.");
            return false;
        }
        books.add(new Book(bookTitle, bookAuthor, bookISBN, false));
        saveBooks(books);
        return true;
    }

    public List<Book> searchBooks(String query, String type) {
        List<Book> books = loadBooks();
        if (query == null || query.isEmpty()) {
            return books;
        }

        switch(type){
            case "bookTitle":
                return books.stream().filter(book -> book.getBookTitle().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
            case "bookAuthor":
                return books.stream().filter(book -> book.getBookAuthor().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
            case "bookISBN":
                return books.stream().filter(book -> book.getBookISBN().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
            default:
                return new ArrayList<>();
        }
    }

    public boolean borrowBook(String bookISBN){
        List<Book> books = loadBooks();
        Book book = books.stream().filter(b -> b.getBookISBN().equals(bookISBN)).findFirst().orElse(null);
        if (book == null){
            System.out.println("Book with ISBN " + bookISBN + " not found.");
            return false;
        }
        if (book.isBorrowed()){
            System.out.println("Book with ISBN " + bookISBN + " is already borrowed.");
            return false;
        }
        book.setBorrowed(true);
        saveBooks(books);
        return true;
    }  

    public boolean returnBook(String bookISBN){
        List<Book> books = loadBooks();
        Book book = books.stream().filter(b -> b.getBookISBN().equals(bookISBN)).findFirst().orElse(null);
        if (book == null){
            System.out.println("Book with ISBN " + bookISBN + " not found.");
            return false;
        }  
        if (!book.isBorrowed()){
            System.out.println("Book with ISBN " + bookISBN + " is not borrowed.");
            return false;
        }
        book.setBorrowed(false);
        saveBooks(books);
        return true;
    }

    public List<Book> getAllBooks(){
        return loadBooks();
    }
}
