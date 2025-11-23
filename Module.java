package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public final class Module implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	private final String code;
	private final String name;
	private final int credits;
	private final Block block;
	private final boolean mandatory;

	public Module(String code, String name, int credits, Block block, boolean mandatory) {
		this.code = code;
		this.name = name;
		this.credits = credits;
		this.block = block;
		this.mandatory = mandatory;
	}

	public String getCode() { return code; }
	public String getName() { return name; }
	public int getCredits() { return credits; }
	public Block getBlock() { return block; }
	public boolean isMandatory() { return mandatory; }

	@Override
	public String toString() {
		return code + " : " + name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Module)) return false;
		Module module = (Module) o;
		return Objects.equals(code, module.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}
}


