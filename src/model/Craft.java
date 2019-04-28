package model;

public class Craft {

	private Item[][] tab;
	
	public Craft (Item[][] arg) {
		// TODO shrink
		Item[][] item = arg;
		
		// Test si des colonnes sont vides
		boolean leftColEmpty = true;
		boolean middleColEmpty = true;
		boolean rightColEmpty = true;
		for (int row = 0; row < 3; row++) {
			if (item[row][0] != null) {
				leftColEmpty = false;
			}
			if (item[row][1] != null) {
				middleColEmpty = false;
			}
			if (item[row][2] != null) {
				rightColEmpty = false;
			}
		}	
		// Si TOUTES les colonnes sont vides, on go a la fin, sinon, on peut enlever des colonnes
		if (!(leftColEmpty && middleColEmpty && rightColEmpty)) {
			
			// Colonne de droite vide
			if (rightColEmpty) {
				//System.out.println("right col empty");
				Item[][] newItem = new Item[3][2];
				for (int row = 0; row < 3; row++) {
					for (int col = 0; col < 2; col++) {
						newItem[row][col] = item[row][col];
					}
				}
				item = newItem;
			}
			
			// Colonne du milieu vide
			if ((middleColEmpty && leftColEmpty) || (middleColEmpty && rightColEmpty)) {
				//System.out.println("middle col empty");
				int nCol = item[0].length;
				Item[][] newItem = new Item[3][nCol-1];
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
			
			// Colonne de gauche vide
			if (leftColEmpty) {
				//System.out.println("left col empty");
				int nCol = item[0].length;
				Item[][] newItem = new Item[3][nCol-1];
				for (int row = 0; row < 3; row++) {
					for (int col = 0; col < nCol; col++) {
						if (col > 0) {
							newItem[row][col-1] = item[row][col];
						}
					}
				}
				item = newItem;
			}
			
			// Test si des lignes sont vides
			int nCol = item[0].length;
			boolean topRowEmpty = true;
			boolean middleRowEmpty = true;
			boolean bottomRowEmpty = true;
			for (int col = 0; col < nCol; col++) {
				if (item[0][col] != null) {
					topRowEmpty = false;
				}
				if (item[1][col] != null) {
					middleRowEmpty = false;
				}
				if (item[2][col] != null) {
					bottomRowEmpty = false;
				}
			}
			
			// Ligne du bas
			if (bottomRowEmpty) {
				//System.out.println("bottom row empty");
				Item[][] newItem = new Item[2][nCol];
				for (int row = 0; row < 2; row++) {
					for (int col = 0; col < nCol; col++) {
						newItem[row][col] = item[row][col];
					}
				}
				item = newItem;
			}
			
			// Ligne du milieu
			if ((middleRowEmpty && topRowEmpty) || (middleRowEmpty && bottomRowEmpty)) {
				//System.out.println("middle row empty");
				int nRow = item.length;
				Item[][] newItem = new Item[nRow-1][nCol];
				for (int row = 0; row < nRow; row++) {
					for (int col = 0; col < nCol; col++) {
						if (row < 1) {
							newItem[row][col] = item[row][col];
						}
						if (row > 1) {
							newItem[row-1][col] = item[row][col];
						}
					}
				}
				item = newItem;
			}
			
			// Ligne du haut
			if (topRowEmpty) {
				//System.out.println("top row empty");
				int nRow = item.length;
				Item[][] newItem = new Item[nRow-1][nCol];
				for (int row = 0; row < nRow; row++) {
					for (int col = 0; col < nCol; col++) {
						if (row > 0) {
							newItem[row-1][col] = item[row][col];
						}
					}
				}
				item = newItem;
			}
			
			this.tab = item;
		}
		else { // si tout est vide
			this.tab = null;
		}
	}
	
	public boolean isNull() {
		return (this.tab == null);
	}
	
	// Returns an empty string if the craft contains nothing
	public String getSizeString() {
		return this.isNull() ? "" : "" + tab[0].length + tab.length;
	}
	
	public Item[][] getItemTab() {
		return tab;
	}
	
	@Override
	public boolean equals(Object obj) {
		Craft c = (Craft) obj;

		if (!this.getSizeString().equals(c.getSizeString()))
			return false;
		
		for (int i = 0; i < c.tab.length; i++) 
			for (int j = 0; j < c.tab[i].length; j++) {
				if (this.tab[i][j] != c.tab[i][j])
					return false;
		}
		return true;
	}
	
	public String toString() {
		if (!this.isNull()) {
			String txt = "[Craft | row:"+this.tab.length+" col:"+this.tab[0].length+" ]\n";
			for (int row = 0; row < this.tab.length; row++) {
				for (int col = 0; col < this.tab[0].length; col++) {
					txt += this.tab[row][col] + "\t";
				}
				txt += "\n";
			}
			return txt;
		}
		else {
			return "craft is null";
		}
	}
}
