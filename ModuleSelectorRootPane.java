package view;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;

public final class ModuleSelectorRootPane extends BorderPane {
	private final MenuItem loadData = new MenuItem("Load Student Data");
	private final MenuItem saveData = new MenuItem("Save Student Data");
	private final MenuItem exitApp = new MenuItem("Exit");
	private final MenuItem about = new MenuItem("About");

	private final TabPane tabs = new TabPane();
	private final CreateProfilePane createProfilePane = new CreateProfilePane();
	private final SelectModulesPane selectModulesPane = new SelectModulesPane();
	private final ReserveModulesPane reserveModulesPane = new ReserveModulesPane();
	private final OverviewSelectionPane overviewSelectionPane = new OverviewSelectionPane();

	public ModuleSelectorRootPane() {
		// Set keyboard shortcuts
		loadData.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
		saveData.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		exitApp.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
		
		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(loadData, saveData, new SeparatorMenuItem(), exitApp);
		Menu helpMenu = new Menu("Help");
		helpMenu.getItems().addAll(about);
		MenuBar bar = new MenuBar(fileMenu, helpMenu);

		setTop(bar);

		Tab t1 = new Tab("Create Profile", createProfilePane);
		Tab t2 = new Tab("Select Modules", selectModulesPane);
		Tab t3 = new Tab("Reserve Modules", reserveModulesPane);
		Tab t4 = new Tab("Overview Selection", overviewSelectionPane);
		t1.setClosable(false);
		t2.setClosable(false);
		t3.setClosable(false);
		t4.setClosable(false);
		tabs.getTabs().addAll(t1, t2, t3, t4);
		setCenter(tabs);
	}

	public MenuItem getLoadData() { return loadData; }
	public MenuItem getSaveData() { return saveData; }
	public MenuItem getExitApp() { return exitApp; }
	public MenuItem getAbout() { return about; }

	public TabPane getTabs() { return tabs; }
	public CreateProfilePane getCreateProfilePane() { return createProfilePane; }
	public SelectModulesPane getSelectModulesPane() { return selectModulesPane; }
	public ReserveModulesPane getReserveModulesPane() { return reserveModulesPane; }
	public OverviewSelectionPane getOverviewSelectionPane() { return overviewSelectionPane; }
}


