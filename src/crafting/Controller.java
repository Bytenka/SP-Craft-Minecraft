package crafting;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
	public Slot itemInHand;
	
	private Model m_model;
	private View m_view;

	public Controller() {
	}
	
	public void init(Model model, View view) {
		m_model = model;
		m_view = view;
		itemInHand = new Slot(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		Slot slot = (Slot) arg; // This is safe
		if (slot.getItem().equals(itemInHand.getItem())) {
			slot.addQuantity(itemInHand.getQuantity());
			itemInHand.clear();
		} else {
			Slot.swapItems(itemInHand, slot);
		}

		System.out.println(slot);
	}
}
