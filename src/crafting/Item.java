package crafting;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item {
	private String m_name;
	private File m_file;
	private BufferedImage m_image;

	public Item(String name, File file) throws IOException {
		m_name = name;
		m_file = file;
		m_image = ImageIO.read(file);
	}
	
	public String getName() {
		return m_name;
	}
	
	public File getFile() {
		return m_file;
	}

	public BufferedImage getImage() {
		return m_image;
	}

	@Override
	public String toString() {
		return m_name;
	}

	@Override
	public boolean equals(Object obj) { // Accepts Item and String to test equality
		if (obj != null) {
			if (obj instanceof String)
				return obj.equals(m_name);

			if (obj instanceof Item)
				return ((Item) obj).m_name.equals(m_name);
		}
		return false;
	}
}
