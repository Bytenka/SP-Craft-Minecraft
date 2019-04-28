package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Item;
import model.ItemDB;
import model.Slot;
import model.SlotsTable;

public class Inventory extends SlotsTable {
	private static final int ROWS = 3;
	private static final int COLS = 9;
	private static final String BACKGROUND_IMAGE_PATH = "res/graphics/ui/inventory.png";

	private ImageView backgroundImage;

	public Inventory(Controller controller) {
		super(ROWS, COLS, controller);
		try {
			Image bi = new Image(new FileInputStream(new File(BACKGROUND_IMAGE_PATH)), this.getWidth() + 4,
					this.getHeight() + 4, false, false);
			backgroundImage = new ImageView(bi);
			backgroundImage.setLayoutX(-2);
			backgroundImage.setLayoutY(-2);
			this.getChildren().add(0, backgroundImage); // Adds behind everything

		} catch (FileNotFoundException e) {
			System.err.println("Could not set inventory backgr5ound");
		}

		// TODO Modify/remove that
		this.autoPutSlot(ItemDB.getItem("stick"), 12);
		this.autoPutSlot(ItemDB.getItem("string"), 12);
		this.autoPutSlot(ItemDB.getItem("planks_block"), 64);
		this.autoPutSlot(ItemDB.getItem("iron_ingot"), 54);
		this.autoPutSlot(ItemDB.getItem("redstone_dust"), 40);
		this.autoPutSlot(ItemDB.getItem("gold_ingot"), 9);
		this.autoPutSlot(ItemDB.getItem("carrot"), 3);
		this.autoPutSlot(ItemDB.getItem("blaze_rod"), 9);
		this.autoPutSlot(ItemDB.getItem("cobblestone_block"), 200);
		this.autoPutSlot(ItemDB.getItem("diamond"), 84);
		// -------------------- //

	}

	public boolean autoPutSlot(Item item, int quantity) {
		Slot stackHere = null;
		Slot emptyHere = null;

		for (Slot[] sl : this.getSlots())
			for (Slot s : sl) {
				if (stackHere == null && s.getItem() == item)
					stackHere = s;

				else if (emptyHere == null && s.isEmpty())
					emptyHere = s;
			}

		if (stackHere != null) {
			if (stackHere.put(item, quantity))
				return true;
		}

		if (emptyHere != null)
			if (emptyHere.put(item, quantity))
				return true;

		return false;
	}
	
	public boolean autoRemoveSlot(Item item, int quantity) {
		int qToGo = quantity;
		for (Slot[] sl : this.getSlots())
			for (Slot s : sl) {
				if (s.getItem() == item) {
					if (qToGo >  s.getQuantity()) {
						qToGo -= s.getQuantity();
						s.removeAll();
					} else {
						s.remove(qToGo);
						return true;
					}
				}
			}
		return false;
	}
	
	public int getQuantityOfItem(Item item) {
		int q = 0;
		for (Slot[] sl : this.getSlots())
			for (Slot s : sl) {
				if (s.getItem() == item)
					q += s.getQuantity();
			}
		return q;
	}
}
