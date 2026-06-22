package data;

import java.awt.Point;

import core.ItemType;

public class SelectionDragged extends Selection {

    private boolean done = false;
    private Point itemSource = null; //original [row, col]
	
    //class used to save the needed values for drag and drop an object / NPC on the canvas
	public SelectionDragged() {
		super(ItemType.NONE, -1);
	}
	
	public void reset() {
		this.done = false;
		this.itemSource = null;
		this.setType(ItemType.NONE);
		this.setIndex(-1);
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Point getItemSource() {
		return itemSource;
	}

	public void setItemSource(Point itemSource) {
		this.itemSource = itemSource;
	}
}
