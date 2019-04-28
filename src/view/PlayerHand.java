package view;

import controller.Controller;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import model.Model;
import model.Slot;
import model.Slot.SlotType;

public class PlayerHand extends Pane {
	public Slot slot;

	// Hovering text
	private Pane itemDesc;
	private Rectangle itemDescBG;
	private Text itemDescText;
	
	private Controller controller;

	public PlayerHand(Controller controller) {
		super();
		this.slot = new Slot(SlotType.REGULAR, controller);
		this.setMouseTransparent(true);
		this.getChildren().add(slot.getDrawableItem());

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
		setHoveringTextVisible(false);
		
		itemDesc.getChildren().add(itemDescText);

		this.getChildren().add(itemDesc);
	}


	public void setHoveringText(String name) {
		itemDescText.setText(name);
		itemDescBG.setWidth(itemDescText.getBoundsInLocal().getWidth() + 30);
	}
	
	public void setHoveringTextVisible(boolean value) {
		itemDesc.setVisible(value);
	}

	public void updatePosition(MouseEvent event) {
		updatePosition(event.getX(), event.getY());
	}

	public void updatePosition(double posX, double posY) {
		this.setLayoutX(posX - (Slot.SIZE / 2));
		this.setLayoutY(posY - (Slot.SIZE / 2));
	}
}