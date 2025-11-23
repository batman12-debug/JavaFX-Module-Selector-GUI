package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StudentProfile implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	private Name name;
	private String pNumber;
	private String email;
	private LocalDate submissionDate;
	private Course course;

	private final List<Module> selectedModules = new ArrayList<>();
	private final List<Module> reservedModules = new ArrayList<>(1);

	public void setName(Name name) { this.name = name; }
	public void setPNumber(String pNumber) { this.pNumber = pNumber; }
	public void setEmail(String email) { this.email = email; }
	public void setSubmissionDate(LocalDate submissionDate) { this.submissionDate = submissionDate; }
	public void setCourse(Course course) { this.course = course; }

	public Name getName() { return name; }
	public String getPNumber() { return pNumber; }
	public String getEmail() { return email; }
	public LocalDate getSubmissionDate() { return submissionDate; }
	public Course getCourse() { return course; }

	public List<Module> getSelectedModules() {
		return Collections.unmodifiableList(selectedModules);
	}
	public List<Module> getReservedModules() {
		return Collections.unmodifiableList(reservedModules);
	}

	public void clearSelections() {
		selectedModules.clear();
		reservedModules.clear();
	}

	public int getSelectedCredits() {
		return selectedModules.stream().mapToInt(Module::getCredits).sum();
	}

	public void addSelectedModule(Module m) {
		if (!selectedModules.contains(m)) selectedModules.add(m);
	}

	public void removeSelectedModule(Module m) {
		selectedModules.remove(m);
	}

	public void setReservedModule(Module m) {
		reservedModules.clear();
		if (m != null) reservedModules.add(m);
	}
}


