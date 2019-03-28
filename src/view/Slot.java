package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Model;

public class Slot extends Group {
	public static final int SIZE = 50;

	private Item item;
	private int quantity;
	private boolean contentUserModifiable;
	private Button button;
	private Group itemGraphics;
	private Text quantityText;

	public Slot() {
		contentUserModifiable = true;

		itemGraphics = new Group();
		this.getChildren().add(itemGraphics);

		button = new Button();
		button.setPrefSize(SIZE, SIZE);
		button.getStyleClass().add("itemSlot"); // Defined in crafting.css
		this.getChildren().add(button);

		quantityText = new Text();
		quantityText.setLayoutX(3);
		quantityText.setLayoutY(SIZE - 3);
		quantityText.setWrappingWidth(SIZE - 6);
		quantityText.setMouseTransparent(true);
		quantityText.setFont(Model.FONT);
		quantityText.setFill(Color.rgb(240, 240, 240));
		this.getChildren().add(quantityText);

		clear();

		initializeEventHandelers();
	}

	public void setItem(Item item, int quantity) { // TODO this should maybe be private
		this.item = item;

		if (item.getIs3D())
			itemGraphics.getChildren().add(ItemGraphicsFactory.make3D(item.getImage()));
		else
			itemGraphics.getChildren().add(ItemGraphicsFactory.make2D(item.getImage()));

		this.setQuantity(quantity);
	}

	public Item getItem() {
		return this.item;
	}

	public void clear() {
		this.item = null;
		this.quantity = 0;
		this.quantityText.setText("");
		itemGraphics.getChildren().clear();
	}

	public void setQuantity(int quantity) {
		if (quantity < 1)
			throw new RuntimeException("Quantity cannot be < 1");
		this.quantity = quantity;

		if (this.quantity >= 2)
			quantityText.setText("" + this.quantity);

		if (this.quantity > 99)
			quantityText.setText("99+");
	}

	public void addQuantity(int quantity) {
		if (quantity < 0)
			throw new RuntimeException("Cannot add negative quantity");
		this.setQuantity(this.quantity + quantity); // That way, we update quantityText as well
	}

	public int getQuantity() {
		return this.quantity;
	}
	
	public void setContentUserModifiable(boolean value) {
		contentUserModifiable = value;
	}
	
	public boolean getContentUserModifiable() {
		return contentUserModifiable;
	}

	public boolean isEmpty() {
		return this.item == null;
	}

	public boolean putItem(Item item, int quantity) { // This method TRIES to put the item, and handles stacking
		if (contentUserModifiable) {
			if (this.isEmpty()) {
				this.setItem(item, quantity);
				return true;

			} else if (this.item.equals(item)) {
				this.addQuantity(quantity);
				return true;
			}
		}
		return false;
	}

	public static void swap(Slot slot1, Slot slot2) {
		if (slot1.contentUserModifiable && slot2.contentUserModifiable) {
			Item tempI = slot2.item;
			int tempQ = slot2.quantity;

			slot2.setItem(slot1.item, slot1.quantity);
			slot1.setItem(tempI, tempQ);
		} else
			throw new RuntimeException("Cannot swap items: slot content is not user modifiable");
	}

	@Override
	public String toString() {
		if (this.item != null)
			return this.item.toString() + ": " + this.quantity;
		return "none";
	}

	private void initializeEventHandelers() {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(Slot.this.toString());
			}
		});

		button.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			}
		});

		button.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			}
		});
	}
}
