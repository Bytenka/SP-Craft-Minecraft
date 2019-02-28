package crafting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class Slot extends JComponent implements MouseListener {
	public static int SIZE = 50;
	private static final Color DEFAULT_COLOR = new Color(135, 135, 135, 100);
	private static final Color HIGHLIGHTED_COLOR = new Color(255, 255, 255, 100);

	private Item item;
	private Color color;

	public Slot() {
		this.item = null;
		this.color = DEFAULT_COLOR;
		this.addMouseListener(this);
	}

	public Item getItem() {
		return this.item;
	}

	public boolean isEmpty() {
		return this.item == null;
	}

	public boolean putItem(Item i) { // TODO stacking of items
		if (this.isEmpty()) {
			this.item = i;
			return true;
		}
		return false;
	}

	public void setItem(Item i) {
		this.item = i;
	}

	public void removeItem() {
		this.item = null;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		super.paint(g);
	}

	@Override
	public String toString() {
		return this.item.toString();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.color = HIGHLIGHTED_COLOR;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.color = DEFAULT_COLOR;
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
	}
}
