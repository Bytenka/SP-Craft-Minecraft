package crafting;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
	private Model m_model;
	private View m_view;

	public Controller() {
	}

	public void init(Model model, View view) {
		m_model = model;
		m_view = view;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Slot.SlotObserver) {
			Slot.SlotObserver observer = (Slot.SlotObserver) o;
			Slot slot = (Slot) arg;

			switch (observer.getEvent()) {
			case MOUSE_ENTERED:
				slot.setIsSelected(true);
				break;
			case MOUSE_EXITED:
				slot.setIsSelected(false);
				break;
			case MOUSE_CLICKED:
				if (!m_model.playerHand.isEmpty()) {
					if (slot.putItem(m_model.playerHand.getItem(), m_model.playerHand.getQuantity())) // Attempt stacking
					{
						m_model.playerHand.clear();
					} else {
						Slot.swap(slot, m_model.playerHand);
						System.out.println("SWAP");
					}
				} else {
					m_model.playerHand.setItem(slot.getItem(), slot.getQuantity());
					slot.clear();
				}

				System.out.println(m_model.playerHand);
				break;
			default:
				break;
			}

			observer.reset();
			slot.repaint();
		}
		/*
		 * if (slot.getItem().equals(itemInHand.getItem())) {
		 * slot.addQuantity(itemInHand.getQuantity()); itemInHand.clear(); } else {
		 * Slot.swapItems(itemInHand, slot); }
		 * 
		 * System.out.println("oui");
		 */
	}
}
