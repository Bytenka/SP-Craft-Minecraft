package crafting;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item {

	private String name;
	private File file;
	private BufferedImage image;

	public Item(String name, File file) throws IOException {
		this.name = name;
		this.file = file;
		image = ImageIO.read(file);
	}

	public BufferedImage getImage() {
		return image;
	}

	public File getFile() {
		return this.file;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String)
			return obj.equals(this.name);

		if (obj instanceof Item)
			return ((Item) obj).name.equals(this.name);
		return false;
	}
}
