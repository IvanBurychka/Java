import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		Book[] books = new Book[3];
		books[0] = new Book(1, "first book");
//		System.out.println(Book.getId());
		books[1] = new Book(2, "second book");
//		System.out.println(Book.getId());
		books[2] = new Book(3, "third book");
//		System.out.println(Book.getId());
		
		for (Book book: books ) {
			System.out.println(book.getName() + " : " + book.getNumber() + " : " + Book.getId() );
		}
		

	}

}
