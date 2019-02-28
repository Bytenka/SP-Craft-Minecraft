package crafting;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JFrame {
	public static final int DEFAULT_WIDTH = 500;
	public static final int DEFAULT_HEIGHT = 500;

	private String iconPath = "img/icon.png";
	private InnerPanel panel;

	public App() {
		panel = new InnerPanel();

		// Initializing frame
		try {
			this.setIconImage(ImageIO.read(new File(iconPath)));
		} catch (IOException e) {
			System.err.println("Unable to set app icon: " + iconPath + ": file not found!");
		}

		this.setTitle("Crafting bench");
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.getContentPane().setBackground(Color.black); // Color in case of errors
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Just to be explicit

		this.add(panel);

		this.setVisible(true);
	}
	
	// Inner class to have something to draw to
	private class InnerPanel extends JPanel {
		public InnerPanel() {
			// Initializing panel
			this.setBackground(Color.cyan);
			this.setVisible(true);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.blue);
			g.fillRect(10,  10, 50, 50);
		}
	}

	public static void main(String[] args) {
		new App();
	}
}
