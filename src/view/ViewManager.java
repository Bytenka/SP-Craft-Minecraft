package view;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;

import controller.Controller;
import model.Model;
import model.SlotsTable;

// This class holds scenes, in case we want to expand the game
public class ViewManager {
	public static final int WIDTH = 1300;
	public static final int HEIGHT = 900;

	// Scenes
	private Scene craftingScene;
	private Group craftingLayout;
	private CraftingList craftingList;

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
		craftingList = new CraftingList(controller);

		// Setting up the background
		Image img = new Image("file:res/graphics/crafting_background.png");
		ImagePattern backgroundImage = new ImagePattern(img, 0, 0, img.getWidth(), img.getHeight(), false);
		craftingScene.setFill(backgroundImage);

		CraftingUI ui = new CraftingUI(model, img);
		ui.setLayoutX(craftingScene.getWidth() / 2 - ui.getWidth() / 2);
		ui.setLayoutY(craftingScene.getHeight() / 2 - ui.getHeight() / 2);
		ui.updateGraphics(img); // Because we moved the ui, we must reconstruct the background
		craftingLayout.getChildren().add(ui);

		craftingLayout.getChildren().add(model.playerHand);

		// Load graphics from CSS
		File styleCSS = new File(Model.CSS_PATH + "main.css");
		craftingScene.getStylesheets().add("file:///" + styleCSS.getAbsolutePath().replace("\\", "/"));

		// Forward events
		craftingScene.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				controller.updatePlayerHandPosition(mouseEvent);
			}
		});

		craftingLayout.getChildren().add(craftingList);

	}
}
