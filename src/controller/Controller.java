package controller;

import javafx.scene.input.MouseEvent;
import model.Item;
import model.Model;
import model.Slot;
import model.Slot.SlotType;
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
		Slot ph = model.playerHand.slot;
		
		switch (event.getButton()) {
		case PRIMARY: {
			if (slot.getType() == SlotType.CRAFT_RESULT) {
				if (model.shiftIsDown) {
					Item currentlyCraftedItem = slot.getItem();
					while (slot.getItem() != null && currentlyCraftedItem == slot.getItem() && model.inventory.autoPutSlot(slot.getItem(), slot.getQuantity())) {
						craftResultPickedUpAction();
						model.craftingTable.update();
					}
				} else {
					if (!slot.isEmpty()) {
						Item tempI = slot.getItem();
						int tempQ = slot.getQuantity();
						
						if (slot.removeAll()) {
							if (ph.put(tempI, tempQ)) {
								craftResultPickedUpAction();
								model.craftingTable.update();							
							} else slot.set(tempI, tempQ); // Revert the changes made by slot.removeAll(). // TODO fix bad design
						}
					}
				}
				
			} else {
				if (!slot.isEmpty()) {
					Item tempI = slot.getItem();
					int tempQ = slot.getQuantity();
					
					if (!ph.isEmpty()) {
						// STACK
						if (slot.put(ph.getItem(), ph.getQuantity()))
							ph.clear();
						
						// SWAP
						else if (slot.removeAll()) {
							if (slot.put(ph.getItem(), ph.getQuantity()))
								ph.set(tempI, tempQ);
							else slot.set(tempI, tempQ); // Revert the changes made by slot.removeAll(). // TODO fix bad design
						} 
					} else {
						if (slot.removeAll()) ph.set(tempI, tempQ);
					}
					
				} else if (!ph.isEmpty()) {					
					if (slot.put(ph.getItem(), ph.getQuantity()))
						ph.clear();
				}
				
				model.craftingTable.update();
			}
			
			break;
		}
		
		case SECONDARY: {
			if (slot.getType() == SlotType.LIST) {
				int q = model.shiftIsDown ? 64 : 1;
				model.inventory.autoPutSlot(slot.getItem(), q);
				
			} else { 
				if (!ph.isEmpty()) {
					if (slot.put(ph.getItem(), 1))
						ph.remove(1);
				} else if (!slot.isEmpty()) {
					Item tempI = slot.getItem();
					int tempQ = slot.getQuantity();
					int q = slot.getQuantity() / 2;
					
					if (slot.remove(tempQ - q))
						ph.set(tempI, tempQ - q);
				}
			}
			model.craftingTable.update();
			break;
		}
		
		default:
			break;
		}
		
		/*
		switch (event.getButton()) {
		case PRIMARY: {
			switch (slot.getType()) {
			case REGULAR: {
				
				break;
			}
				
			case CRAFT_RESULT: {
				
				break;
			}
				
			case LIST: {
				
				break;
			}
				

			default:
				break;
			}
			
			break;
		}

		default:
			break;
		}
		*/

		/*
		// Switch on the buttons
		switch (event.getButton()) {
		case PRIMARY: {
			if (!slot.isEmpty()) {
				if (!ph.isEmpty()) {
					// SWAP ITEMS
					if (slot.getType() == SlotType.REGULAR) {
						if (slot.put(ph.getItem(), ph.getQuantity())) // Attempt stacking in the slot
							ph.clear();
						else {
							// PERFORM SWAP
							Item tempI = ph.getItem();
							int tempQ = ph.getQuantity();

							ph.set(slot.getItem(), slot.getQuantity());
							slot.set(tempI, tempQ);
						}
					} else if (slot.getType() == SlotType.CRAFT_RESULT) {
						if (model.shiftIsDown) {
							Item craftedItem = slot.getItem(); // While the item is still the same
							while (craftedItem == slot.getItem() && model.inventory.autoPutSlot(slot.getItem(), slot.getQuantity())) {
								slot.remove(slot.getQuantity());
								craftResultPickedUpAction();
								model.craftingTable.update();
							}
							
						} else if (ph.put(slot.getItem(), slot.getQuantity())) { // Attempt stacking in the player hand
							slot.clear();
							craftResultPickedUpAction();
						}
						// else, we can't do anything because the slot can't be set
					}
				} else {
					// GET ITEM FROM SLOT
					if (slot.getType() == SlotType.CRAFT_RESULT) {
						if (model.shiftIsDown) {
							Item craftedItem = slot.getItem();
							while (craftedItem == slot.getItem() && model.inventory.autoPutSlot(slot.getItem(), slot.getQuantity())) {
								slot.remove(slot.getQuantity());
								craftResultPickedUpAction();
								model.craftingTable.update();
							}
						}
					}
					
					if (slot.getType() == SlotType.REGULAR || slot.getType() == SlotType.CRAFT_RESULT) {
						if (ph.put(slot.getItem(), slot.getQuantity()))
							slot.clear();

						if (slot.getType() == SlotType.CRAFT_RESULT)
							craftResultPickedUpAction();
					}
				}
			} else if (!ph.isEmpty()) {
				// PUT ITEM IN SLOT
				if (slot.getType() == SlotType.REGULAR) {
					if (slot.put(ph.getItem(), ph.getQuantity()))
						ph.clear();
				}
			}
			// ELSE NOTHING HAPPENS
			break;
		}

		case SECONDARY: {
			if (!ph.isEmpty()) {
				if (slot.put(ph.getItem(), 1))
					ph.remove(1);
			} else if (!slot.isEmpty()) {
				if (slot.getType() == SlotType.REGULAR || slot.getType() == SlotType.CRAFT_RESULT) {
					int q = slot.getQuantity() / 2;
					ph.set(slot.getItem(), slot.getQuantity() - q);
					slot.remove(slot.getQuantity() - q);

					if (slot.getType() == SlotType.CRAFT_RESULT)
						craftResultPickedUpAction();
				}
			}
			break;
		}

		default:
			break;
		}

		model.craftingTable.update();
		*/
	}

	private void craftResultPickedUpAction() {
		// Remove one item on every slot
		for (Slot[] st : model.craftingTable.getCraftingBench().getSlots())
			for (Slot s : st) {
				s.remove(1);
			}
	}
}
