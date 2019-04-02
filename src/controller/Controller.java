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

	public void updatePlayerHandPosition(MouseEvent mouseEvent) {
		model.playerHand.updatePosition(mouseEvent);
	}

	public void slotClicked(Slot slot) {
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

		// TODO update crafting table (call Pricilien's function)
	}

	public void init(Model model, View view) {
		this.model = model;
		this.view = view;

	}
}
