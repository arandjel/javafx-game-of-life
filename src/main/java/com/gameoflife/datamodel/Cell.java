package com.gameoflife.datamodel;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Cell extends Rectangle {

	private Boolean isAlive;

	public Cell() {
		super();
		this.isAlive = false;
		this.setStroke(Color.DARKGRAY);
		this.setStrokeType(StrokeType.INSIDE);
		this.setStrokeWidth(0.5);
	}

	public Boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;

		if (this.isAlive == true) {
			this.setFill(Color.YELLOW);
		} else {
			this.setFill(Color.WHITE);
		}
	}
}
