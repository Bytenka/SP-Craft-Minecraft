package crafting;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class App extends Frame implements WindowListener {

	public App() {
		this.setSize(500, 500);
		this.setBackground(Color.gray);
		this.setTitle("Crafting bench");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png"));
		
		this.addWindowListener(this);
		
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new App();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		this.dispose();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}
