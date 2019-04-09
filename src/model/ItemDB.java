package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;

public final class ItemDB {
	private static HashMap<String, Item> items;

	public ItemDB() {
		items = new HashMap<>();

		File dirItems = new File(Model.ITEMS_PATH);
		File dirBlocks = new File(Model.BLOCKS_PATH);

		int loaded = 0;
		int notLoaded = 0;
		// Loading items
		{
			File[] listeImg = dirItems.listFiles();

			for (File file : listeImg) {
				String nom = file.getName().split("\\.")[0];

				try {
					Item item = new Item(nom, new FileInputStream(file), false);
					ItemDB.addItem(nom, item);
					loaded++;

				} catch (IOException | RuntimeException e) {
					System.err.println("Could not load item: " + e.getMessage());
					notLoaded++;
				}
			}
		}

		// Loading blocks
		{
			class BlockInProgress {
				String name;
				FileInputStream fileTop = null;
				FileInputStream fileFront = null;
				FileInputStream fileSide = null;
			}
			
			File[] listeImg = dirBlocks.listFiles();
			HashMap<String, BlockInProgress> itemsInProgress = new HashMap<>();

			for (File file : listeImg) {
				String nom = file.getName().split("\\.")[0];
				
				String[] oui = nom.split("_");
				String posAttribute = oui[oui.length-1];
				
				// TODO
				
				
				try {
					Item item = new Item(nom, new FileInputStream(file), true);
					ItemDB.addItem(nom, item);
					loaded++;

				} catch (IOException | RuntimeException e) {
					System.err.println("Could not load block: " + e.getMessage());
					notLoaded++;
				}
			}
			
			// Add multi-faces blocks
			for (BlockInProgress b : itemsInProgress.values()) {
				if (b.fileTop == null || b.fileFront == null || b.fileSide == null)
					System.out.println("Block \"" + b.name +"\" is incomplete! Fire the cookie");
				else {
					Item i = new Item(b.name, b.fileFront, b.fileSide, b.fileTop, true);
					ItemDB.addItem(b.name, i);
				}
			}
		}

		System.out.println("Items database: loaded " + (loaded - notLoaded) + " objects");
	}

	public static void addItem(String name, Item item) {
		if (items.containsKey(name))
			throw new RuntimeException("Object with name \"" + name + "\" already exists in the database!");

		items.put(name, item);
	}

	public static HashMap<String, Item> getItems() {
		return items;
	}

	public static Item getItem(String name) {
		Item i = items.get(name);
		if (i == null)
			throw new RuntimeException("Item \"" + name + "\" does not exist!");
		return i;
	}
}
