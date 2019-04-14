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
	private static final int BLUR_STRENGTH = 20;

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
		iw.setLayoutX(-BLUR_STRENGTH);
		iw.setLayoutY(-BLUR_STRENGTH);
		iw.setFitWidth(this.getWidth() + 2 * BLUR_STRENGTH);
		iw.setFitHeight(this.getHeight() + 2 * BLUR_STRENGTH);
		iw.setViewport(new Rectangle2D(
				this.getLayoutX() - BLUR_STRENGTH, 
				this.getLayoutY() - BLUR_STRENGTH, 
				this.getWidth()+2*BLUR_STRENGTH, 
				this.getHeight()+2*BLUR_STRENGTH));
		
		Blend blend = new Blend(BlendMode.MULTIPLY, null,
				new ColorInput(
						0, 
						0, 
						iw.getFitWidth(), 
						iw.getFitHeight(), 
						Color.grayRgb(180)));

		BoxBlur blur = new BoxBlur();
		blur.setWidth(BLUR_STRENGTH/2);
		blur.setHeight(BLUR_STRENGTH/2);
		blur.setIterations(5);
		blur.setInput(blend);
		
		iw.setEffect(blur);
		/* Transform the image */
		// Clip for rounded corners
		Rectangle clip = new Rectangle(
				this.getWidth(), 
				this.getHeight());
		clip.setLayoutX(BLUR_STRENGTH);
		clip.setLayoutY(BLUR_STRENGTH);
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

		/* ------------------- */

		this.getChildren().add(iw);
		
		Rectangle border = new Rectangle(this.getWidth(), this.getHeight());
		border.setFill(Color.TRANSPARENT);
		border.setStroke(Color.grayRgb(0, 0.5));
		border.setStrokeWidth(1);
		border.setArcWidth(ROUNDED_CORNERS);
		border.setArcHeight(ROUNDED_CORNERS);
		
		this.getChildren().add(border);
		/*
		*/
		
		model.craftingTable.setLayoutX(this.getWidth() / 2 - model.craftingTable.getWidth() / 2);
		model.craftingTable.setLayoutY(BORDERS);
		this.getChildren().add(model.craftingTable);

		model.inventory.setLayoutX(BORDERS);
		model.inventory.setLayoutY(this.getHeight() - model.inventory.getHeight() - BORDERS);
		this.getChildren().add(model.inventory);
	}
}
