package _do_not_use;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CraftingTable extends Container {
	private static final int ROWS = 3;
	private static final int COLS = 3;
	private static final int GAP_SIZE = 5;
	private static final String BACKGROUND_IMAGE_PATH = "res/graphics/ui/table.png"; //TODO background
	
	//private Slot[][] tab;
	private Container table;
	private Container res;
	
	private BufferedImage backgroundImage;
	
	public CraftingTable(Controller ctrl) {
		this.setBackground(Color.pink);
		this.setVisible(true);
		
		// Do not use layouts for this (for now) TODO
		this.setLayout(null);
		
		this.table = new Table(ctrl);
		this.res = new CraftingRes(ctrl);
		
		this.table.setLocation(50, 50);
		this.add(this.table);
		
		this.res.setLocation(300, 50);
		this.add(this.res);
	}
	
	public class Table extends Container {
		private Slot[][] tab;
		
		public Table(Controller ctrl) {
			this.setVisible(true);
			try {
				backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
			} catch (IOException e) {
				System.err.println("Could not load background image: " + e.getMessage());
				backgroundImage = null;
			}
			
			// Set the size according to the slots
			
			this.setSize(COLS * Slot.SIZE + (COLS - 1) * GAP_SIZE, ROWS * Slot.SIZE + (ROWS - 1) * GAP_SIZE);
			this.setLayout(new GridLayout(ROWS, COLS, GAP_SIZE, GAP_SIZE));
					
			// Set and fill the table
			this.tab = new Slot[ROWS][COLS];
			for (int r = 0; r < ROWS; r++)
				for (int c = 0; c < COLS; c++) {
					this.tab[r][c] = new Slot(ctrl);
					this.add("" + r + c, tab[r][c]); // Slot at (3, 2) has name "32"
				}
		}
		@Override
		public void paint(Graphics g) {
			if (backgroundImage != null)
				g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

			super.paint(g);
		}
	}
	
	
	
	public class CraftingRes extends Container {
		
		private Slot res;
		public CraftingRes(Controller ctrl) {
			this.setVisible(true);
			try {
				backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
			} catch (IOException e) {
				System.err.println("Could not load background image: " + e.getMessage());
				backgroundImage = null;
			}
			
			// Set the size according to the slots
			
			this.setSize(Slot.SIZE, Slot.SIZE);
			this.setLayout(null); //TODO
			this.setLocation(300, 50);
			
			this.res = new Slot(ctrl);
			
		}
		
		public void setRes(Item obj, int quantity) {
			this.res.setItem(obj, quantity);
		}
		
		
		@Override
		public void paint(Graphics g) {
			if (backgroundImage != null)
				g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

			super.paint(g);
		}
	}
	
	// TEST
	public void setRes(Item obj, int quantity) {
		((CraftingRes) this.res).setRes(obj, quantity);
	}
	
	public Container getTab() {
		return this.table;
	}
	
	public Container getRes() {
		return this.res;
	}
	
	@Override
	public void paint(Graphics g) {
		if (backgroundImage != null)
			g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

		super.paint(g);
	}
	
}