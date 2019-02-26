package crafting;

public class Item {

	private String id;
	
	public Item(String str) {
		this.id = str;
	}
	
	@Override
	public String toString() {
		return this.id;
	}
}
