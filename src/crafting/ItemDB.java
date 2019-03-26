package crafting;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class ItemDB {
	private static HashMap<String, Item> MapItems;

	public ItemDB() {
		MapItems = new HashMap<>();

		File dirItems = new File(Model.ITEMS_PATH);
		File[] listeImgItems = dirItems.listFiles();

		int notLoaded = 0;
		for (File file : listeImgItems) {
			String nom = file.getName().split("\\.")[0];
			try {
				MapItems.put(nom, new Item(nom, file));
			} catch (IOException e) {
				System.err.println("Could not load Item \"" + nom + "\": " + e.getMessage());
				notLoaded++;
			}
		}
		System.out.println("ItemBD: Loaded " + (listeImgItems.length - notLoaded) + " items");
	}

	public static Item getItem(String name) {
		Item i = MapItems.get(name);
		if (i == null)
			throw new RuntimeException("Item \"" + name + "\" does not exist!");
		return i;
	}
}
