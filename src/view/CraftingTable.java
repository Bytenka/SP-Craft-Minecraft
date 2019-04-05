package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Craft;

public class CraftingTable extends Pane {
	// Where we make the crafts
	private class CraftingBench extends SlotsTable {
		private static final int ROWS = 3;
		private static final int COLS = 3;

		public CraftingBench(Controller controller) {
			super(ROWS, COLS, controller);
		}
		
	}
	
	private static final String BACKGROUND_IMAGE_PATH = "res/graphics/ui/crafting_table.png";
	private static final int ARROW_SIZE = 45;
	
	private CraftingBench craftingBench;
	private Slot craftResult;
	private ImageView backgroundImage;
	
	public CraftingTable(Controller controller) {
		craftingBench = new CraftingBench(controller);
		craftResult = new Slot(controller);
		craftResult.setContentUserModifiable(false);
		
		this.setWidth(Slot.SIZE * (craftingBench.getRows() + 2) + (craftingBench.getCols() + 1) * SlotsTable.GAP_SIZE + ARROW_SIZE);
		this.setHeight(Slot.SIZE * craftingBench.getRows() + (craftingBench.getRows() - 1) * SlotsTable.GAP_SIZE);
		
		try {
			Image bi = new Image(
					new FileInputStream(new File(BACKGROUND_IMAGE_PATH)), 
					this.getWidth(),
					this.getHeight(),
					false, 
					false
			);
			backgroundImage = new ImageView(bi);
			this.getChildren().add(0, backgroundImage); // Adds behind everything
			
		} catch (FileNotFoundException e) {
			System.err.println("Could not set inventory background");
		}
		
		craftResult.setLayoutX(260);
		craftResult.setLayoutY(Slot.SIZE + SlotsTable.GAP_SIZE);
		
		this.getChildren().add(craftingBench);
		this.getChildren().add(craftResult);
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
