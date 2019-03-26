package crafting;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class Model {
	public static final String APP_ICON = "res/graphics/icon.png";
	public static final String ITEMS_PATH = "res/graphics/items/";
	public static final Color FONT_COLOR = new Color(240, 240, 240);
	public static Font FONT;

	public Inventory inventory;
	public ItemDB itemDB;
	public CraftingTable ctable;

	public Model(Controller ctrl) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("res/minecraft_font.ttf"));
			FONT = font.deriveFont(Slot.SIZE/3f);

		} catch (FontFormatException | IOException e) {
			System.err.println("Could not load main font: " + e.getMessage());
		}
		itemDB = new ItemDB();
		inventory = new Inventory(ctrl); // Initialize everything *before* panel
		ctable = new CraftingTable(ctrl);


		// -------------- Filling with items
		inventory.setSlot(0, 0, ItemDB.getItem("pumpkin_pie"), 1);
		inventory.setSlot(0, 1, ItemDB.getItem("potion_bottle_drinkable"), 5);
		inventory.setSlot(0, 2, ItemDB.getItem("fireball"), 200);
		inventory.setSlot(0, 3, ItemDB.getItem("paper"), 2);
		inventory.setSlot(0, 4, ItemDB.getItem("arrow"), 1);
		inventory.setSlot(0, 5, ItemDB.getItem("bread"), 1);
		inventory.setSlot(0, 6, ItemDB.getItem("beef_raw"), 1);
		// -------------- TODO move or remove that
	}

	public static void main(String[] args) {
		Controller ctrl = new Controller();
		Model model = new Model(ctrl);
		new View(model);
	}
}
