package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public final class Name implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	private final String firstName;
	private final String familyName;

	public Name(String firstName, String familyName) {
		this.firstName = firstName == null ? "" : firstName.trim();
		this.familyName = familyName == null ? "" : familyName.trim();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFamilyName() {
		return familyName;
	}

	@Override
	public String toString() {
		return firstName + " " + familyName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Name)) return false;
		Name name = (Name) o;
		return Objects.equals(firstName, name.firstName) && Objects.equals(familyName, name.familyName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, familyName);
	}
}


