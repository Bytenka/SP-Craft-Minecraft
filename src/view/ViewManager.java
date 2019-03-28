package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import controller.Controller;
import model.Model;

// This class holds scenes, in case we want to expand the game
public class ViewManager {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	
	// Scenes
	private Scene craftingScene;
	private Group craftingLayout;

	public ViewManager(Model model, Controller controller) {
		initCraftingScene(model, controller);
	}

	public Scene getMainScene() {
		return craftingScene;
	}

	private void initCraftingScene(Model model, Controller controller) {
		// Init
		craftingLayout = new Group();
		craftingScene = new Scene(craftingLayout, WIDTH, HEIGHT);
		craftingScene.setFill(Color.DARKSLATEGRAY);

		// Adding objects to scene
		craftingLayout.getChildren().add(model.inventory);
		craftingLayout.getChildren().add(model.craftingTable);
		craftingLayout.getChildren().add(model.playerHand);
		
		// Placing objects in scene
		model.inventory.setLayoutX(10);
		model.inventory.setLayoutY(10);
		model.craftingTable.setLayoutX(10);
		model.craftingTable.setLayoutY(300);

		// Load graphics from CSS
		File styleCSS = new File(Model.CSS_PATH + "crafting.css");
		craftingScene.getStylesheets().add("file:///" + styleCSS.getAbsolutePath().replace("\\", "/"));

		// Forward events
		craftingScene.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				controller.updatePlayerHandPosition(mouseEvent);
			}
		});
	}
}
