import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        int choice;

        do {
            System.out.println("\n==============================");
            System.out.println("     LIBRARY MANAGEMENT");
            System.out.println("==============================");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Add Member");
            System.out.println("4. View Members");
            System.out.println("5. Exit");
            System.out.println("==============================");

            System.out.print("Enter your choice: ");
            choice = readInt(scanner);

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int bookId = readInt(scanner);

                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();

                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();

                    Book book = new Book(bookId, title, author);
                    library.addBook(book);
                    break;

                case 2:
                    library.displayBooks();
                    break;
                case 3:
                    System.out.print("Enter Member ID: ");
                    int memberId = readInt(scanner);

                    System.out.print("Enter Member Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = scanner.nextLine();

                    Member member = new Member(memberId, name, phone);
                    library.addMember(member);
                    break;

                case 4:
                    library.displayMembers();
                    break;

                case 5:
                    System.out.println("Thank you for using the Library Management System!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 5);

        scanner.close();
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            if (!scanner.hasNextLine()) {
                System.out.println("\nNo more input. Exiting.");
                return 5;
            }

            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid whole number: ");
            }
        }
    }
}
