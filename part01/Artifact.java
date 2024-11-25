package part01;

public class Artifact extends IdNameClass {
	private int engagementTime;

	public Artifact(String name, int engagementTime) {
		super(name);
		this.engagementTime = engagementTime;
	}

	public int getEngagementTime() {
		return engagementTime;
	}
	
	public String toString() {
		return String.format("ID: %s\n"
				+ "Name: %s", super.getId(), super.getName());
	}
}
