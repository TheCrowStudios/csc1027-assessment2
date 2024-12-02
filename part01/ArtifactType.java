package part01;

public enum ArtifactType {
	ARTIFACT(1), PAINTING(2), SCULPTURE(3), HISTORICAL(4), DIGITAL(5), TACTILE(6), OTHER(7);
	
	private final int value;

	ArtifactType(int value) {
		this.value = value;
	}

	public static ArtifactType fromInt(int value) {
		for (ArtifactType type : values()) {
			if (type.value == value) return type;
		}

		throw new IllegalArgumentException("Invalid value");
	}
}
