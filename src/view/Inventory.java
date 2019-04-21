package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ItemDB;
import model.SlotsTable;

public class Inventory extends SlotsTable {
	private static final int ROWS = 3;
	private static final int COLS = 9;
	private static final String BACKGROUND_IMAGE_PATH = "res/graphics/ui/inventory.png";

	private ImageView backgroundImage;

	public Inventory(Controller controller) {
		super(ROWS, COLS, controller);		
		try {
			Image bi = new Image(
					new FileInputStream(new File(BACKGROUND_IMAGE_PATH)), 
					this.getWidth()+4,
					this.getHeight()+4, 
					false, 
					false
			);
			backgroundImage = new ImageView(bi);
			backgroundImage.setLayoutX(-2);
			backgroundImage.setLayoutY(-2);
			this.getChildren().add(0, backgroundImage); // Adds behind everything
			
		} catch (FileNotFoundException e) {
			System.err.println("Could not set inventory backgr5ound");
		}

		// TODO Modify/remove that
		this.putSlot(0, 0, ItemDB.getItem("diamond"), 64);
		this.putSlot(0, 1, ItemDB.getItem("potion_bottle_empty"), 5);
		this.putSlot(0, 2, ItemDB.getItem("ender_eye"), 40);
		this.putSlot(0, 3, ItemDB.getItem("planks_block"), 82);
		this.putSlot(0, 4, ItemDB.getItem("emerald_block"), 5);
		this.putSlot(0, 5, ItemDB.getItem("stick"), 3);
		this.putSlot(0, 6, ItemDB.getItem("string"), 3);
		//this.setSlot(0, 7, ItemDB.get, quantity);
		// -------------------- //
		
	}
}
