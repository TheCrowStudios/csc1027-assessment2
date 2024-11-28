package com.qubwebmuseum;

import java.util.ArrayList;

public class Exhibit extends IdNameClass {
	private ArrayList<ExhibitArtifact> artifacts = new ArrayList<ExhibitArtifact>();
	private static int currentId;
	
	public Exhibit(String name) {
		super(currentId, name);
		currentId += 1;
	}

	public ArrayList<Artifact> getArtifacts() {
		ArrayList<Artifact> artifacts = new ArrayList<>();
		for (int i = 0; i < this.artifacts.size(); i++) {
			artifacts.add(this.artifacts.get(i).getArtifact());
		}

		return artifacts;
	}
	
	public boolean addArtifact(Artifact artifact, Sign sign) {
		if (Helper.findById(getArtifacts(), artifact.getId()) == null) {
			artifacts.add(new ExhibitArtifact(artifact, sign));
			return true;
		}
		
		return false;
	}
	
	public boolean removeArtifact(int id) {
		for (int i = 0; i < artifacts.size(); i++) {
			if (artifacts.get(i).getArtifact().getId() == id) {
				artifacts.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public int getEngagementTime() {
		int engagementTime = 0;
		
		for (int i = 0; i < artifacts.size(); i++) {
			engagementTime += artifacts.get(i).getArtifact().getEngagementTime();
		}
		
		return engagementTime;
	}
	
	public String toString() {
		return super.toString() + String.format("\nNumber of artifacts: %d\n"
				+ "Overall engagement time: %d", artifacts.size(), getEngagementTime());
	}
}
