package view;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PlayerHand extends Pane {
	private Item item;
	private Group itemGraphics;

	public PlayerHand() {
		super();
		itemGraphics = new Group();
		clearItem();
		this.setMouseTransparent(true);
	}

	public void clearItem() {
		item = null;
		itemGraphics.getChildren().clear();
	}

	public void setItem(Item item) {
		this.item = item;
		if (item.getIs3D())
			itemGraphics.getChildren().add(ItemGraphicsFactory.make3D(item.getImage()));
		else
		    itemGraphics.getChildren().add(ItemGraphicsFactory.make2D(item.getImage()));
	}

	public Item getItem() {
		return item;
	}

	public void updatePosition(MouseEvent event) {
		updatePosition(event.getX(), event.getY());
	}

	public void updatePosition(double posX, double posY) {
		this.setLayoutX(posX - (Slot.SIZE  / 2));
		this.setLayoutY(posY - (Slot.SIZE  / 2));
	}
}