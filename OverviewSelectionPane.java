package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public final class OverviewSelectionPane extends BorderPane {
	private final TextArea profileArea = new TextArea();
	private final TextArea selectedArea = new TextArea();
	private final TextArea reservedArea = new TextArea();
	private final Button saveOverviewBtn = new Button("Save Overview");

	public OverviewSelectionPane() {
		GridPane grid = new GridPane();
		grid.setHgap(25);
		grid.setVgap(10);
		grid.setPadding(new Insets(15));

		profileArea.setEditable(false);
		selectedArea.setEditable(false);
		reservedArea.setEditable(false);

		grid.add(new Label("Profile will appear here"), 0, 0, 4, 1);
		grid.add(profileArea, 0, 1, 4, 1);
		GridPane.setHgrow(profileArea, Priority.ALWAYS);

		grid.add(new Label("Selected modules will appear here"), 0, 2);
		grid.add(selectedArea, 0, 3);
		GridPane.setVgrow(selectedArea, Priority.ALWAYS);
		GridPane.setHgrow(selectedArea, Priority.ALWAYS);
		
		// Add spacing column between selected and reserved modules (at least 3 lines of spacing)
		javafx.scene.control.Label spacer = new javafx.scene.control.Label();
		spacer.setMinWidth(50);
		spacer.setPrefWidth(50);
		grid.add(spacer, 1, 3);
		
		grid.add(new Label("Reserved modules will appear here"), 2, 2);
		grid.add(reservedArea, 2, 3);
		GridPane.setVgrow(reservedArea, Priority.ALWAYS);
		GridPane.setHgrow(reservedArea, Priority.ALWAYS);

		setCenter(grid);
		setBottom(saveOverviewBtn);
		BorderPane.setMargin(saveOverviewBtn, new Insets(10));
	}

	public TextArea getProfileArea() { return profileArea; }
	public TextArea getSelectedArea() { return selectedArea; }
	public TextArea getReservedArea() { return reservedArea; }
	public Button getSaveOverviewBtn() { return saveOverviewBtn; }
}


