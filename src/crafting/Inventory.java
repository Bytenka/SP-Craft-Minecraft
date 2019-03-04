package crafting;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Inventory extends Container {
	private static final int ROWS = 3;
	private static final int COLS = 9;
	private static final int GAP_SIZE = 5;
	private static final String BACKGROUND_IMAGE_PATH = "res/graphics/ui/inventory.png";

	private Slot[][] inv;
	private BufferedImage backgroundImage;

	public Inventory() {
		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
		} catch (IOException e) {
			System.err.println("Could not load background image: " + e.getMessage());
			backgroundImage = null;
		}

		// Set the size according to the slots
		this.setSize(COLS * Slot.SIZE + (COLS - 1) * GAP_SIZE, ROWS * Slot.SIZE + (ROWS - 1) * GAP_SIZE);
		this.setLayout(new GridLayout(ROWS, COLS, GAP_SIZE, GAP_SIZE));

		// Set and fill the inventory
		this.inv = new Slot[ROWS][COLS];
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++) {
				inv[r][c] = new Slot();
				this.add("" + r + c, inv[r][c]); // Slot at (3, 7) has name "37"
			}
	}

	public void setSlot(int row, int col, Item obj, int quantity) {
		this.inv[row][col].setItem(obj, quantity);
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
		// g.setColor(new Color(168, 168, 168, 100));
		// g.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (backgroundImage != null)
			g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

		super.paint(g);
	}
}