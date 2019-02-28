package crafting;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JFrame {
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 500;

	private String iconPath = "img/icon.png";
	private InnerPanel panel;

	Inventory inventory;

	public App() {
		inventory = new Inventory(); // Initialize everything *before* panel
		
		panel = new InnerPanel();

		this.setIconImage(new ImageIcon(iconPath).getImage());

		this.setTitle("Crafting bench");
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.getContentPane().setBackground(Color.pink); // Flashy color, just for bugs hunting
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Default, so just to be explicit here

		this.add(panel);

		this.setVisible(true);
	}

	// Inner class to have something to draw to
	private class InnerPanel extends JPanel {
		public InnerPanel() {
			this.setBackground(Color.cyan);
			this.setVisible(true);

			// Do not use layouts for this (for now) TODO
			this.setLayout(null);
			inventory.setLocation(50, 50);
			this.add(inventory);
		}
	}

	public static void main(String[] args) {
		new App();
	}
}
