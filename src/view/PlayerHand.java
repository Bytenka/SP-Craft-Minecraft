package view;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Item;
import model.Slot;

public class PlayerHand extends Pane {
	private Item item;
	private int quantity;
	private DrawableItem itemGraphics;

	public PlayerHand() {
		super();
		this.setMouseTransparent(true);
		itemGraphics = new DrawableItem();
		this.getChildren().add(itemGraphics);
		clear();
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
	
	public void clear() {
			this.item = null;
			this.quantity = 0;
			itemGraphics.clear();
	}
	
	public void addQuantity(int quantity) {
		if (quantity < 0)
			throw new RuntimeException("Cannot add negative quantity");
		this.setQuantity(this.quantity + quantity);
	}
	
	public void removeQuantity(int quantity) {
		if (this.quantity > quantity)
			this.setQuantity(this.quantity - quantity);
		else
			this.clear();
	}
	
	public void replaceItem(Item item, int quantity) {
		if (item == null || quantity <= 0)
			throw new RuntimeException("Could not replace item with " + item.toString() + " | " + quantity);

		this.setItem(item);
		this.setQuantity(quantity);
	}
	
	// Does not make any checks
	private void setItem(Item item) {
		this.item = item;
		itemGraphics.set(this.item);
	}

	// Does not make any checks
	private void setQuantity(int quantity) {
		this.quantity = quantity;
		itemGraphics.setQuantity(quantity);
	}

	public void updatePosition(MouseEvent event) {
		updatePosition(event.getX(), event.getY());
	}

	public void updatePosition(double posX, double posY) {
		this.setLayoutX(posX - (Slot.SIZE  / 2));
		this.setLayoutY(posY - (Slot.SIZE  / 2));
	}
	
	@Override
	public String toString() {
		if (this.item != null)
			return this.item.toString() + ": " + this.quantity;
		return "none";
	}
}