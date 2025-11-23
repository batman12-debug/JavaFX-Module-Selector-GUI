package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import model.Module;

public final class ReserveModulesPane extends BorderPane {
	private final ListView<Module> unselectedBlock34 = new ListView<>();
	private final ListView<Module> reservedBlock34 = new ListView<>();
	private final Button addBtn = new Button("Add");
	private final Button removeBtn = new Button("Remove");
	private final Button confirmBtn = new Button("Confirm");

	public ReserveModulesPane() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));

		grid.add(new Label("Unselected Block 3/4 modules"), 0, 0);
		grid.add(unselectedBlock34, 0, 1);
		GridPane.setVgrow(unselectedBlock34, Priority.ALWAYS);
		GridPane.setHgrow(unselectedBlock34, Priority.ALWAYS);
		grid.add(new Label("Reserved Block 3/4 modules"), 1, 0);
		grid.add(reservedBlock34, 1, 1);
		GridPane.setVgrow(reservedBlock34, Priority.ALWAYS);
		GridPane.setHgrow(reservedBlock34, Priority.ALWAYS);

		ToolBar bar = new ToolBar(new Label("Reserve one optional module"), addBtn, removeBtn, confirmBtn);

		setCenter(grid);
		setBottom(bar);
	}

	public ListView<Module> getUnselectedBlock34() { return unselectedBlock34; }
	public ListView<Module> getReservedBlock34() { return reservedBlock34; }
	public Button getAddBtn() { return addBtn; }
	public Button getRemoveBtn() { return removeBtn; }
	public Button getConfirmBtn() { return confirmBtn; }
}


