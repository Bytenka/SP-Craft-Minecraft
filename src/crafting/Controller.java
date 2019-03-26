package crafting;

import java.awt.event.MouseEvent;
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
		if (o instanceof Slot.SlotObservable) {
			Slot.SlotObservable observer = (Slot.SlotObservable) o;
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
					// Attempt stacking
					if (slot.putItem(m_model.playerHand.getItem(), m_model.playerHand.getQuantity()))
						m_model.playerHand.clear();
					else
						Slot.swap(slot, m_model.playerHand);
				} else {
					m_model.playerHand.setItem(slot.getItem(), slot.getQuantity());
					slot.clear();
				}
				break;
			default:
				break;
			}
			observer.reset();
			slot.repaint();
		} else if (o instanceof View.ViewObservable){
			MouseEvent mouseEvent = (MouseEvent)arg;
			
			int centerOffset = Slot.SIZE/2;
			m_model.playerHand.setLocation(mouseEvent.getX() - centerOffset, mouseEvent.getY() - centerOffset);
			m_model.playerHand.repaint();
		}
	}
}
