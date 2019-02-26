package crafting;

public class Inventory {
	
	private Slot[][] inv;
	private static final int HEIGHT = 3;
	private static final int WIDTH = 9;
	
	public Inventory() {
		this.inv = new Slot[3][9];
	}
	
	public void setSlot(int i, int j, Item obj) {
		this.inv[i][j].setItem(obj);
	}
	
	@Override
	public String toString() {
		String txt = "";
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				txt += this.inv[i][j];
			}
			txt += "\n";
		}
		return txt;
	}
}