package model;

import java.io.InputStream;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Item {
	private String name;
	private String displayName;
	private Image imageFront;
	private Image imageTop = null; // null if !is3D
	private Image imageSide = null; // null if !is3D
	private boolean is3D;
	private boolean isMultiFaces;

	public Item(String name, InputStream fileFront, InputStream fileSide, InputStream fileTop, boolean is3D) {
		this.name = name;
		this.is3D = is3D;
		this.isMultiFaces = fileSide != null;
		this.imageFront = new Image(fileFront, Slot.SIZE, Slot.SIZE, false, false);

		if (this.isMultiFaces) {
			this.imageSide = new Image(fileSide, Slot.SIZE, Slot.SIZE, false, false);
			this.imageTop = new Image(fileTop, Slot.SIZE, Slot.SIZE, false, false);
		}

		this.displayName = "";
		for (String s : name.split("_")) {
			this.displayName += s.substring(0, 1).toUpperCase() + s.substring(1) + " ";
		}
		this.displayName = this.displayName.trim();
	}

	public Item(String name, InputStream file, boolean is3D) {
		this(name, file, null, null, is3D);
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Image getImageFront() {
		return imageFront;
	}

	public Image getImageTop() {
		return imageTop;
	}

	public Image getImageSide() {
		return imageSide;
	}

	public boolean is3D() {
		return is3D;
	}

	public boolean getIsMultiFaces() {
		return isMultiFaces;
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
