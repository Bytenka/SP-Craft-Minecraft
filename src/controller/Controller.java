package controller;

import javafx.scene.input.MouseEvent;
import model.Model;
import view.View;

public class Controller {
	
	private Model model = null;
	private View view = null;
	
	public Controller() {
	}
	
	public void updatePlayerHandPosition(MouseEvent mouseEvent) {
		model.playerHand.updatePosition(mouseEvent);
	}
	
	public void init(Model model, View view) {
		this.model = model;
		this.view = view;
		
		
	}
}
