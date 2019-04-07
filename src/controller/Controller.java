package controller;

import javafx.scene.input.MouseEvent;
import model.Model;
import view.Item;
import view.Slot;
import view.View;

public class Controller {

	private Model model = null;
	private View view = null;

	public Controller() {
	}

	public void init(Model model, View view) {
		this.model = model;
		this.view = view;

	}

	public void updatePlayerHandPosition(MouseEvent mouseEvent) {
		model.playerHand.updatePosition(mouseEvent);
	}

	public void slotClicked(Slot slot, MouseEvent event) {
		// Switch on the buttons
		switch (event.getButton()) {
		case PRIMARY: {
			// Take or release a stack of items
			if (slot.getContentUserModifiable()) {
				if (!model.playerHand.isEmpty()) {
					if (!slot.isEmpty()) {
						if (slot.getItem().equals(model.playerHand.getItem())) {
							// Stack the items
							slot.addQuantity(model.playerHand.getQuantity());
							model.playerHand.clear();

						} else {
							// Swap items
							Item tempI = model.playerHand.getItem();
							int tempQ = model.playerHand.getQuantity();

							model.playerHand.setItemFromSlot(slot);
							slot.setItem(tempI, tempQ);
						}

					} else {
						slot.setItem(model.playerHand.getItem(), model.playerHand.getQuantity());
						model.playerHand.clear();
					}
				} else if (!slot.isEmpty()) {
					model.playerHand.setItemFromSlot(slot);
					slot.clear();
				}
			}
			break;
		}
		
		case SECONDARY: {
			// Drop one item in the clicked slot, if possible
			if (!model.playerHand.isEmpty()) {
				if (slot.putItem(model.playerHand.getItem(), 1))
					model.playerHand.removeQuantity(1);
			}
			break;
		}
		default:
			break;
		}
		model.craftingTable.update();
	}
}
