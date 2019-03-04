package crafting;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JFrame {
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 500;
	
	private InnerPanel panel;

	public View(Model model) {
		panel = new InnerPanel(model);

		this.setIconImage(new ImageIcon(Model.APP_ICON).getImage());
		this.setTitle("Crafting bench");
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.getContentPane().setBackground(Color.pink); // Flashy color, just for bug hunting
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Default -> just to be explicit here

		this.add(panel);

		this.setVisible(true);
	}

	// Inner class to have something to draw to
	private class InnerPanel extends JPanel {
		public InnerPanel(Model model) {
			this.setBackground(Color.darkGray);
			this.setVisible(true);

			// Do not use layouts for this (for now) TODO
			this.setLayout(null);
			model.inventory.setLocation(50, 50);
			this.add(model.inventory);
		}
	}

}
