package part03;

public abstract class IdNameClass {
	private int id;
	private String name;

	public IdNameClass(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return String.format("ID: %s\n"
				+ "Name: %s", id, name);
	}
}
