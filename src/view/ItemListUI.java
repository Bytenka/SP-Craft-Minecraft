package view;

import java.util.TreeSet;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import model.ItemDB;
import model.Slot;
import model.Slot.SlotType;
import model.SlotsTable;

public class ItemListUI extends Pane {
	private static final int DISPLAY_ROWS = 9;
	private static final int DISPLAY_COLS = 9;

	private int nbRows = 50;

	private ScrollPane scrollPane;
	private SlotsTable slots;
	private BackgroundUI background;

	public ItemListUI(Controller controller) {
		super();
		nbRows = ItemDB.getItems().size() / DISPLAY_COLS + 1;
		scrollPane = new ScrollPane();
		background = new BackgroundUI();
		this.getChildren().add(background);
		
		slots = new SlotsTable(nbRows, DISPLAY_COLS, controller);
		
		// Sort the keys in the HashMap and feed them to the SlotsTable
		TreeSet<String> sortedTreeSet = new TreeSet<>(ItemDB.getItems().keySet());
		String[] sortedList = new String[sortedTreeSet.size()];
		sortedTreeSet.toArray(sortedList);
		slots.populate(sortedList, 1);
		
		for(Slot[] sl : slots.getSlots())
			for(Slot s : sl) {
				s.setType(SlotType.REGULAR);
			}

		scrollPane.setPrefWidth(DISPLAY_COLS * Slot.SIZE + (DISPLAY_COLS - 1) * SlotsTable.GAP_SIZE);
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
		this.setWidth(scrollPane.getPrefWidth());
		this.setHeight(scrollPane.getPrefHeight());
		
		background.forceSize(this.getWidth(), this.getHeight());
		this.updateGraphics();
	}
	
	public void updateGraphics() {
		background.updateGraphics();	
	}
}