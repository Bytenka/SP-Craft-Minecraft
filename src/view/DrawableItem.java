package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Item;
import model.Model;
import model.Slot;

public class DrawableItem extends Group {
	private Group itemGraphics;
	private Text quantityText;

	public DrawableItem() {
		super();

		itemGraphics = new Group();
		this.getChildren().add(itemGraphics);

		quantityText = new Text();
		quantityText.setLayoutX(3);
		quantityText.setLayoutY(Slot.SIZE - 3);
		quantityText.setWrappingWidth(Slot.SIZE - 6);
		quantityText.setMouseTransparent(true);
		quantityText.setFont(Model.FONT);
		quantityText.setFill(Color.rgb(230, 230, 230));
		this.getChildren().add(quantityText);

		this.clear();
		this.setQuantity(0);
	}

	public void clear() {
		itemGraphics.getChildren().clear();
		this.setQuantity(0);
	}

	public void set(Item item) {
		if (item == null)
			throw new RuntimeException("Item is null");

		this.clear();

		if (item.is3D()) {
			if (item.getIsMultiFaces())
				itemGraphics.getChildren()
						.add(ItemGraphicsFactory.make3D(item.getImageTop(), item.getImageFront(), item.getImageSide()));
			else
				itemGraphics.getChildren().add(ItemGraphicsFactory.make3D(item.getImageFront()));

		} else
			itemGraphics.getChildren().add(ItemGraphicsFactory.make2D(item.getImageFront()));

	}

	public void setQuantity(int quantity) {
		int maxVal = 99;
		if (quantity > maxVal) {
			quantityText.setText("+" + maxVal);
			return;
		}

		if (quantity >= 2) {
			quantityText.setText("" + quantity);
			return;
		}

		quantityText.setText("");
	}
}
