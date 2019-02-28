package crafting;

import java.io.File;
import java.util.HashMap;

public class Items {
	
	public HashMap<String, Item> MapItems;
	
	public Items() {
		this.MapItems = new HashMap<>();

		File dirItems = new File("img/items/");
		File[] listeImgItems = dirItems.listFiles();
		
		for (File file : listeImgItems) {
			String nom = file.getName().split("\\.")[0];
			this.MapItems.put(nom, new Item(nom));
		}
	}

}
