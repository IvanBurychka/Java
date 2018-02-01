
public class Book {
	
	private int number;
	private String name;
	private static int id = 0;

	public static void main(String[] args) {
		System.out.println("Main from book");
	}
	
	public Book(int number, String name) {
		this.number = number;
		this.name = name;
		id++;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		Book.id = id;
	}

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	{
		System.out.println("hi " + id);
	}
	
	
}
