package part01;

import java.util.ArrayList;

public class Exhibit {
	private int id;
	private String name;
	private ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
	static int currentId = 0;
	
	public Exhibit(String name) {
		this.id = currentId;
		this.name = name;
		currentId += 1;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public ArrayList<Artifact> getArtifacts() {
		return artifacts;
	}
	
	public void addArtifact(Artifact artifact) {
		artifacts.add(artifact);
	}
	
	public int getEngagementTime() {
		int engagementTime = 0;
		
		for (int i = 0; i < artifacts.size(); i++) {
			engagementTime += artifacts.get(i).getEngagementTime();
		}
		
		return engagementTime;
	}
	
	public String toString() {
		return String.format("ID: %s\n"
				+ "Name: %s\n"
				+ "Number of artifacts: %d\n"
				+ "Overall engagement time: %d", id, name, artifacts.size(), getEngagementTime());
	}
}
