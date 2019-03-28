package view;

import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;
import javafx.scene.transform.Translate;

public final class ItemGraphicsFactory {
	public static Group make2D(Image img) {
		Group g = new Group();
		g.getChildren().add(new ImageView(img));
		return g;
	}

	public static Group make3D(Image img) {
		return make3D(img, img, img);
	}

	public static Group make3D(Image blockTop, Image blockLeft, Image blockRight) {
		Group group = new Group();

		// Top part
		{
			ImageView top = new ImageView(blockTop);

			Rotate rotate = new Rotate();
			rotate.setPivotX(Slot.SIZE / 2.0);
			rotate.setPivotY(Slot.SIZE / 2.0);
			rotate.setAngle(45);

			Translate translate = new Translate();
			translate.setX(Slot.SIZE / 5.0);
			translate.setY(Slot.SIZE / 5.0);

			Scale scale1 = new Scale();
			scale1.setX(0.707); // 1 / sqrt(2)
			scale1.setY(0.707); // //

			Scale scale2 = new Scale();
			scale2.setY(0.40);

			top.getTransforms().addAll(scale2, scale1, translate, rotate); // Order matters (last one first applied)
			group.getChildren().add(top);
		}

		// Left part
		{
			ImageView left = new ImageView(blockLeft);

			Translate translate = new Translate();
			translate.setY(Slot.SIZE / 5.0);

			Scale scale = new Scale();
			scale.setPivotY(Slot.SIZE / 5.0);
			scale.setX(0.5);
			scale.setY(0.6);

			Shear shear = new Shear();
			shear.setPivotY(Slot.SIZE / 5.0);
			shear.setY(0.4);

			left.getTransforms().addAll(shear, scale, translate); // Order matters (last one first applied)

			// Lightning
			ColorAdjust adjust = new ColorAdjust();
			adjust.setBrightness(-0.25);
			left.setEffect(adjust);

			group.getChildren().add(left);
		}

		// Right part
		{
			ImageView right = new ImageView(blockRight);

			Translate translate = new Translate();
			translate.setX(Slot.SIZE / 2.0);
			translate.setY(Slot.SIZE / 2.5);

			Scale scale = new Scale();
			scale.setPivotX(Slot.SIZE / 2.0);
			scale.setPivotY(Slot.SIZE / 2.5);
			scale.setX(0.5);
			scale.setY(0.6);

			Shear shear = new Shear();
			shear.setPivotX(Slot.SIZE / 2.0);
			shear.setPivotY(Slot.SIZE / 2.5);
			shear.setY(-0.4);

			right.getTransforms().addAll(shear, scale, translate); // Order matters (last one first applied)

			// Lightning
			ColorAdjust adjust = new ColorAdjust();
			adjust.setBrightness(-0.4);
			right.setEffect(adjust);

			group.getChildren().add(right);
		}

		// Finalize -> scale down a bit
		{
			Scale scale = new Scale();
			scale.setPivotX(Slot.SIZE / 2.0);
			scale.setPivotY(Slot.SIZE / 2.0);
			scale.setX(0.85);
			scale.setY(0.85);

			group.getTransforms().add(scale);
		}

		return group;
	}
}
