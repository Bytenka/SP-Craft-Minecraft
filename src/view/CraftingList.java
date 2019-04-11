package view;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import model.ItemDB;
import model.SlotsTable;
import model.Slot;

public class CraftingList extends Pane {
	private static final int DISPLAY_ROWS = 9;
	private static final int DISPLAY_COLS = 9;

	private int nbRows = 50;

	private ScrollPane scrollPane;
	private SlotsTable slots;

	public CraftingList(Controller controller) {
		super();
		nbRows = ItemDB.getItems().size() / DISPLAY_COLS + 1;
		scrollPane = new ScrollPane();
		
		slots = new SlotsTable(nbRows, DISPLAY_COLS, controller);
		slots.populate(ItemDB.getItems().values(), 1);
		for(Slot[] sl : slots.getSlots())
			for(Slot s : sl) {
				s.setContentUserSettable(false);
				s.setContentUserGettable(false);
			}

		scrollPane.setPrefHeight(DISPLAY_ROWS * Slot.SIZE + (DISPLAY_ROWS - 1) * SlotsTable.GAP_SIZE);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setContent(slots);
		scrollPane.getStyleClass().add("itemList");

		slots.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if (event.getDeltaY() > 1)
					scrollPane.setVvalue(scrollPane.getVvalue() - 1.0 / (nbRows - DISPLAY_ROWS));
				else
					scrollPane.setVvalue(scrollPane.getVvalue() + 1.0 / (nbRows - DISPLAY_ROWS));
				event.consume();
			}
		});

		this.getChildren().add(scrollPane);
	}

}