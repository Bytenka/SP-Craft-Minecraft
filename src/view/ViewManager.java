package view;

import java.io.File;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import model.Model;

// This class holds scenes, in case we want to expand the game
public class ViewManager {
	// Scenes
	private Scene craftingScene;
	private Group craftingLayout;
	private ItemListUI craftingItemList;

	public ViewManager(Model model, Controller controller) {
		initCraftingScene(model, controller);
	}

	public Scene getMainScene() {
		return craftingScene;
	}

	private void initCraftingScene(Model model, Controller controller) {
		// Init
		craftingLayout = new Group();
		craftingScene = new Scene(craftingLayout, Model.BACKGROUND_IMAGE.getWidth(),
				Model.BACKGROUND_IMAGE.getHeight());
		craftingItemList = new ItemListUI(controller);

		// Setting up the background
		Image img = Model.BACKGROUND_IMAGE;
		ImagePattern bimg = new ImagePattern(img, 0, 0, img.getWidth(), img.getHeight(), false);
		craftingScene.setFill(bimg);

		CraftingUI craftingUI = new CraftingUI(model);
		craftingUI.setLayoutX(craftingScene.getWidth() / 2 - craftingUI.getWidth() / 2);
		craftingUI.setLayoutY(craftingScene.getHeight() / 2 - craftingUI.getHeight() / 2);
		craftingUI.updateGraphics(); // Because we moved the ui, we must reconstruct the background
		craftingLayout.getChildren().add(craftingUI);

		craftingItemList.setLayoutX(craftingScene.getWidth() + BackgroundUI.BORDERS);
		craftingItemList.setLayoutY(craftingScene.getHeight() / 2 - craftingItemList.getHeight() / 2);
		craftingItemList.updateGraphics();
		craftingLayout.getChildren().add(craftingItemList);

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
	}
}
