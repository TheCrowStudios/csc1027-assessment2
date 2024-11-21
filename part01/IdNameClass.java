package part01;

public abstract class IdNameClass {
	private int id;
	private String name;
	private static int currentId;

	public IdNameClass(String name) {
		this.id = currentId;
		this.name = name;
		currentId += 1;
		
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
