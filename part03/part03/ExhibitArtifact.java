package part03;

public class ExhibitArtifact {
	private Artifact artifact;
	private Sign sign;

	public ExhibitArtifact(Artifact artifact, Sign sign) {
		this.artifact = artifact;
		this.sign = sign;
	}

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public Sign getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return artifact.toString() + "\nSign text: " + sign.getName();
    }
}
