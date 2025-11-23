package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import model.Block;
import model.Course;
import model.Module;
import model.Name;
import model.StudentProfile;
import view.ModuleSelectorRootPane;
import view.CreateProfilePane;
import view.SelectModulesPane;
import view.ReserveModulesPane;
import view.OverviewSelectionPane;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public final class ModuleSelectorController {
	private final ModuleSelectorRootPane view;
	private final StudentProfile model;

	private final Map<String, Course> courses = new LinkedHashMap<>();

	public ModuleSelectorController(ModuleSelectorRootPane view, StudentProfile model) {
		this.view = view;
		this.model = model;
		createModulesAndCourses();
		initialise();
	}

	private void initialise() {
		addCourseDataToComboBox();
		registerHandlers();
		resetSelectTab();
	}

	private void addCourseDataToComboBox() {
		CreateProfilePane cp = view.getCreateProfilePane();
		cp.getCourseCombo().setItems(FXCollections.observableArrayList(courses.keySet()));
		if (!courses.isEmpty()) cp.getCourseCombo().getSelectionModel().selectFirst();
	}

	private void registerHandlers() {
		CreateProfilePane cp = view.getCreateProfilePane();
		SelectModulesPane sp = view.getSelectModulesPane();
		ReserveModulesPane rp = view.getReserveModulesPane();
		OverviewSelectionPane op = view.getOverviewSelectionPane();

		cp.getCreateProfileBtn().setOnAction(this::handleCreateProfile);
		sp.getResetBtn().setOnAction(e -> resetSelectTab());
		sp.getAddBtn().setOnAction(e -> moveItem(sp.getUnselectedBlock34(), sp.getSelectedBlock34()));
		sp.getRemoveBtn().setOnAction(e -> moveItem(sp.getSelectedBlock34(), sp.getUnselectedBlock34()));
		sp.getSubmitBtn().setOnAction(e -> handleSubmitSelected());

		rp.getAddBtn().setOnAction(e -> moveSingleReserve(rp.getUnselectedBlock34(), rp.getReservedBlock34()));
		rp.getRemoveBtn().setOnAction(e -> moveItem(rp.getReservedBlock34(), rp.getUnselectedBlock34()));
		rp.getConfirmBtn().setOnAction(e -> handleConfirmReserve());

		view.getSaveData().setOnAction(e -> saveProfileBinary());
		view.getLoadData().setOnAction(e -> loadProfileBinary());
		view.getExitApp().setOnAction(e -> view.getScene().getWindow().hide());
		view.getAbout().setOnAction(e -> new Alert(Alert.AlertType.INFORMATION, "JavaFX Module Selector GUI\nCTEC2710", ButtonType.OK).showAndWait());

		op.getSaveOverviewBtn().setOnAction(e -> saveOverviewText());
	}

	private void handleCreateProfile(ActionEvent e) {
		CreateProfilePane cp = view.getCreateProfilePane();
		String courseName = cp.getCourseCombo().getSelectionModel().getSelectedItem();
		if (courseName == null) {
			alert(Alert.AlertType.WARNING, "Please select a course.");
			return;
		}
		String pNumber = cp.getPNumberField().getText().trim();
		String firstName = cp.getFirstNameField().getText().trim();
		String familyName = cp.getFamilyNameField().getText().trim();
		String email = cp.getEmailField().getText().trim();
		
		if (pNumber.isEmpty() || firstName.isEmpty() || familyName.isEmpty() || email.isEmpty()) {
			alert(Alert.AlertType.WARNING, "Please fill in all fields.");
			return;
		}
		
		model.setCourse(courses.get(courseName));
		model.setPNumber(pNumber);
		model.setName(new Name(firstName, familyName));
		model.setEmail(email);
		model.setSubmissionDate(Optional.ofNullable(cp.getDatePicker().getValue()).orElse(LocalDate.now()));

		resetSelectTab();
		view.getTabs().getSelectionModel().select(1);
	}

	private void resetSelectTab() {
		SelectModulesPane sp = view.getSelectModulesPane();
		sp.getSelectedBlock1().getItems().clear();
		sp.getSelectedBlock2().getItems().clear();
		sp.getSelectedBlock34().getItems().clear();
		sp.getUnselectedBlock34().getItems().clear();

		model.clearSelections();
		sp.getCurrentCreditsLbl().setText(String.valueOf(model.getSelectedCredits()));

		Course course = model.getCourse();
		if (course == null && !courses.isEmpty()) {
			course = courses.values().iterator().next();
		}
		if (course == null) return;

		// Compulsory modules to their blocks; optional 3/4 to unselected
		for (model.Module m : course.getModules()) {
			if (m.isMandatory()) {
				if (m.getBlock() == Block.BLOCK_1) sp.getSelectedBlock1().getItems().add(m);
				else if (m.getBlock() == Block.BLOCK_2) sp.getSelectedBlock2().getItems().add(m);
				else if (m.getBlock() == Block.BLOCK_3_4) sp.getSelectedBlock34().getItems().add(m); // Development Project
				model.addSelectedModule(m);
			} else if (m.getBlock() == Block.BLOCK_3_4) {
				sp.getUnselectedBlock34().getItems().add(m);
			}
		}
		sp.getCurrentCreditsLbl().setText(String.valueOf(model.getSelectedCredits()));
	}

	private void moveItem(javafx.scene.control.ListView<model.Module> from, javafx.scene.control.ListView<model.Module> to) {
		model.Module selected = from.getSelectionModel().getSelectedItem();
		if (selected == null) return;
		from.getItems().remove(selected);
		to.getItems().add(selected);
		updateSelectedFromSelectTab();
	}

	private void moveSingleReserve(javafx.scene.control.ListView<model.Module> from, javafx.scene.control.ListView<model.Module> to) {
		if (!to.getItems().isEmpty()) return;
		moveItem(from, to);
	}

	private void updateSelectedFromSelectTab() {
		SelectModulesPane sp = view.getSelectModulesPane();
		model.clearSelections();
		sp.getSelectedBlock1().getItems().forEach(model::addSelectedModule);
		sp.getSelectedBlock2().getItems().forEach(model::addSelectedModule);
		sp.getSelectedBlock34().getItems().forEach(model::addSelectedModule);
		sp.getCurrentCreditsLbl().setText(String.valueOf(model.getSelectedCredits()));
	}

	private void handleSubmitSelected() {
		int credits = model.getSelectedCredits();
		if (credits != 120) {
			alert(Alert.AlertType.WARNING, "You must select exactly 120 credits. Current: " + credits);
			return;
		}
		populateReserveTab();
		view.getTabs().getSelectionModel().select(2);
	}

	private void populateReserveTab() {
		ReserveModulesPane rp = view.getReserveModulesPane();
		rp.getReservedBlock34().getItems().clear();
		rp.getUnselectedBlock34().getItems().clear();

		Course course = model.getCourse();
		Set<model.Module> selected = new HashSet<>(model.getSelectedModules());
		List<model.Module> remaining = course.getModules().stream()
			.filter(m -> m.getBlock() == Block.BLOCK_3_4 && !m.isMandatory() && !selected.contains(m))
			.collect(Collectors.toList());
		rp.getUnselectedBlock34().getItems().addAll(remaining);
	}

	private void handleConfirmReserve() {
		ReserveModulesPane rp = view.getReserveModulesPane();
		model.Module reserve = rp.getReservedBlock34().getItems().isEmpty() ? null : rp.getReservedBlock34().getItems().getFirst();
		if (reserve == null) {
			alert(Alert.AlertType.WARNING, "Please reserve one optional module.");
			return;
		}
		model.setReservedModule(reserve);
		populateOverview();
		view.getTabs().getSelectionModel().select(3);
	}

	private void populateOverview() {
		OverviewSelectionPane op = view.getOverviewSelectionPane();
		Name name = Optional.ofNullable(model.getName()).orElse(new Name("",""));
		LocalDate date = Optional.ofNullable(model.getSubmissionDate()).orElse(LocalDate.now());
		op.getProfileArea().setText(
			"Name: " + name.getFirstName() + " " + name.getFamilyName() + "\n" +
			"PNo: " + nonNull(model.getPNumber()) + "\n" +
			"Email: " + nonNull(model.getEmail()) + "\n" +
			"Date: " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + "\n" +
			"Course: " + Optional.ofNullable(model.getCourse()).map(Course::getName).orElse("")
		);
		op.getSelectedArea().setText(formatModules("Selected modules", model.getSelectedModules(), true));
		op.getReservedArea().setText(formatModules("Reserved modules", model.getReservedModules(), false));
	}

	private static String formatModules(String title, List<model.Module> modules, boolean showMandatory) {
		StringBuilder sb = new StringBuilder(title).append(":\n\n");
		for (model.Module m : modules) {
			String blockStr = "block " + m.getBlock().toString();
			sb.append("Module code: ").append(m.getCode())
				.append(", Module name: ").append(m.getName())
				.append(", Credits: ").append(m.getCredits());
			if (showMandatory && m.isMandatory()) {
				sb.append(", Mandatory on your course? yes");
			}
			sb.append(", Block: ").append(blockStr).append("\n\n");
		}
		return sb.toString();
	}

	private void saveOverviewText() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save Overview");
		chooser.setInitialFileName("overview.dat");
		// Set .dat as the default/selected filter
		FileChooser.ExtensionFilter datFilter = new FileChooser.ExtensionFilter("Data Files (*.dat)","*.dat");
		chooser.getExtensionFilters().add(datFilter);
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files","*.*"));
		chooser.setSelectedExtensionFilter(datFilter); // Force .dat filter to be selected
		
		File file = chooser.showSaveDialog(view.getScene().getWindow());
		if (file == null) return;
		
		// Force .dat extension regardless of what the user typed or system added
		String fileName = file.getName();
		String parentPath = file.getParent();
		
		// Remove ALL extensions and add .dat
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0) {
			fileName = fileName.substring(0, lastDot);
		}
		// Always add .dat extension
		fileName = fileName + ".dat";
		
		file = new File(parentPath, fileName);
		
		try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
			pw.println(view.getOverviewSelectionPane().getProfileArea().getText());
			pw.println();
			pw.println(view.getOverviewSelectionPane().getSelectedArea().getText());
			pw.println();
			pw.println(view.getOverviewSelectionPane().getReservedArea().getText());
			alert(Alert.AlertType.INFORMATION, "Overview saved successfully to: " + file.getName());
		} catch (IOException ex) {
			alert(Alert.AlertType.ERROR, "Failed to save overview: " + ex.getMessage());
		}
	}

	private void saveProfileBinary() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save Student Data");
		chooser.setInitialFileName("student.dat");
		// Set .dat as the default/selected filter
		FileChooser.ExtensionFilter datFilter = new FileChooser.ExtensionFilter("Data Files (*.dat)","*.dat");
		chooser.getExtensionFilters().add(datFilter);
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files","*.*"));
		chooser.setSelectedExtensionFilter(datFilter); // Force .dat filter to be selected
		
		File file = chooser.showSaveDialog(view.getScene().getWindow());
		if (file == null) return;
		
		// Force .dat extension regardless of what the user typed or system added
		String fileName = file.getName();
		String parentPath = file.getParent();
		
		// Remove ALL extensions and add .dat
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0) {
			fileName = fileName.substring(0, lastDot);
		}
		// Always add .dat extension
		fileName = fileName + ".dat";
		
		file = new File(parentPath, fileName);
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(model);
			alert(Alert.AlertType.INFORMATION, "Student data saved successfully to: " + file.getName());
		} catch (IOException ex) {
			alert(Alert.AlertType.ERROR, "Failed to save: " + ex.getMessage());
		}
	}

	private void loadProfileBinary() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Load Student Data");
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data Files (*.dat)","*.dat"));
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files","*.*"));
		File file = chooser.showOpenDialog(view.getScene().getWindow());
		if (file == null) return;
		
		// Also try .dat.txt if .dat file doesn't exist (for backwards compatibility)
		if (!file.exists() && file.getName().toLowerCase().endsWith(".dat")) {
			File altFile = new File(file.getParent(), file.getName() + ".txt");
			if (altFile.exists()) {
				file = altFile;
			}
		}
		
		if (!file.exists()) {
			alert(Alert.AlertType.ERROR, "File not found: " + file.getName());
			return;
		}
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			StudentProfile loaded = (StudentProfile) ois.readObject();
			// copy fields to current model instance
			model.setName(loaded.getName());
			model.setPNumber(loaded.getPNumber());
			model.setEmail(loaded.getEmail());
			model.setSubmissionDate(loaded.getSubmissionDate());
			model.setCourse(loaded.getCourse());
			
			// Restore selected modules
			model.clearSelections();
			for (model.Module m : loaded.getSelectedModules()) {
				model.addSelectedModule(m);
			}
			if (!loaded.getReservedModules().isEmpty()) {
				model.setReservedModule(loaded.getReservedModules().get(0));
			}
			
			// Restore GUI state
			restoreSelectTab();
			restoreReserveTab();
			populateOverview();
			view.getTabs().getSelectionModel().select(3);
			alert(Alert.AlertType.INFORMATION, "Student data loaded successfully from: " + file.getName());
		} catch (IOException | ClassNotFoundException ex) {
			alert(Alert.AlertType.ERROR, "Failed to load: " + ex.getMessage() + "\n\nMake sure you're loading a .dat file created by 'Save Student Data'.");
		}
	}
	
	private void restoreSelectTab() {
		SelectModulesPane sp = view.getSelectModulesPane();
		sp.getSelectedBlock1().getItems().clear();
		sp.getSelectedBlock2().getItems().clear();
		sp.getSelectedBlock34().getItems().clear();
		sp.getUnselectedBlock34().getItems().clear();
		
		Course course = model.getCourse();
		if (course == null) return;
		
		Set<model.Module> selected = new HashSet<>(model.getSelectedModules());
		
		// Populate selected blocks
		for (model.Module m : model.getSelectedModules()) {
			if (m.getBlock() == Block.BLOCK_1) sp.getSelectedBlock1().getItems().add(m);
			else if (m.getBlock() == Block.BLOCK_2) sp.getSelectedBlock2().getItems().add(m);
			else if (m.getBlock() == Block.BLOCK_3_4) sp.getSelectedBlock34().getItems().add(m);
		}
		
		// Populate unselected Block 3/4 modules
		for (model.Module m : course.getModules()) {
			if (m.getBlock() == Block.BLOCK_3_4 && !m.isMandatory() && !selected.contains(m)) {
				sp.getUnselectedBlock34().getItems().add(m);
			}
		}
		
		sp.getCurrentCreditsLbl().setText(String.valueOf(model.getSelectedCredits()));
	}
	
	private void restoreReserveTab() {
		ReserveModulesPane rp = view.getReserveModulesPane();
		rp.getReservedBlock34().getItems().clear();
		rp.getUnselectedBlock34().getItems().clear();
		
		Course course = model.getCourse();
		if (course == null) return;
		
		Set<model.Module> selected = new HashSet<>(model.getSelectedModules());
		Set<model.Module> reserved = new HashSet<>(model.getReservedModules());
		
		// Add reserved module if exists
		if (!model.getReservedModules().isEmpty()) {
			rp.getReservedBlock34().getItems().addAll(model.getReservedModules());
		}
		
		// Populate remaining unselected Block 3/4 modules
		for (Module m : course.getModules()) {
			if (m.getBlock() == Block.BLOCK_3_4 && !m.isMandatory() && !selected.contains(m) && !reserved.contains(m)) {
				rp.getUnselectedBlock34().getItems().add(m);
			}
		}
	}

	private static String nonNull(String s) { return s == null ? "" : s; }

	private void alert(Alert.AlertType type, String msg) {
		new Alert(type, msg, ButtonType.OK).showAndWait();
	}

	private void createModulesAndCourses() {
		// Shared modules
		model.Module ctec3701 = new model.Module("CTEC3701","Software Development: Methods & Standards",30, Block.BLOCK_1, true);
		model.Module ctec3702 = new model.Module("CTEC3702","Big Data and Machine Learning",30, Block.BLOCK_2, false);
		model.Module ctec3703 = new model.Module("CTEC3703","Mobile App Development and Big Data",30, Block.BLOCK_2, false);
		model.Module ctec3451 = new model.Module("CTEC3451","Development Project",30, Block.BLOCK_3_4, true);
		model.Module ctec3704 = new model.Module("CTEC3704","Functional Programming",30, Block.BLOCK_3_4, false);
		model.Module ctec3705 = new model.Module("CTEC3705","Advanced Web Development",30, Block.BLOCK_3_4, false);
		model.Module imat3711 = new model.Module("IMAT3711","Privacy and Data Protection",30, Block.BLOCK_3_4, false);
		model.Module imat3722 = new model.Module("IMAT3722","Fuzzy Logic and Inference Systems",30, Block.BLOCK_3_4, false);
		model.Module ctec3706 = new model.Module("CTEC3706","Embedded Systems and IoT",30, Block.BLOCK_3_4, false);

		// Computer Science: compulsory credits 90 (3701, 3702, 3451)
		List<model.Module> csModules = new ArrayList<>();
		csModules.add(ctec3701); // block1 compulsory
		csModules.add(ctec3702); // block2 compulsory for CS
		csModules.add(ctec3451); // project compulsory
		// options (block 3/4)
		csModules.add(ctec3704);
		csModules.add(ctec3705);
		csModules.add(imat3711);
		csModules.add(imat3722);

		// Software Engineering: compulsory credits 90 (3701, 3703, 3451)
		List<model.Module> seModules = new ArrayList<>();
		seModules.add(ctec3701); // block1 compulsory
		seModules.add(ctec3703); // block2 compulsory for SE
		seModules.add(ctec3451); // project compulsory
		// options (block 3/4)
		seModules.add(ctec3704);
		seModules.add(ctec3705);
		seModules.add(ctec3706);

		courses.put("Computer Science", new Course("Computer Science", csModules));
		courses.put("Software Engineering", new Course("Software Engineering", seModules));
	}
}


