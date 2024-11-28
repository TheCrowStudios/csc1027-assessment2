package com.qubwebmuseum;

public class Artifact extends IdNameClass {
	private int engagementTime;
	private ArtifactType type;
	private static int currentId;

	public Artifact(String name, ArtifactType type, int engagementTime) {
		super(currentId, name);
		this.type = type;
		this.engagementTime = engagementTime;
		currentId += 1;
	}

	public int getEngagementTime() {
		return engagementTime;
	}
	
	public String toString() {
		return super.toString() + String.format("\nType: %s\nEngagement time: %d", type.name(), engagementTime);
	}

    public ArtifactType getType() {
        return type;
    }
}
