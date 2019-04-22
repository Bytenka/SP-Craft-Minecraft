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

	public static enum SlotType {
		LIST, // Will respond to click events, but the item can't be changed by the user
		CRAFT_RESULT, // User can pick up the item, but can't put anything
		REGULAR // User can pick up and put items
	};

	private Item item;
	private int quantity;
	private Button button;
	private DrawableItem itemGraphics;
	private SlotType type;
	
	public Slot(Controller controller) {
		this(SlotType.REGULAR, controller);
	}

	public Slot(SlotType type, Controller controller) {
		super();
		this.type = type;

		itemGraphics = new DrawableItem();
		this.getChildren().add(itemGraphics);

		button = new Button();
		button.setPrefSize(SIZE, SIZE);
		button.getStyleClass().add("itemSlot"); // Defined in crafting.css
		this.getChildren().add(button);

		clear();

		initializeEventHandelers(controller);
	}
	
	public DrawableItem getDrawableItem() {
		return this.itemGraphics;
	}

	public void setType(SlotType type) {
		this.type = type;
	}
	
	public SlotType getType() {
		return this.type;
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

	// Support stacking if possible
	public boolean put(Item item, int quantity) {
		if (type == SlotType.REGULAR && item != null && quantity > 0) {
			if (this.isEmpty()) {
				this.setItem(item);
				this.setQuantity(quantity);
				return true;
			} else if (item.equals(this.item)) {
				this.setQuantity(this.quantity + quantity);
				return true;
			}
		}
		return false;
	}

	public boolean add(int quantity) {
		if (type == SlotType.REGULAR) {
			if (quantity < 0)
				throw new RuntimeException("Cannot add negative quantity");
			this.setQuantity(this.quantity + quantity);
			return true;
		}
		return false;
	}

	public boolean remove(int quantity) {
		if (type == SlotType.REGULAR || type == SlotType.CRAFT_RESULT) {
			if (this.quantity > quantity)
				this.setQuantity(this.quantity - quantity);
			else
				this.clear();
			return true;
		}
		return false;
	}

	public boolean removeAll() {
		if (type == SlotType.REGULAR || type == SlotType.CRAFT_RESULT) {
			this.clear();
			return true;
		}
		return false;
	}

	// ---------------------------------------------------------- //
	
	public void set(Item item, int quantity) {
		this.setItem(item);
		this.setQuantity(quantity);
	}

	public void setItem(Item item) {
		this.item = item;
		itemGraphics.set(this.item);
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		itemGraphics.setQuantity(quantity);
	}

	public void clear() {
		this.item = null;
		this.quantity = 0;
		itemGraphics.clear();
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

		button.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.slotMouseEntered(Slot.this, event);
			}
		});

		button.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.slotMouseExited(Slot.this, event);
			}
		});
	}
	
	@Override
	public String toString() {
		if (this.item != null)
			return this.item.toString() + ": " + this.quantity;
		return "none";
	}
}
