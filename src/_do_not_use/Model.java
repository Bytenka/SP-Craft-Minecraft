package _do_not_use;

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
	public PlayerHand playerHand;

	private View m_view = null;
	private Controller m_controller = null;

	public Model() {
	}

	public void init(View view, Controller controller) {
		m_view = view;
		m_controller = controller;

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("res/minecraft_font.ttf"));
			FONT = font.deriveFont(Slot.SIZE / 3f);

		} catch (FontFormatException | IOException e) {
			System.err.println("Could not load main font: " + e.getMessage());
		}
		
		itemDB = new ItemDB();
		inventory = new Inventory(m_controller); // Initialize everything *before* panel
		ctable = new CraftingTable(m_controller);
		playerHand = new PlayerHand(m_controller);

		// -------------- Filling with items
		inventory.setSlot(0, 0, ItemDB.getItem("pumpkin_pie"), 1);
		inventory.setSlot(0, 1, ItemDB.getItem("potion_bottle_drinkable"), 5);
		inventory.setSlot(0, 2, ItemDB.getItem("fireball"), 200);
		inventory.setSlot(0, 3, ItemDB.getItem("fireball"), 52);
		inventory.setSlot(0, 4, ItemDB.getItem("arrow"), 1);
		inventory.setSlot(0, 5, ItemDB.getItem("bread"), 1);
		inventory.setSlot(0, 6, ItemDB.getItem("beef_raw"), 1);
		// -------------- TODO move or remove that
	}
/*
	public static void main(String[] args) {
		Model model = new Model();
		View view = new View();
		Controller controller = new Controller();

		model.init(view, controller);
		view.init(model, controller);
		controller.init(model, view);
	}
	*/
}
