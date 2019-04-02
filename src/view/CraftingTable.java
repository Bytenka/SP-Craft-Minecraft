package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CraftingTable extends Pane {
	// Where we make the crafts
	private class CraftingBench extends SlotsTable {
		private static final int ROWS = 3;
		private static final int COLS = 3;
		private static final String BACKGROUND_IMAGE_PATH = "res/graphics/ui/crafting_table.png"; // TODO background

		private ImageView backgroundImage;

		public CraftingBench(Controller controller) {
			super(ROWS, COLS, controller);
			try {
				throw new FileNotFoundException(); // TODO remove that when the code below is ready
				/*
				Image bi = new Image(
						new FileInputStream(new File(BACKGROUND_IMAGE_PATH)), 
						Slot.SIZE * COLS + (COLS - 1) * SlotsTable.GAP_SIZE, // TODO
						Slot.SIZE * ROWS + (ROWS - 1) * SlotsTable.GAP_SIZE, // TODO
						false, 
						false
				);
				backgroundImage = new ImageView(bi);
				this.getChildren().add(0, backgroundImage); // Adds behind everything
				*/
			} catch (FileNotFoundException e) {
				System.err.println("Could not set inventory background");
			}
		}
	}
	
	private CraftingBench craftingBench;
	private Slot craftResult;
	
	public CraftingTable(Controller controller) {
		craftingBench = new CraftingBench(controller);
		craftResult = new Slot(controller);
		craftResult.setContentUserModifiable(false);
		
		this.getChildren().add(craftingBench);
		this.getChildren().add(craftResult);
		
		craftResult.setLayoutX(180);
		craftResult.setLayoutY(Slot.SIZE + SlotsTable.GAP_SIZE);
	}
	
	public void setResult(Item item) {
		craftResult.setItem(item, 1);
	}
	
	public Item getResult() {
		return craftResult.getItem();
	}
}
