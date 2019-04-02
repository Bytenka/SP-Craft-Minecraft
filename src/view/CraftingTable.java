package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Craft;

public class CraftingTable extends Pane {
	// Where we make the crafts
	private class CraftingBench extends SlotsTable {
		private static final int ROWS = 3;
		private static final int COLS = 3;
		private static final int ARROW_SIZE = 45;
		private static final String BACKGROUND_IMAGE_PATH = "res/graphics/ui/crafting_table.png";

		private ImageView backgroundImage;

		public CraftingBench() {
			super(ROWS, COLS);
			try {			
				Image bi = new Image(
						new FileInputStream(new File(BACKGROUND_IMAGE_PATH)), 
						Slot.SIZE * (COLS + 2) + (COLS + 1) * SlotsTable.GAP_SIZE + ARROW_SIZE,
						Slot.SIZE * ROWS + (ROWS - 1) * SlotsTable.GAP_SIZE,
						false, 
						false
				);
				backgroundImage = new ImageView(bi);
				this.getChildren().add(0, backgroundImage); // Adds behind everything
				
			} catch (FileNotFoundException e) {
				System.err.println("Could not set inventory background");
			}
		}
		
	}
	
	private CraftingBench craftingBench;
	private Slot craftResult;
	
	public CraftingTable() {
		craftingBench = new CraftingBench();
		craftResult = new Slot();
		craftResult.setContentUserModifiable(false);
		
		this.getChildren().add(craftingBench);
		this.getChildren().add(craftResult);
		
		craftResult.setLayoutX(260);
		craftResult.setLayoutY(Slot.SIZE + SlotsTable.GAP_SIZE);
	}
	
	public void update() {
		Item[][] tabItems = new Item[craftingBench.slots.length][craftingBench.slots[0].length];
		for (int i = 0; i < craftingBench.slots.length; i++) {
			for (int j = 0; j < craftingBench.slots[i].length; j++) {
				tabItems[i][j]=craftingBench.getSlot(i, j).getItem();
			}
		}
		Craft tabCraft = new Craft(tabItems);
	}
	
	public void setResult(Item item) {
		craftResult.setItem(item, 1);
	}
	
	public Item getResult() {
		return craftResult.getItem();
	}
}
