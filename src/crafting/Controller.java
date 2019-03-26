package crafting;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
	public Slot itemInHand;

	public Controller() {
		itemInHand = new Slot(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		Slot slot = (Slot) arg; // This is safe
		Slot.swapItems(itemInHand, slot);
		System.out.println(slot);
	}
}
