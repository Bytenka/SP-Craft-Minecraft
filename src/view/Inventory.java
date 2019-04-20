package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import controller.Controller;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
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

		// TODO Modify/remove that
		this.setSlot(0, 0, ItemDB.getItem("pumpkin_pie"), 1);
		this.setSlot(0, 1, ItemDB.getItem("potion_bottle_empty"), 5);
		this.setSlot(0, 2, ItemDB.getItem("ender_eye"), 40);
		this.setSlot(0, 3, ItemDB.getItem("ender_eye"), 82);
		this.setSlot(0, 4, ItemDB.getItem("emerald_block"), 5);
		this.setSlot(0, 5, ItemDB.getItem("bread"), 1);
		this.setSlot(0, 6, ItemDB.getItem("beef_raw"), 1);
		//this.setSlot(0, 7, ItemDB.get, quantity);
		// -------------------- //

	}
}
