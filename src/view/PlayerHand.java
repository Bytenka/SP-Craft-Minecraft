package view;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import model.Item;
import model.Model;
import model.Slot;

public class PlayerHand extends Pane {
	private Item item;
	private int quantity;
	private DrawableItem itemGraphics;

	private Pane itemDesc;
	private Rectangle itemDescBG;
	private Text itemDescText;

	public PlayerHand() {
		super();
		this.setMouseTransparent(true);
		itemGraphics = new DrawableItem();
		this.getChildren().add(itemGraphics);
		clear();

		itemDesc = new Pane();
		itemDescText = new Text();

	    itemDescBG = new Rectangle();
		itemDescBG.setStrokeType(StrokeType.OUTSIDE);
		itemDescBG.setStrokeWidth(3);
		itemDescBG.setStroke(Color.rgb(255, 255, 255, 0.6));
		itemDescBG.setArcWidth(10);
		itemDescBG.setArcHeight(10);
		itemDescBG.setFill(Color.rgb(0, 0, 20, 0.6));
		itemDescBG.setLayoutX(Slot.SIZE / 2 + 15);
		itemDescBG.setLayoutY(Slot.SIZE / 2 + 15);
		itemDescBG.setHeight(40);
		
		itemDesc.getChildren().add(itemDescBG);
		
		itemDescText.setFont(Model.FONT);
		itemDescText.setFill(Model.FONT_COLOR);
		itemDescText.setLayoutX(Slot.SIZE / 2 + 30);
		itemDescText.setLayoutY(Slot.SIZE / 2 + 40);
		setHoveringText("Mange tes morts");
		setHoveringTextVisible(false);
		
		itemDesc.getChildren().add(itemDescText);

		this.getChildren().add(itemDesc);
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

	public void setHoveringText(String name) {
		itemDescText.setText(name);
		itemDescBG.setWidth(itemDescText.getBoundsInLocal().getWidth() + 30);
	}
	
	public void setHoveringTextVisible(boolean value) {
		itemDesc.setVisible(value);
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
		this.setLayoutX(posX - (Slot.SIZE / 2));
		this.setLayoutY(posY - (Slot.SIZE / 2));
	}

	@Override
	public String toString() {
		if (this.item != null)
			return this.item.toString() + ": " + this.quantity;
		return "none";
	}
}