package view;

import javafx.geometry.Rectangle2D;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Model;

public class CraftingUI extends Pane {
	private static final int BORDERS = 80;
	private static final int INNER_SPACE = 100;
	private static final int ROUNDED_CORNERS = 60;

	Model model;

	public CraftingUI(Model model, Image backgroundImage) {
		this.model = model;
		this.setWidth(model.inventory.getWidth() + 2 * BORDERS);
		this.setHeight(model.craftingTable.getHeight() + model.inventory.getHeight() + 2 * BORDERS + INNER_SPACE);
		updateGraphics(backgroundImage);
	}
	
	public void updateGraphics(Image backgroundImage) {
		this.getChildren().clear();
		
		ImageView iw = new ImageView(backgroundImage);
		iw.setFitWidth(this.getWidth());
		iw.setFitHeight(this.getHeight());
		iw.setViewport(new Rectangle2D(this.getLayoutX(), this.getLayoutY(), iw.getFitWidth(), iw.getFitHeight()));
		
		/* Transform the image */
		// Clip for rounded corners
		Rectangle clip = new Rectangle(iw.getFitWidth(), iw.getFitHeight());
		clip.setArcWidth(ROUNDED_CORNERS);
		clip.setArcHeight(ROUNDED_CORNERS);
		iw.setClip(clip);
		
		//SnapshotParameters parameters = new SnapshotParameters();
		//parameters.setFill(Color.TRANSPARENT);
		//WritableImage roundedImage = iw.snapshot(parameters, null);
		//iw.setClip(null);
		//iw.setViewport(null);
		//iw.setImage(roundedImage);
		
		// Make effects to tweak the image
		Blend blend = new Blend(BlendMode.MULTIPLY, null,
				new ColorInput(0, 0, iw.getFitWidth(), iw.getFitHeight(), Color.grayRgb(210)));

		BoxBlur blur = new BoxBlur();
		blur.setWidth(20);
		blur.setHeight(20);
		blur.setIterations(3);
		blur.setInput(blend);
		
		iw.setEffect(blur);
		/* ------------------- */

		this.getChildren().add(iw);
		
		model.craftingTable.setLayoutX(this.getWidth() / 2 - model.craftingTable.getWidth() / 2);
		model.craftingTable.setLayoutY(BORDERS);
		this.getChildren().add(model.craftingTable);

		model.inventory.setLayoutX(BORDERS);
		model.inventory.setLayoutY(this.getHeight() - model.inventory.getHeight() - BORDERS);
		this.getChildren().add(model.inventory);
	}
}
