import java.util.ArrayList;

public class Library {
    private final ArrayList<Book> books = new ArrayList<>();
    private final ArrayList<Member> members = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully!");
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }

        System.out.println("\n===== ALL BOOKS =====");
        for (Book book : books) {
            book.displayBook();
        }
    }

    public void addMember(Member member) {
        members.add(member);
        System.out.println("Member registered successfully!");
    }

    public void displayMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }

        System.out.println("\n===== ALL MEMBERS =====");
        for (Member member : members) {
            member.displayMember();
        }
    }
}
