package _do_not_use;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerHand extends Slot {
	public PlayerHand(Controller ctrl) {
		super(ctrl);
		this.setSize(Slot.SIZE, Slot.SIZE);
	}

	@Override
	public void paint(Graphics g) {
		// Display the item image
		if (this.item != null)
			g.drawImage(this.item.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	
		// Display the item quantity
		if (this.quantity > 1) {
			g.setFont(Model.FONT);

			// Draw quantity with a shadow to make it readable
			int textX = 5;
			int textY = this.getHeight() - 5;
			g.setColor(Color.darkGray);
			g.drawString(String.valueOf(this.quantity), textX - 2, textY + 2);
			g.setColor(Model.FONT_COLOR);
			g.drawString(String.valueOf(this.quantity), textX, textY);
		}
	}
}
