package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

				public BlockInProgress(String name) {
					this.name = name;
				}
			}

			File[] listeImg = dirBlocks.listFiles();
			HashMap<String, BlockInProgress> itemsInProgress = new HashMap<>();

			for (File file : listeImg) {
				String nom = file.getName().split("\\.")[0];

				int lio_ = nom.lastIndexOf("_");
				String posAttr = nom.substring(lio_ + 1);

				if (posAttr.contentEquals("top") || posAttr.contentEquals("front") || posAttr.contentEquals("side")) {
					nom = nom.substring(0, lio_);
					itemsInProgress.putIfAbsent(nom, new BlockInProgress(nom));

					BlockInProgress block = itemsInProgress.get(nom);
					try {
						switch(posAttr)
						{
						case "top":
							if (block.fileTop == null)
								block.fileTop = new FileInputStream(file);
							break;
						case "front":
							if (block.fileFront == null)
								block.fileFront = new FileInputStream(file);
							break;
						case "side":
							if (block.fileSide == null)
								block.fileSide = new FileInputStream(file);
							break;
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
				} else {		
					try {
						Item item = new Item(nom, new FileInputStream(file), true);
						ItemDB.addItem(nom, item);
						loaded++;
						
					} catch (IOException | RuntimeException e) {
						System.err.println("Could not load block: " + e.getMessage());
						notLoaded++;
					}
				}
			}

			// Add multi-faces blocks
			for (BlockInProgress b : itemsInProgress.values()) {
				if (b.fileTop == null || b.fileFront == null || b.fileSide == null)
					System.err.println("Block \"" + b.name + "\" is incomplete! Fire the cookie");
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
			throw new RuntimeException(
					"Object with name \"" + name + "\" already exists in the database! Files must have unique names.");

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
