package part01;

public class Artifact {
	private int id;
	private String name;
	private int engagementTime;
	static int currentId = 0;

	public Artifact(String name, int engagementTime) {
		this.id = currentId;
		this.name = name;
		this.engagementTime = engagementTime;
		currentId += 1;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public int getEngagementTime() {
		return engagementTime;
	}
	
	public String toString() {
		return String.format("ID: %s\n"
				+ "Name: %s", id, name);
	}
}
