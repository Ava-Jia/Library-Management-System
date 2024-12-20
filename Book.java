public class Book {

    private String bookTitle;
    private String bookAuthor;
    private String bookISBN;
    private boolean isBorrowed;

    public Book(String bookTitle, String bookAuthor, String bookISBN, boolean isBorrowed) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookISBN = bookISBN;
        this.isBorrowed = isBorrowed;
    }

    public String getBookTitle() {
        return bookTitle;
    }
    public String getBookAuthor() {
        return bookAuthor;
    }
    public String getBookISBN() {
        return bookISBN;
    }
    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }
    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    @Override
    public String toString() {
        return bookTitle + " by " + bookAuthor + " with ISBN " + bookISBN + (isBorrowed ? " (Borrowed)" : "");
    }

}
