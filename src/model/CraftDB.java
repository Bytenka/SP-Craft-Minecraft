package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.*;

public final class CraftDB {
	
	ArrayList<HashMap<String,InternalCraft>> craftDB;
	
	private class InternalCraft{
		int quantity;
		Item[][] recipe;
	}
	
	public CraftDB() throws IOException {
		// TODO remplacer String[][] par Item[][] (voir getItem dans ItemDB)
		// HashMap<String,HashMap<String,String[][]>> craftDB = new HashMap<String, HashMap<String, String[][]>>();
		this.craftDB = new ArrayList<HashMap<String,InternalCraft>>();
		// 0: 11
		// 1: 22
		// 2: 33
		// 3: 12
		// 4: 23
		// 5: 13
		// 6: 32
		File craftJson = new File("res/CraftDB2.json");
		InputStream is = new FileInputStream(craftJson);
		JSONTokener tokens = new JSONTokener(is);
		JSONObject craftDBJson = new JSONObject(tokens);
		InternalCraft ic = new InternalCraft();
		HashMap<String, InternalCraft> hashMap = null;
		for (Iterator<String> iterator = craftDBJson.keys(); iterator.hasNext();) {
			String key = iterator.next();
			if (key.length()==2) {
				JSONTokener token = new JSONTokener(craftDBJson.get(key).toString());
				JSONObject tokenobj = new JSONObject(token);
				hashMap = new HashMap<String, InternalCraft>();
				for (Iterator<String> iterator2 = tokenobj.keys(); iterator2.hasNext();) {
					String key2 = (String) iterator2.next();
					System.out.println(key2.toString());
					//System.out.println(tokenobj.get(key2));
					JSONTokener token2 = new JSONTokener(tokenobj.get(key2).toString());
					JSONObject token2obj = new JSONObject(token2);
					//System.out.println(token2obj.toString());
					//System.out.println(token2obj.getInt("quantity"));
					System.out.println(token2obj.get("recipe"));
					ic.quantity = token2obj.getInt("quantity");
					JSONArray recipeA = (JSONArray) token2obj.get("recipe");
					// TODO JSONArray to Item[][]
					for (int i = 0; i < recipeA.length(); i++) {
						for (int j = 0; j < recipeA.getJSONArray(0).length(); j++) {
							// TODO
							System.out.println(recipeA.getJSONArray(0).get(i));
						}
					}
					
					
					//ic.recipe = recipe;
					
					//hashMap.put(key2.toString(), ic);
					
					//HashMap<String, InternalCraft> hashMap = (HashMap<String, InternalCraft>) iterator2.next();
					
				}
				
				//craftDB.add((HashMap)tokenobj.toMap());
				//craftDB.put(key, (HashMap)tokenobj.toMap());
			}
			this.craftDB.add(hashMap);
		}
		//System.out.println(craftDB.toString());
	}

	public static Item craftExists(Craft combi) {
		// TODO check if craft is in the json
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		CraftDB craftDB = new CraftDB();
	}
}
