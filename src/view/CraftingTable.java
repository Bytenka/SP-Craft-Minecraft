package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import model.Craft;
import model.CraftDB;
import model.Item;
import model.ItemDB;
import model.Slot;
import model.Slot.SlotType;
import model.SlotsTable;

public class CraftingTable extends Pane {
	// Where we make the crafts
	public class CraftingBench extends SlotsTable {
		private static final int ROWS = 3;
		public static final int COLS = 3;

		public CraftingBench(Controller controller) {
			super(ROWS, COLS, controller);
		}
	}
	
	private static final String BACKGROUND_IMAGE_PATH = "res/graphics/ui/crafting_table.png";
	private static final int ARROW_SPACE = 100;
	
	private CraftingBench craftingBench;
	private Slot craftResult;
	private ImageView backgroundImage;
	
	public CraftingTable(Controller controller) {
		craftingBench = new CraftingBench(controller);
		craftResult = new Slot(SlotType.CRAFT_RESULT, controller);
		
		this.setWidth(2 + craftingBench.getWidth() + 2 + ARROW_SPACE + 6 + Slot.SIZE + 6);
		this.setHeight(2 + craftingBench.getHeight() + 2);
		
		try {
			Image bi = new Image(
					new FileInputStream(new File(BACKGROUND_IMAGE_PATH)), 
					this.getWidth(),
					this.getHeight(),
					false, 
					false
			);
			backgroundImage = new ImageView(bi);
			backgroundImage.setLayoutX(-2);
			backgroundImage.setLayoutY(-2);
			this.getChildren().add(0, backgroundImage); // Adds behind everything
		} catch (FileNotFoundException e) {
			System.err.println("Could not set inventory background");
		}
		
		craftResult.setLayoutX(craftingBench.getWidth() + 2 + ARROW_SPACE + 6);
		craftResult.setLayoutY(Slot.SIZE + SlotsTable.GAP_SIZE);
		
		this.getChildren().add(craftingBench);
		this.getChildren().add(craftResult);
	}
	
	public void update() {
		Item[][] tabItems = new Item[CraftingBench.ROWS][CraftingBench.COLS];
		for (int i = 0; i < CraftingBench.ROWS; i++) {
			for (int j = 0; j < CraftingBench.COLS; j++) {
				tabItems[i][j] = craftingBench.getSlot(i, j).getItem();
			}
		}

		Pair<Item, Integer> item = CraftDB.getItemFromCraft(new Craft(tabItems));
		if (item != null) 
			this.setResult(item.getKey(), item.getValue());
		 else 
			this.clearResult();
	}
	
	public CraftingBench getCraftingBench() {
		return this.craftingBench;
	}
	
	public void setResult(Item item, int quantity) {
		craftResult.set(item, quantity);
	}
	
	public void clearResult() {
		craftResult.clear();
	}
	
	public Item getResult() {
		return craftResult.getItem();
	}
}
