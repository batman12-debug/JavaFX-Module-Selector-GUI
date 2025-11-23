package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import model.Module;

public final class SelectModulesPane extends BorderPane {
	private final ListView<Module> selectedBlock1 = new ListView<>();
	private final ListView<Module> selectedBlock2 = new ListView<>();
	private final ListView<Module> unselectedBlock34 = new ListView<>();
	private final ListView<Module> selectedBlock34 = new ListView<>();

	private final TextField currentCreditsField = new TextField("0");
	private final Button addBtn = new Button("Add");
	private final Button removeBtn = new Button("Remove");
	private final Button resetBtn = new Button("Reset");
	private final Button submitBtn = new Button("Submit");

	public SelectModulesPane() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));

		grid.add(new Label("Selected Block 1 modules"), 0, 0);
		grid.add(selectedBlock1, 0, 1);
		GridPane.setVgrow(selectedBlock1, Priority.ALWAYS);
		grid.add(new Label("Selected Block 2 modules"), 0, 2);
		grid.add(selectedBlock2, 0, 3);
		GridPane.setVgrow(selectedBlock2, Priority.ALWAYS);

		grid.add(new Label("Unselected Block 3/4 modules"), 1, 0);
		grid.add(unselectedBlock34, 1, 1, 1, 2);
		GridPane.setVgrow(unselectedBlock34, Priority.ALWAYS);

		// Buttons between the two Block 3/4 lists
		GridPane buttonPane = new GridPane();
		buttonPane.setVgap(5);
		buttonPane.setPadding(new Insets(5));
		buttonPane.add(new Label("Block 3/4"), 0, 0);
		buttonPane.add(addBtn, 0, 1);
		buttonPane.add(removeBtn, 0, 2);
		grid.add(buttonPane, 2, 1);

		grid.add(new Label("Selected Block 3/4 modules"), 3, 0);
		grid.add(selectedBlock34, 3, 1, 1, 2);
		GridPane.setVgrow(selectedBlock34, Priority.ALWAYS);

		// Bottom controls
		currentCreditsField.setEditable(false);
		GridPane bottomPane = new GridPane();
		bottomPane.setHgap(10);
		bottomPane.setPadding(new Insets(10));
		bottomPane.add(new Label("Current credits:"), 0, 0);
		bottomPane.add(currentCreditsField, 1, 0);
		bottomPane.add(resetBtn, 2, 0);
		bottomPane.add(submitBtn, 3, 0);

		setCenter(grid);
		setBottom(bottomPane);
	}

	public ListView<Module> getSelectedBlock1() { return selectedBlock1; }
	public ListView<Module> getSelectedBlock2() { return selectedBlock2; }
	public ListView<Module> getUnselectedBlock34() { return unselectedBlock34; }
	public ListView<Module> getSelectedBlock34() { return selectedBlock34; }
	public TextField getCurrentCreditsLbl() { return currentCreditsField; }
	public Button getAddBtn() { return addBtn; }
	public Button getRemoveBtn() { return removeBtn; }
	public Button getResetBtn() { return resetBtn; }
	public Button getSubmitBtn() { return submitBtn; }
}


