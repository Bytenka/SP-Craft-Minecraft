package model;

import view.Item;

public class Craft {

	Item[][] tab;
	
	public Craft (Item[][] arg) {
		// TODO shrink
		Item[][] item = arg;
		
		// shrink des colonnes
		boolean leftColEmpty = true;
		boolean middleColEmpty = true;
		boolean rightColEmpty = true;
		for (int row = 0; row < 3; row++) {
			if (!item[row][0].equals("")) {
				leftColEmpty = false;
			}
			if (!item[row][1].equals("")) {
				middleColEmpty = false;
			}
			if (!item[row][2].equals("")) {
				rightColEmpty = false;
			}
		}
		if (rightColEmpty) {
			Item[][] newItem = new Item[3][2];
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 2; col++) {
					newItem[row][col] = item[row][col];
				}
			}
			item = newItem;
		}
		if ((middleColEmpty && leftColEmpty) || (middleColEmpty && rightColEmpty)) {
			int nCol = item[0].length;
			Item[][] newItem = new Item[3][nCol];
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < nCol; col++) {
					if (col < 1) {
						newItem[row][col] = item[row][col];
					}
					if (col > 1) {
						newItem[row][col-1] = item[row][col];
					}
				}
			}
			item = newItem;
		}
		if (leftColEmpty) {
			int nCol = item[0].length;
			Item[][] newItem = new Item[3][nCol];
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < nCol; col++) {
					if (col > 0) {
						newItem[row][col-1] = item[row][col];
					}
				}
			}
			item = newItem;
		}
		
		//shrink des lignes
		// TODO faut faire ça eh
		
		
		this.tab = item;
	}
}
