package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.*;

public final class CraftDB {
	
	ArrayList<HashMap<String,InternalCraft>> craftDB;
	
	private class InternalCraft{
		@SuppressWarnings("unused")
		int quantity;
		@SuppressWarnings("unused")
		Item[][] recipe;
	}
	
	@SuppressWarnings("static-access")
	public CraftDB() throws IOException {
		this.craftDB = new ArrayList<HashMap<String,InternalCraft>>();
		// 0: 11
		// 1: 22
		// 2: 33
		// 3: 12
		// 4: 23
		// 5: 13
		// 6: 32
		int loaded = 0;		// Number of Crafts loaded
		File craftJson = new File("res/CraftDB.json");
		InputStream is = new FileInputStream(craftJson);
		JSONTokener tokens = new JSONTokener(is);
		JSONObject craftDBJson = new JSONObject(tokens);
		InternalCraft ic = new InternalCraft();
		HashMap<String, InternalCraft> hashMap = null;
		ItemDB itemDB = new ItemDB();
		for (Iterator<String> iterator = craftDBJson.keys(); iterator.hasNext();) {
			String key = iterator.next();
			if (key.length()==2) {
				JSONTokener token = new JSONTokener(craftDBJson.get(key).toString());
				JSONObject tokenobj = new JSONObject(token);
				hashMap = new HashMap<String, InternalCraft>();
				for (Iterator<String> iterator2 = tokenobj.keys(); iterator2.hasNext();) {
					String key2 = (String) iterator2.next();
					JSONTokener token2 = new JSONTokener(tokenobj.get(key2).toString());
					JSONObject token2obj = new JSONObject(token2);
					JSONArray recipeA = (JSONArray) token2obj.get("recipe");
					Item[][] recipe = new Item[recipeA.length()][recipeA.getJSONArray(0).length()];
					for (int i = 0; i < recipeA.length(); i++) {
						for (int j = 0; j < recipeA.getJSONArray(0).length(); j++) {
							String ItemName = (String) recipeA.getJSONArray(0).get(i);
							System.out.println(ItemName);
							recipe[i][j]=itemDB.getItem(ItemName);
						}
					}
					ic.quantity = token2obj.getInt("quantity");
					ic.recipe = recipe;
					hashMap.put(key2.toString(), ic);
					loaded++;
				}
			}
			this.craftDB.add(hashMap);
		}
		System.out.println("Crafts database: loaded "+loaded+" crafts");
	}

	public static Item craftExists(Craft combi) {
		// TODO check if craft is in the json
		return null;
	}
	
}
