package crafting;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

	private Item inHand;
	
	public Controller() {
		
	}

	@Override
	public void update(Observable o, Object arg) {
		Slot slot = (Slot)arg; // This is safe
		System.out.println(slot);
	}
}
