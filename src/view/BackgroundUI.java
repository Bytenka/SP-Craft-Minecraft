package view;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Model;

public class BackgroundUI extends Pane {
	public static final int BORDERS = 60;
	private static final int ROUNDED_CORNERS = 50;
	private static final int BLUR_STRENGTH = 20; // Also used to offset things

	public BackgroundUI() {
		this.updateGraphics();
	}
	
	public void forceSize(double width, double height) {
		this.setWidth(width);
		this.setHeight(height);
	}

	public void updateGraphics() {
		this.getChildren().clear();

		// We must get the scene coordinates to cut the background correctly
		Bounds posInScene = this.localToScene(this.getBoundsInLocal());
		
		ImageView iw = new ImageView(Model.BACKGROUND_IMAGE);
		iw.setLayoutX(-BORDERS - BLUR_STRENGTH);
		iw.setLayoutY(-BORDERS - BLUR_STRENGTH);
		iw.setViewport(new Rectangle2D(
				posInScene.getMinX() - BORDERS - BLUR_STRENGTH, 
				posInScene.getMinY() - BORDERS - BLUR_STRENGTH, 
				posInScene.getWidth() + 2 * BORDERS + 2 * BLUR_STRENGTH, 
				posInScene.getHeight() + 2 * BORDERS + 2 * BLUR_STRENGTH));
		
		Blend blend = new Blend(BlendMode.MULTIPLY, null,
				new ColorInput(
						0, 
						0, 
						this.getWidth() + 2*BORDERS + 2*BLUR_STRENGTH,
						this.getHeight() + 2*BORDERS + 2*BLUR_STRENGTH, 
						Color.grayRgb(180)));
		
		BoxBlur blur = new BoxBlur();
		blur.setWidth(BLUR_STRENGTH / 2);
		blur.setHeight(BLUR_STRENGTH / 2);
		blur.setIterations(5);
		blur.setInput(blend);
		
		iw.setEffect(blur);
		
		/* Transform the image */
		// Clipping, for rounded corners
		Rectangle clip = new Rectangle(
				this.getWidth() + 2*BORDERS, 
				this.getHeight() + 2*BORDERS);
		clip.setLayoutX(BLUR_STRENGTH);
		clip.setLayoutY(BLUR_STRENGTH);
		clip.setArcWidth(ROUNDED_CORNERS);
		clip.setArcHeight(ROUNDED_CORNERS);
		iw.setClip(clip);
		this.getChildren().add(iw);
		
		Rectangle border = new Rectangle(this.getWidth() + 2*BORDERS, this.getHeight() + 2*BORDERS);
		border.setLayoutX(-BORDERS);
		border.setLayoutY(-BORDERS);
		border.setFill(Color.TRANSPARENT);
		border.setStroke(Color.grayRgb(0, 0.5));
		border.setStrokeWidth(1);
		border.setArcWidth(ROUNDED_CORNERS);
		border.setArcHeight(ROUNDED_CORNERS);
		this.getChildren().add(border);
	}
}
