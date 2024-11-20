package part01;

public class Artifact extends IdNameClass {
	private int id;
	private String name;
	private int engagementTime;

	public Artifact(String name, int engagementTime) {
		super(name);
		this.engagementTime = engagementTime;
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
