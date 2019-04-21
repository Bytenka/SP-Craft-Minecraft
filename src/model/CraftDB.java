package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.util.Pair;

public final class CraftDB {
	
	private static HashMap<Item, InternalCraft>[] craftDB;
	
	private class InternalCraft {
		Item item;
		int quantity;
		Craft recipe;
	}
	
	@SuppressWarnings("unchecked") // The cast is safe for this.craftDB
	public CraftDB() throws IOException {
		// Initialize the array
		craftDB = (HashMap<Item, InternalCraft>[]) new HashMap[9];
		for (int i = 0; i < craftDB.length; i++) {
			craftDB[i] = new HashMap<Item, InternalCraft>();
		}

		int loaded = 0;		// Number of crafts loaded
		InputStream jsonCraftsFile = new FileInputStream(new File("res/CraftDB.json"));
		
		JSONTokener tokens = new JSONTokener(jsonCraftsFile);
		JSONObject topLevelDB = new JSONObject(tokens);
		
		// Get the top level objects, which are the length "11", "12" and so on
		Iterator<String> lengthsIt= topLevelDB.keys();
		while (lengthsIt.hasNext()) {
			String lengthStr = lengthsIt.next();
			JSONObject lengthObj = topLevelDB.getJSONObject(lengthStr);
			
			// Get the crafts in that object
			Iterator<String> craftIt = lengthObj.keys();
			while (craftIt.hasNext()) {
				String craftStr = craftIt.next();
				try {
					JSONObject craft = lengthObj.getJSONObject(craftStr);
					
					InternalCraft internalCraft = new InternalCraft();
					internalCraft.item = ItemDB.getItem(craftStr);;
					internalCraft.quantity = craft.getInt("quantity");
					
					JSONArray recipeJSON = craft.getJSONArray("recipe");
					Item[][] recipeTab = new Item[3][3];
					
					// Iterate on the 2D JSONArray to build internalCraft.recipe
					for (int r = 0; r < recipeJSON.length(); r++) {
						JSONArray rowArray = recipeJSON.getJSONArray(r);
						
						for (int c = 0; c < rowArray.length(); c++) {
							String itemStr = rowArray.getString(c);
							recipeTab[r][c] = itemStr.isEmpty() ? null : ItemDB.getItem(itemStr);
						}
					}
					
					internalCraft.recipe = new Craft(recipeTab);
					craftDB[stringSizeToIndex(lengthStr)].put(ItemDB.getItem(craftStr), internalCraft);		
					loaded++;
					
				} catch (RuntimeException e) {
					System.err.println("Unable to load craft for object " + craftStr + ": " + e.getMessage());
				}
			}
		}
		
		System.out.println("Crafts database: loaded "+loaded+" crafts");
	}

	public static Pair<Item, Integer> getItemFromCraft(Craft combi) {
		if (!combi.isNull()) {
			HashMap<Item, InternalCraft> hm = craftDB[stringSizeToIndex(combi.getSizeString())];
	
			for (InternalCraft c :  hm.values()) {
				if (c.recipe.equals(combi))
					return new Pair<>(c.item, c.quantity);
			}
		}
		return null;
	}
	
	private static int stringSizeToIndex(String size) {
		switch (size) {
			case "11": return 0; 
			case "12": return 1; 
			case "13": return 2; 
			case "21": return 3; 
			case "22": return 4; 
			case "23": return 5; 
			case "31": return 6; 
			case "32": return 7; 
			case "33": return 8;
			default: throw new RuntimeException("No matching index for size " + size);
		}
	}
	
}
