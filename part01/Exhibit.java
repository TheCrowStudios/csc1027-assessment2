package part01;

import java.util.ArrayList;

public class Exhibit extends IdNameClass {
	private ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
	private static int currentId;
	
	public Exhibit(String name) {
		super(currentId, name);
		currentId += 1;
	}

	public ArrayList<Artifact> getArtifacts() {
		return artifacts;
	}
	
	public boolean addArtifact(Artifact artifact) {
		if (Helper.findById(artifacts, artifact.getId()) == null) {
			artifacts.add(artifact);
			return true;
		}
		
		return false;
	}
	
	public boolean removeArtifact(int id) {
		for (int i = 0; i < artifacts.size(); i++) {
			if (artifacts.get(i).getId() == id) {
				artifacts.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public int getEngagementTime() {
		int engagementTime = 0;
		
		for (int i = 0; i < artifacts.size(); i++) {
			engagementTime += artifacts.get(i).getEngagementTime();
		}
		
		return engagementTime;
	}
	
	public String toString() {
		return super.toString() + String.format("\nNumber of artifacts: %d\n"
				+ "Overall engagement time: %d", artifacts.size(), getEngagementTime());
	}
}
