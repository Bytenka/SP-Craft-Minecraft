package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

//import com.sun.xml.internal.ws.addressing.model.InvalidAddressingHeaderException;

import view.Item;

public final class ItemDB {
	private static HashMap<String, Item> items;

	public ItemDB() {
		items = new HashMap<>();

		File dirItems = new File(Model.ITEMS_PATH);
		File[] listeImgItems = dirItems.listFiles();

		int notLoaded = 0;
		for (File file : listeImgItems) {
			String nom = file.getName().split("\\.")[0];
			
			try {
				if (items.containsKey(nom))
					throw new RuntimeException("An item with the same name already exists in the database");
				items.put(nom, new Item(nom, new FileInputStream(file), false));
				
			} catch (IOException | RuntimeException e) {
				System.err.println("Could not load Item \"" + nom + "\": " + e.getMessage());
				notLoaded++;
			}
		}
		System.out.println("Items database: loaded " + (listeImgItems.length - notLoaded) + " items");
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
