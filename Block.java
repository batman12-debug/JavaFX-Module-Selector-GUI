package model;

import java.io.Serializable;

public enum Block implements Serializable {
	BLOCK_1("1"),
	BLOCK_2("2"),
	BLOCK_3_4("3/4");

	private final String label;

	Block(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}
}


