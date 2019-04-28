package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import view.CraftingTable;
import view.Inventory;
import view.ItemGraphicsFactory;
import view.PlayerHand;
import view.View;

public class Model {
	public static final String APP_ICON = "res/graphics/icon.png";
	public static final String ITEMS_PATH = "res/graphics/items/";
	public static final String BLOCKS_PATH = "res/graphics/blocks/";
	public static final String CSS_PATH = "res/css/";
	public static final Image BACKGROUND_IMAGE = new Image("file:res/graphics/crafting_background.png");
	public static final Color FONT_COLOR = Color.rgb(240, 240, 240);
	public static Font FONT;

	public ItemGraphicsFactory itemGraphicsFactory;
	public Inventory inventory;
	public CraftingTable craftingTable;
	public PlayerHand playerHand;
	public boolean shiftIsDown = false;

	private ItemDB itemDB;
	private CraftDB craftDB;
	private View view = null;
	private Controller controller = null;

	public Model() {
	}

	public void init(View view, Controller controller) {
		this.view = view;
		this.controller = controller;

		FONT = Font.getDefault();
		try {
			FONT = Font.loadFont(new FileInputStream(new File("res/minecraft_font.ttf")), 16);
		} catch (FileNotFoundException e) {
			System.err.println("Could not load main font: " + e.getMessage());
		}

		this.itemGraphicsFactory = new ItemGraphicsFactory();
		this.itemDB = new ItemDB();
		this.inventory = new Inventory(controller);
		this.craftingTable = new CraftingTable(controller);
		this.playerHand = new PlayerHand(controller);

		try {
			this.craftDB = new CraftDB();
		} catch (IOException e) {
			throw new RuntimeException("Failed to initialize the craft database: " + e.getMessage());
		}
	}
}
