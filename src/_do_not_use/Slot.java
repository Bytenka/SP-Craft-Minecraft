package _do_not_use;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.JComponent;

public class Slot extends JComponent implements MouseListener {
	public enum SlotEvent {
		MOUSE_ENTERED, MOUSE_EXITED, MOUSE_CLICKED, NONE
	};

	public class SlotObservable extends Observable {
		private MouseEvent m_mouseEvent;
		private SlotEvent m_event;

		public SlotObservable() {
			this.reset();
		}

		public void setEvent(SlotEvent event, MouseEvent mouseEvent) {
			m_mouseEvent = mouseEvent;
			m_event = event;
			this.setChanged();
		}

		public SlotEvent getEvent() {
			return m_event;
		}

		public void reset() {
			m_mouseEvent = null;
			m_event = SlotEvent.NONE;
		}
	}

	private static final Color HIGHLIGHTED_COLOR = new Color(255, 255, 255, 80);
	public static int SIZE = 50;

	protected Item item;
	protected int quantity;
	
	private boolean m_isSelected = false;
	private SlotObservable m_slotObservable;

	public Slot(Controller ctrl) {
		this.item = null;
		this.quantity = 0;
		this.addMouseListener(this);
		m_slotObservable = new SlotObservable();
		m_slotObservable.addObserver(ctrl);
	}

	public void setItem(Item item, int quantity) { // This method override the item (no checks)
		this.item = item;
		this.quantity = quantity;
	}

	public Item getItem() {
		return this.item;
	}

	public void clear() {
		this.item = null;
		this.quantity = 0;
	}

	public void setQuantity(int quantity) {
		if (quantity < 1)
			throw new RuntimeException("Quantity cannot be < 1");
		this.quantity = quantity;
	}

	public void addQuantity(int quantity) {
		if (quantity < 0)
			throw new RuntimeException("Cannot add negative quantity");
		this.quantity += quantity;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setIsSelected(boolean status) {
		m_isSelected = status;
	}

	public boolean getIsSelected() {
		return m_isSelected;
	}

	public boolean isEmpty() {
		return this.item == null;
	}

	public boolean putItem(Item item, int quantity) { // This method TRIES to put the item, and handles stacking
		if (this.isEmpty()) {
			this.setItem(item, quantity);
			return true;
			
		} else if (this.item.equals(item)) {
			this.addQuantity(quantity);
			return true;
		}
		return false;
	}

	public static void swap(Slot slot1, Slot slot2) {
		Item tempI = slot2.item;
		int tempQ = slot2.quantity;

		slot2.setItem(slot1.item, slot1.quantity);
		slot1.setItem(tempI, tempQ);
	}

	@Override
	public void paint(Graphics g) {
		// Display the item image
		if (this.item != null)
			g.drawImage(this.item.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

		// Display the item quantity
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

		// Visual feedback is the item is hovered
		if (m_isSelected) {
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
		m_slotObservable.setEvent(SlotEvent.MOUSE_ENTERED, arg0);
		m_slotObservable.notifyObservers(this);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		m_slotObservable.setEvent(SlotEvent.MOUSE_EXITED, arg0);
		m_slotObservable.notifyObservers(this);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		m_slotObservable.setEvent(SlotEvent.MOUSE_CLICKED, arg0);
		m_slotObservable.notifyObservers(this);
	}
}
