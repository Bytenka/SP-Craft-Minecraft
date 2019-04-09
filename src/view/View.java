package view;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Craft;
import model.Item;
import model.ItemDB;
import model.Model;

public class View extends Application {	
	private Controller controller = null;
	private Model model = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Minecraft");
		primaryStage.setResizable(false);
		
		controller = new Controller();
		model = new Model();
		
		controller.init(model, this);
		model.init(this, controller);
		
		ViewManager manager = new ViewManager(model, controller);
		
		primaryStage.setScene(manager.getMainScene());
		
		//Sneaky Main (Start)
		Item[][] truc = new Item[][]{
			{null, null, null},
            {null, null, null},
            {null, null, null}};
        Craft allo = new Craft(truc);
        System.out.println(allo);

        //Sneaky Main (End)

		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
		
	}
}
