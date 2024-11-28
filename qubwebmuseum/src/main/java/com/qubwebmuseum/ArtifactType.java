package com.qubwebmuseum;

public enum ArtifactType {
	ARTIFACT(1), PAINTING(2), SCULPTURE(3), DIGITAL(4), TACTILE(5), OTHER(6);
	
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
