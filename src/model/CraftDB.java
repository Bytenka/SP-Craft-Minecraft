package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;

import org.json.*;

public final class CraftDB {
	
	public CraftDB() throws IOException {
		// TODO remplacer String[][] par Item[][] (voir getItem dans ItemDB)
		HashMap<String,HashMap<String,String[][]>> craftDB = new HashMap<String, HashMap<String, String[][]>>();
		File craftJson = new File("res/CraftDB.json");
		InputStream is = new FileInputStream(craftJson);
		JSONTokener tokens = new JSONTokener(is);
		JSONObject craftDBJson = new JSONObject(tokens);
		for (Iterator<String> iterator = craftDBJson.keys(); iterator.hasNext();) {
			String key = iterator.next();
			if (key.length()==2) {
				JSONTokener token = new JSONTokener(craftDBJson.get(key).toString());
				JSONObject tokenobj = new JSONObject(token);
				craftDB.put(key, (HashMap)tokenobj.toMap());
			}
		}
	}

	public static Item craftExists(Craft combi) {
		// TODO check if craft is in the json
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		CraftDB craftDB = new CraftDB();
	}
}
