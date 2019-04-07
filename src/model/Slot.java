package model;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import view.DrawableItem;

public class Slot extends Group {
	public static final int SIZE = 50;

	private boolean contentUserSettable;
	private Item item;
	private int quantity;
	private Button button;
	private DrawableItem itemGraphics;

	public Slot(Controller controller) {
		super();
		contentUserSettable = true;

		itemGraphics = new DrawableItem();
		this.getChildren().add(itemGraphics);

		button = new Button();
		button.setPrefSize(SIZE, SIZE);
		button.getStyleClass().add("itemSlot"); // Defined in crafting.css
		this.getChildren().add(button);

		clear();

		initializeEventHandelers(controller);
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

	public void setContentUserSettable(boolean value) {
		contentUserSettable = value;
	}

	public boolean isContentUserSettable() {
		return contentUserSettable;
	}

	public boolean putItem(Item item, int quantity) {
		if (contentUserSettable && item != null && quantity > 0) {
			if (this.isEmpty()) {
				this.setItem(item);
				this.setQuantity(quantity);
				return true;
			} else if (item.equals(this.item)) {
				this.addQuantity(quantity);
				return true;
			}
		}
		return false;
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

	// Should be used if !contentUserModifiable to set the slot's content
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

	@Override
	public String toString() {
		if (this.item != null)
			return this.item.toString() + ": " + this.quantity;
		return "none";
	}

	private void initializeEventHandelers(Controller controller) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			}
		});

		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.slotClicked(Slot.this, event);
			}
		});

		button.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			}
		});
	}
}
