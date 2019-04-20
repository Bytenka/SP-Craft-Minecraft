package view;

import javafx.scene.layout.Pane;
import model.Model;

public class CraftingUI extends Pane {
	private static final int INNER_SPACE = 100;
	
	private BackgroundUI background;

	public CraftingUI(Model model) {
		background = new BackgroundUI();
		this.getChildren().add(background);

		this.setWidth(model.inventory.getWidth());
		this.setHeight(model.craftingTable.getHeight() + model.inventory.getHeight() + INNER_SPACE);
		
		model.craftingTable.setLayoutX(this.getWidth() / 2 - model.craftingTable.getWidth()/2);
		model.craftingTable.setLayoutY(0);
		this.getChildren().add(model.craftingTable);
		
		model.inventory.setLayoutX(0);
		model.inventory.setLayoutY(model.craftingTable.getHeight() + INNER_SPACE);
		this.getChildren().add(model.inventory);
		
		background.forceSize(this.getWidth(), this.getHeight());
		this.updateGraphics();
	}

	public void updateGraphics() {
		background.updateGraphics();
	}
}
