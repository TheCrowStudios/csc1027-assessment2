package part01;

public class ExhibitArtifact {
	private Artifact artifact;
	private String signText;

	public ExhibitArtifact(Artifact artifact, String signText) {
		this.artifact = artifact;
		this.signText = signText;
	}

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public String getSignText() {
        return signText;
    }

    public void setSignText(String signText) {
        this.signText = signText;
    }

    @Override
    public String toString() {
        return artifact.toString() + "\nSign text: " + signText;
    }
}
