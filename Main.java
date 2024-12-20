import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userFile = "users.txt";
        String bookFile = "books.txt";

        Login login = new Login(userFile);
        Library library = new Library(bookFile);
        User currentUser = null;

        try{
        while(true) {
            if(currentUser == null){
                System.out.println("\n --- Login/Register ---");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        if(login.register(username, password)){
                            System.out.println("Registration successful.");
                        } else {
                            System.out.println("Registration failed.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter username: ");
                        username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        password = scanner.nextLine();
                        currentUser = login.login(username, password);
                        if(currentUser != null){
                            System.out.println("Login successful! Welcome, " + currentUser.getUsername() + "!");
                        } else {
                            System.out.println("Login failed.");
                        }
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }else {
                System.out.println("\n --- Library Management System ---");
                System.out.println("1. Add Book");
                System.out.println("2. Search Book");
                System.out.println("3. Borrow Book");
                System.out.println("4. Return Book");
                System.out.println("5. View All Books");
                System.out.println("6. Logout");
                if(currentUser.isAdmin()){
                    System.out.println("7. Admin Options");
                }

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter book title: ");
                        String bookTitle = scanner.nextLine();
                        System.out.print("Enter book author: ");
                        String bookAuthor = scanner.nextLine();
                        System.out.print("Enter book ISBN: ");
                        String bookISBN = scanner.nextLine();
                        if(library.addBook(bookTitle, bookAuthor, bookISBN)){
                            System.out.println("Book added successfully.");
                        } else {
                            System.out.println("Book addition failed.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter search query: ");
                        String query = scanner.nextLine();
                        System.out.print("Enter search type (bookTitle, bookAuthor, bookISBN): ");
                        String searchType = scanner.nextLine();
                        List<Book> searchResults = library.searchBooks(query, searchType);
                        if(searchResults.isEmpty()){
                            System.out.println("No results found.");
                        } else {
                            for(Book book : searchResults){
                                System.out.println(book);
                            }
                        }
                        break;
                    case 3:
                        System.out.print("Enter book ISBN to borrow: ");
                        bookISBN = scanner.nextLine();
                        if(library.borrowBook(bookISBN)){
                            System.out.println("Book borrowed successfully.");
                        } else {
                            System.out.println("Book borrowing failed.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter book ISBN to return: ");
                        bookISBN = scanner.nextLine();
                        if(library.returnBook(bookISBN)){
                            System.out.println("Book returned successfully.");
                        } else {
                            System.out.println("Book returning failed.");
                        }
                        break;
                    case 5:
                        List<Book> allBooks = library.getAllBooks();
                        if(allBooks.isEmpty()){
                            System.out.println("No books in the library.");
                        } else {
                            for(Book book : allBooks){
                                System.out.println(book);
                            }
                        }
                        break;
                    case 6:
                        currentUser = null;
                        System.out.println("Logged out.");
                        break;
                    case 7:
                        if(currentUser.isAdmin()){
                            System.out.println("Admin functionality not yet implemented");
                        }else{
                            System.out.println("Invalid option");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
        }
        } catch(NumberFormatException e) {
            System.out.println("Invalid input, not a valid number:" + e.getMessage());        
        } 
        finally {
            if( scanner != null){
                scanner.close();
            }
        }
    }
}
