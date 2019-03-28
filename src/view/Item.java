package view;

import java.io.InputStream;

import _do_not_use.Slot;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Item {
	private String name;
	private Image image;
	private boolean is3D;

	public Item(String name, InputStream file, boolean is3D) {
		this.name = name;
		this.is3D = is3D;
		this.image = new Image(file, Slot.SIZE, Slot.SIZE, false, false);
	}

	public String getName() {
		return name;
	}

	public Image getImage() {
		return image;
	}
	
	public boolean getIs3D() {
		return is3D;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) { // Accepts Item and String to test equality
		if (obj != null) {
			if (obj instanceof String)
				return obj.equals(name);

			if (obj instanceof Item)
				return ((Item) obj).name.equals(name);
		}
		return false;
	}
}
