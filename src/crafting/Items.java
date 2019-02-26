package crafting;

import java.io.File;
import java.util.HashMap;

public class Items {
	
	public HashMap MapItems;
	
	public Items() {
		File dirItems = new File("img/items/");
		File[] listeImgItems = dirItems.listFiles();
		this.MapItems = new HashMap();
		for (File file : listeImgItems) {
			String nom = file.getName().split("\\.")[0];
			this.MapItems.put(nom, new Item(nom));
		}
	}

}
