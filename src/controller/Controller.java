package controller;

import java.util.Arrays;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import javafx.scene.input.MouseEvent;
import model.CraftDB;
import model.Item;
import model.ItemDB;
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
			String txt = slot.getItem().getDisplayName();
			if (slot.getQuantity() > 99)
				txt += " (" + slot.getQuantity() + ")";
			model.playerHand.setHoveringText(txt);
			model.playerHand.setHoveringTextVisible(true);
		}

		if (slot.getType() == SlotType.LIST) {
			Slot[][] slots = model.craftingTable.getCraftingBench().getSlots();
			Item[][] craftOfItem = CraftDB.getCraftFromItem(slot.getItem());

			if (craftOfItem != null) {
				for (int i = 0; i < craftOfItem.length; i++)
					for (int j = 0; j < craftOfItem[i].length; j++) {
						if (craftOfItem[i][j] != null)
							slots[i][j].setGhostItem(craftOfItem[i][j]);
					}
			}
		}
	}

	public void slotMouseExited(Slot slot, MouseEvent event) {
		model.playerHand.setHoveringTextVisible(false);

		Slot[][] slots = model.craftingTable.getCraftingBench().getSlots();
		for (Slot[] sl : slots)
			for (Slot s : sl)
				s.clearGhostItem();
	}

	public void slotClicked(Slot slot, MouseEvent event) {
		Slot ph = model.playerHand.slot;

		switch (event.getButton()) {
		case PRIMARY: {
			if (slot.getType() == SlotType.CRAFT_RESULT) {
				if (model.shiftIsDown) {
					Item currentlyCraftedItem = slot.getItem();
					while (slot.getItem() != null && currentlyCraftedItem == slot.getItem()
							&& model.inventory.autoPutSlot(slot.getItem(), slot.getQuantity())) {
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
							} else
								slot.set(tempI, tempQ); // Revert the changes made by slot.removeAll(). // TODO fix bad
														// design
						}
					}
				}

			} else if (slot.getType() == SlotType.LIST) {
				// Here, we want to place the items in the crafting bench

				Slot[][] slots = model.craftingTable.getCraftingBench().getSlots();
				int begI = 0, begJ = 0;

				// First, if the crafting bench is not empty, we must check if the
				// crafted item is the same as the one in the slot.
				// If so, we want to add 1 item to every place possible.
				// If not, we put back every item into the player's inventory
				if (!model.craftingTable.isBenchEmpty()) {
					if (model.craftingTable.getResult() == slot.getItem()) {
						// We need to set the top-left coordinates of
						// the current craft, so the code below can try
						// to add 1 item to the current craft

						// TODO FIXME Wrong result

						int i1 = 0, i2 = 0, j1 = 0, j2 = 0;
						// Lines-Columns
						{
							out: for (int i = 0; i < 3; i++)
								for (int j = 0; j < 3; j++) {
									if (!slots[i][j].isEmpty()) {
										begI = i;
										break out;
									}
								}
							System.out.println(i1 + ", " + j1);
						}

						// Columns-Lines
						{
							out: for (int i = 0; i < 3; i++)
								for (int j = 0; j < 3; j++) {
									if (!slots[j][i].isEmpty()) {
										begJ = i;
										break out;
									}
								}
							System.out.println(i2 + ", " + j2);
						}

					} else {
						// Clear the bench
						for (Slot[] sl : slots)
							for (Slot s : sl) {
								if (!s.isEmpty()) {
									model.inventory.autoPutSlot(s.getItem(), s.getQuantity());
									s.clear();
								}
							}
					}
				}

				// Place items, if possible
				Item[][] craftOfItem = CraftDB.getCraftFromItem(slot.getItem());
				if (craftOfItem != null) {
					boolean stopFlag = false;
					do {
						for (int i = 0; i < craftOfItem.length; i++)
							for (int j = 0; j < craftOfItem[i].length; j++) {
								if (craftOfItem[i][j] != null) {
									if (model.inventory.getQuantityOfItem(craftOfItem[i][j]) >= 1) {
										slots[begI + i][begJ + j].put(craftOfItem[i][j], 1);
										model.inventory.autoRemoveSlot(craftOfItem[i][j], 1);
									} else
										stopFlag = true;
								}
							}
					} while (!stopFlag && model.shiftIsDown);
				}

				model.craftingTable.update();

			} else {
				if (!slot.isEmpty()) {
					Item tempI = slot.getItem();
					int tempQ = slot.getQuantity();

					if (model.shiftIsDown) {
						int q = slot.getQuantity();
						for (int i = 0; i < q; i++) {
							if (!slot.isEmpty() && model.shiftIsDown && model.inventory.autoPutSlot(slot.getItem(), 1))
								slot.remove(1);
						}
					} else {

						if (!ph.isEmpty()) {
							// STACK
							if (slot.put(ph.getItem(), ph.getQuantity()))
								ph.clear();

							// SWAP
							else if (slot.removeAll()) {
								if (slot.put(ph.getItem(), ph.getQuantity()))
									ph.set(tempI, tempQ);
								else
									slot.set(tempI, tempQ); // Revert the changes made by slot.removeAll(). // TODO fix
															// bad
															// design
							}
						} else {
							if (slot.removeAll())
								ph.set(tempI, tempQ);
						}
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
				} else if (!slot.isEmpty() && slot.getType() != SlotType.CRAFT_RESULT) {
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
		 * switch (event.getButton()) { case PRIMARY: { switch (slot.getType()) { case
		 * REGULAR: {
		 * 
		 * break; }
		 * 
		 * case CRAFT_RESULT: {
		 * 
		 * break; }
		 * 
		 * case LIST: {
		 * 
		 * break; }
		 * 
		 * 
		 * default: break; }
		 * 
		 * break; }
		 * 
		 * default: break; }
		 */

		/*
		 * // Switch on the buttons switch (event.getButton()) { case PRIMARY: { if
		 * (!slot.isEmpty()) { if (!ph.isEmpty()) { // SWAP ITEMS if (slot.getType() ==
		 * SlotType.REGULAR) { if (slot.put(ph.getItem(), ph.getQuantity())) // Attempt
		 * stacking in the slot ph.clear(); else { // PERFORM SWAP Item tempI =
		 * ph.getItem(); int tempQ = ph.getQuantity();
		 * 
		 * ph.set(slot.getItem(), slot.getQuantity()); slot.set(tempI, tempQ); } } else
		 * if (slot.getType() == SlotType.CRAFT_RESULT) { if (model.shiftIsDown) { Item
		 * craftedItem = slot.getItem(); // While the item is still the same while
		 * (craftedItem == slot.getItem() && model.inventory.autoPutSlot(slot.getItem(),
		 * slot.getQuantity())) { slot.remove(slot.getQuantity());
		 * craftResultPickedUpAction(); model.craftingTable.update(); }
		 * 
		 * } else if (ph.put(slot.getItem(), slot.getQuantity())) { // Attempt stacking
		 * in the player hand slot.clear(); craftResultPickedUpAction(); } // else, we
		 * can't do anything because the slot can't be set } } else { // GET ITEM FROM
		 * SLOT if (slot.getType() == SlotType.CRAFT_RESULT) { if (model.shiftIsDown) {
		 * Item craftedItem = slot.getItem(); while (craftedItem == slot.getItem() &&
		 * model.inventory.autoPutSlot(slot.getItem(), slot.getQuantity())) {
		 * slot.remove(slot.getQuantity()); craftResultPickedUpAction();
		 * model.craftingTable.update(); } } }
		 * 
		 * if (slot.getType() == SlotType.REGULAR || slot.getType() ==
		 * SlotType.CRAFT_RESULT) { if (ph.put(slot.getItem(), slot.getQuantity()))
		 * slot.clear();
		 * 
		 * if (slot.getType() == SlotType.CRAFT_RESULT) craftResultPickedUpAction(); } }
		 * } else if (!ph.isEmpty()) { // PUT ITEM IN SLOT if (slot.getType() ==
		 * SlotType.REGULAR) { if (slot.put(ph.getItem(), ph.getQuantity())) ph.clear();
		 * } } // ELSE NOTHING HAPPENS break; }
		 * 
		 * case SECONDARY: { if (!ph.isEmpty()) { if (slot.put(ph.getItem(), 1))
		 * ph.remove(1); } else if (!slot.isEmpty()) { if (slot.getType() ==
		 * SlotType.REGULAR || slot.getType() == SlotType.CRAFT_RESULT) { int q =
		 * slot.getQuantity() / 2; ph.set(slot.getItem(), slot.getQuantity() - q);
		 * slot.remove(slot.getQuantity() - q);
		 * 
		 * if (slot.getType() == SlotType.CRAFT_RESULT) craftResultPickedUpAction(); } }
		 * break; }
		 * 
		 * default: break; }
		 * 
		 * model.craftingTable.update();
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
