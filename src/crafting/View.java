package crafting;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JFrame {
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 500;

	// The panel we are going to draw to.
	private InnerPanel m_panel;

	public View() {
	}

	public void init(Model model, Controller controller) {
		m_panel = new InnerPanel();
		m_panel.init(model, controller);

		// Setting up the window
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon(Model.APP_ICON).getImage());
		this.setTitle("Crafting bench");
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.getContentPane().setBackground(Color.pink); // Flashy color, just for bug hunting

		this.add(m_panel);
		this.setVisible(true);

	}

	private class InnerPanel extends JPanel{

		private Model m_model = null;
		private Controller m_controller = null;

		public InnerPanel() {
			
		}

		private void init(Model model, Controller controller) {
			m_model = model;
			m_controller = controller;
			
			this.setBackground(Color.darkGray);
			this.setVisible(true);

			// Do not use layouts for this (for now) TODO
			this.setLayout(null);
			m_model.inventory.setLocation(50, 50);
			this.add(m_model.inventory);
			m_model.ctable.setLocation(50, 200);
			this.add(m_model.ctable);
		}
	}
}
