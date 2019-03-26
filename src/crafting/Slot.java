package crafting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.JComponent;

public class Slot extends JComponent implements MouseListener {
	// We can't do multi inheritance in Java AND the setChanged() method is
	// protected...
	private class InternalObserver extends Observable {
		public void setClicked() {
			this.setChanged();
		}
	}

	private static final Color HIGHLIGHTED_COLOR = new Color(255, 255, 255, 80);
	public static int SIZE = 50;

	private Item item;
	private int quantity;
	private boolean isSelected = false;
	private InternalObserver internalObservable;

	public Slot(Controller ctrl) {
		this.item = null;
		this.quantity = 0;
		this.addMouseListener(this);
		this.internalObservable = new InternalObserver();
		this.internalObservable.addObserver(ctrl);
	}

	public Item getItem() {
		return this.item;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public boolean isEmpty() {
		return this.item == null;
	}

	public boolean putItem(Item i, int quantity) { // This method TRIES to put the item
		if (this.quantity > 0) {
			if (this.item.equals(i)) {
				this.quantity += quantity;
				return true;
			}

			if (this.isEmpty()) {
				this.item = i;
				this.quantity = quantity;
				return true;
			}
		}
		return false;
	}

	public void setItem(Item i, int quantity) { // This method override the item (no checks)
		this.item = i;
		this.quantity = quantity;
	}

	public void clear() {
		this.item = null;
		this.quantity = 0;
	}

	public static void swapItems(Slot item1, Slot item2) {
		Item tempItem = item2.item;
		int tempQuantity = item2.quantity;
		
		item2.setItem(item1.item, item1.quantity);
		item1.setItem(tempItem, tempQuantity);
	}

	@Override
	public void paint(Graphics g) {
		if (this.item != null)
			g.drawImage(this.item.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

		if (this.quantity > 1) {
			g.setFont(Model.FONT);

			// Draw quantity with a shadow to make it readable
			int textX = 5;
			int textY = this.getHeight() - 5;
			g.setColor(Color.darkGray);
			g.drawString(String.valueOf(this.quantity), textX - 2, textY + 2);
			g.setColor(Model.FONT_COLOR);
			g.drawString(String.valueOf(this.quantity), textX, textY);
		}

		if (this.isSelected) {
			g.setColor(HIGHLIGHTED_COLOR);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

		super.paint(g);
	}

	@Override
	public String toString() {
		if (this.item != null)
			return this.item.toString() + ": " + this.quantity;
		return "none";
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.isSelected = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.isSelected = false;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.internalObservable.setClicked();
		this.internalObservable.notifyObservers(this);
	}
}
