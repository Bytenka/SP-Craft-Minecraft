package view;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Model;

public class View extends Application {	
	private Controller controller = null;
	private Model model = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Minecraft");
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("file:res/graphics/icon.png"));
		
		controller = new Controller();
		model = new Model();
		
		controller.init(model, this);
		model.init(this, controller);
		
		ViewManager manager = new ViewManager(model, controller);
		
		primaryStage.setScene(manager.getMainScene());

		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
