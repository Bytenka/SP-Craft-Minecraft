package crafting;

public class Slot {
	
	private Item item;
	
	public Slot() {
		this.item = null;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public boolean isEmpty() {
		return this.item != null;
	}
	
	public void putItem(Item i) {
		if (this.isEmpty()) {
			this.item = i;
		}
	}
	
	public void setItem(Item i) {
		this.item = i;
	}
	
	public void removeItem() {
		this.item = null;
	}
	
	@Override
	public String toString() {
		return this.item.toString();
	}
}
