package model;

import java.util.Collection;
import java.util.Iterator;

import controller.Controller;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class SlotsTable extends Pane {
	public static final int GAP_SIZE = 5;

	private TilePane layout;
	protected Slot[][] slots;

	public SlotsTable(int rows, int cols, Controller controller) {
		if (rows < 0 || cols < 0)
			throw new RuntimeException("Cannot create slot table with " + rows + " rows and " + cols + " columns");

		layout = new TilePane();
		layout.setOrientation(Orientation.HORIZONTAL);
		layout.setTileAlignment(Pos.TOP_LEFT);
		layout.setHgap(GAP_SIZE);
		layout.setVgap(GAP_SIZE);
		layout.setPrefRows(rows);
		layout.setPrefColumns(cols);
		setWidth(Slot.SIZE * cols + GAP_SIZE * (cols - 1));
		setHeight(Slot.SIZE * rows + GAP_SIZE * (rows - 1));

		slots = new Slot[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++) {
				Slot s = new Slot(controller);
				slots[r][c] = s;
				layout.getChildren().add(s);
			}

		this.getChildren().add(layout);
	}

	public void setSlot(int row, int col, Item item, int quantity) {
		if (!slots[row][col].putItem(item, quantity))
			throw new RuntimeException("Unable to set slot [" + row + ", " + col + "]");
	}

	// Return true if every item has found a place
	public boolean populate(Collection<Item> items, int quantityForEach) {
		Iterator<Item> i = items.iterator();
		for (Slot[] sl : slots)
			for (Slot s : sl) {
				if (i.hasNext())
					s.replaceItem(i.next(), quantityForEach);
			}

		return !i.hasNext();
	}

	public Slot getSlot(int row, int col) {
		return slots[row][col];
	}

	public Slot[][] getSlots() {
		return slots;
	}

	public int getRows() {
		return layout.getPrefRows();
	}

	public int getCols() {
		return layout.getPrefColumns();
	}
}
