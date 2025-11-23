package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Course implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	private final String name;
	private final List<Module> modules;

	public Course(String name, List<Module> modules) {
		this.name = name;
		this.modules = new ArrayList<>(modules);
	}

	public String getName() { return name; }

	public List<Module> getModules() {
		return Collections.unmodifiableList(modules);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Course)) return false;
		Course course = (Course) o;
		return Objects.equals(name, course.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}


