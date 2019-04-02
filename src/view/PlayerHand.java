package view;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PlayerHand extends Pane {
	private Item item;
	private int quantity;
	private Group itemGraphics;

	public PlayerHand() {
		super();
		itemGraphics = new Group();
		this.getChildren().add(itemGraphics);
		clear();
		this.setMouseTransparent(true);
	}

	public void clear() {
		item = null;
		quantity = 0;
		itemGraphics.getChildren().clear();
	}

	public void setItem(Item item, int quantity) {
		this.clear();
		this.item = item;
		this.quantity = quantity;
		
		if (item.getIs3D())
			itemGraphics.getChildren().add(ItemGraphicsFactory.make3D(item.getImage()));
		else
		    itemGraphics.getChildren().add(ItemGraphicsFactory.make2D(item.getImage()));
	}
	
	public void setItemFromSlot(Slot slot) {
		this.setItem(slot.getItem(), slot.getQuantity());
	}

	public Item getItem() {
		return item;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public boolean isEmpty() {
		return item == null;
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