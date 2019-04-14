package controller;

import javafx.scene.input.MouseEvent;
import model.Item;
import model.Model;
import model.Slot;
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

	public void slotMouseEntered(Slot slot, MouseEvent event) {
		if (!slot.isEmpty()) {
			model.playerHand.setHoveringText(slot.getItem().getDisplayName());
			model.playerHand.setHoveringTextVisible(true);
		}
	}

	public void slotMouseExited(Slot slot, MouseEvent event) {
		model.playerHand.setHoveringTextVisible(false);
	}

	public void slotClicked(Slot slot, MouseEvent event) {
		// Switch on the buttons
		switch (event.getButton()) {
		case PRIMARY: {
			// Take or release a stack of items
			if (!slot.isEmpty()) {
				if (!model.playerHand.isEmpty()) {
					if (slot.isContentUserSettable()) {
						// Attempt stacking
						if (slot.putItem(model.playerHand.getItem(), model.playerHand.getQuantity()))
							model.playerHand.clear();
						else {
							if (slot.isContentUserGettable()) {
								// Swap
								Item tempI = model.playerHand.getItem();
								int tempQ = model.playerHand.getQuantity();

								model.playerHand.replaceItem(slot.getItem(), slot.getQuantity());
								slot.replaceItem(tempI, tempQ);
							}
						}
					}
				} else {
					if (slot.isContentUserGettable()) {
						model.playerHand.replaceItem(slot.getItem(), slot.getQuantity());
						slot.clear();
					}
				}
			} else if (!model.playerHand.isEmpty()) {
				if (slot.isContentUserSettable()) {
					slot.replaceItem(model.playerHand.getItem(), model.playerHand.getQuantity());
					model.playerHand.clear();
				}
			}
			break;
		}

		case SECONDARY: {
			if (!model.playerHand.isEmpty()) {
				if (slot.putItem(model.playerHand.getItem(), 1))
					model.playerHand.removeQuantity(1);
			} else if (!slot.isEmpty()) {
				if (slot.isContentUserGettable()) {
					int q = slot.getQuantity() / 2;
					model.playerHand.replaceItem(slot.getItem(), slot.getQuantity() - q);
					slot.removeQuantity(slot.getQuantity() - q);
				}
			}
			break;
		}
		default:
			break;
		}

		// Check if a craft was made. There is no need to update
		// the table if the slot can't change anything
		if (slot.isContentUserGettable() || slot.isContentUserSettable())
			model.craftingTable.update();
	}
}
