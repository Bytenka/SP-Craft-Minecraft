package _do_not_use;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class ItemDB {
	private static HashMap<String, Item> m_items;

	public ItemDB() {
		m_items = new HashMap<>();

		File dirItems = new File(Model.ITEMS_PATH);
		File[] listeImgItems = dirItems.listFiles();

		int notLoaded = 0;
		for (File file : listeImgItems) {
			String nom = file.getName().split("\\.")[0];
			try {
				m_items.put(nom, new Item(nom, file));
			} catch (IOException e) {
				System.err.println("Could not load Item \"" + nom + "\": " + e.getMessage());
				notLoaded++;
			}
		}
		System.out.println("ItemBD: Loaded " + (listeImgItems.length - notLoaded) + " items");
	}

	public static HashMap<String, Item> getItems() {
		return m_items;
	}

	public static Item getItem(String name) {
		Item i = m_items.get(name);
		if (i == null)
			throw new RuntimeException("Item \"" + name + "\" does not exist!");
		return i;
	}
}
