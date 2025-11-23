package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public final class CreateProfilePane extends GridPane {
	private final ComboBox<String> courseCombo = new ComboBox<>();
	private final TextField pNumberField = new TextField();
	private final TextField firstNameField = new TextField();
	private final TextField familyNameField = new TextField();
	private final TextField emailField = new TextField();
	private final DatePicker datePicker = new DatePicker();
	private final Button createProfileBtn = new Button("Create Profile");

	public CreateProfilePane() {
		setHgap(10);
		setVgap(10);
		setPadding(new Insets(15));

		add(new Label("Select course:"), 0, 0);
		add(courseCombo, 1, 0);

		add(new Label("Input P number:"), 0, 1);
		add(pNumberField, 1, 1);

		add(new Label("Input first name:"), 0, 2);
		add(firstNameField, 1, 2);

		add(new Label("Input surname:"), 0, 3);
		add(familyNameField, 1, 3);

		add(new Label("Input email:"), 0, 4);
		add(emailField, 1, 4);

		add(new Label("Input date:"), 0, 5);
		add(datePicker, 1, 5);

		add(createProfileBtn, 1, 6);

		setMinSize(600, 350);
	}

	public ComboBox<String> getCourseCombo() { return courseCombo; }
	public TextField getPNumberField() { return pNumberField; }
	public TextField getFirstNameField() { return firstNameField; }
	public TextField getFamilyNameField() { return familyNameField; }
	public TextField getEmailField() { return emailField; }
	public DatePicker getDatePicker() { return datePicker; }
	public Button getCreateProfileBtn() { return createProfileBtn; }
}


