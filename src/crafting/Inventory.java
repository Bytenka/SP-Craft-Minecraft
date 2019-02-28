package crafting;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;

public class Inventory extends Container {
	private static final int ROWS = 3;
	private static final int COLS = 9;
	private static final int GAP_SIZE = 3;

	private Slot[][] inv;

	public Inventory() {
		this.setSize(COLS * Slot.SIZE + (COLS-1) * GAP_SIZE, ROWS * Slot.SIZE + (ROWS-1) * GAP_SIZE);
		this.setLayout(new GridLayout(ROWS, COLS, GAP_SIZE, GAP_SIZE));

		// Set and fill the inventory
		this.inv = new Slot[ROWS][COLS];
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++) {
				inv[r][c] = new Slot();
				this.add("" + r + c, inv[r][c]); // Slot at (3, 7) has name "37"
			}
	}

	public void setSlot(int row, int col, Item obj) {
		this.inv[row][col].setItem(obj);
	}

	@Override
	public String toString() {
		String txt = "";
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				txt += this.inv[i][j].toString();
			}
			txt += "\n";
		}
		return txt;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(168, 168, 168, 100));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		super.paint(g);
	}
}